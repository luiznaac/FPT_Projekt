package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import chat.ChatServer;
import javafx.util.Pair;
import fpt.com.Order;
import fpt.com.Product;

public class Warehouse {

	private static Order boughtProducts;
	private static TimeServer timeServer;

	public static void main(String[] args) {

		timeServer = new TimeServer();
		timeServer.start();
		int tcpPort = 6666;
		Lock lock = new ReentrantLock();
		boughtProducts = new model.Order();
		try {
			LocateRegistry.createRegistry(1099);
			Remote chatServer = new ChatServer();
			Naming.rebind("//127.0.0.1:1099/server", chatServer);
		} catch (RemoteException | MalformedURLException e2) {
			e2.printStackTrace();
		}

		try (ServerSocket server = new ServerSocket(tcpPort)) {
			while(true) {
				try {
					Socket socket = server.accept();
					new ClientThread(socket, lock, boughtProducts).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

class ClientThread extends Thread {

	private Socket socket;
	private Lock lock;
	private Order boughtProducts;

	public ClientThread(Socket socket, Lock lock, Order boughtProducts) {
		this.socket = socket;
		this.lock = lock;
		this.boughtProducts = boughtProducts;
	}

	public void run(){
		try (InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream()) {
			ObjectInputStream ois = new ObjectInputStream(in);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			while(true) {
				Pair<Pair<String, String>, Order> read = null;
				try {
					read = (Pair<Pair<String, String>, Order>) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(read != null) {
					String username, password;
					username = read.getKey().getKey();
					password = read.getKey().getValue();
					if((username.equals("admin")) && (password.equals("admin"))){
						Order order = read.getValue();
						if(order != null){
							lock.lock();
							for(Product p : order){
								boughtProducts.add(p);
							}
							oos.writeObject(order);
							oos.flush();
							System.out.println("Order eingegangen:");
							System.out.print(order);
							System.out.println("Orders Gesamt:");
							System.out.println("==========================");
							System.out.print(boughtProducts);
							System.out.println("==========================");
							System.out.println("Gesamtzahl: " + boughtProducts.getQuantity());
							System.out.println("Gesamtwert: " + boughtProducts.getSum() + "EUR");
							lock.unlock();
						}

					}
					else
						System.out.println("Bad login");
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
