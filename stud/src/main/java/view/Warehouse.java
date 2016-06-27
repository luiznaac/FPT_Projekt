package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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

		try (ServerSocket server = new ServerSocket(tcpPort)) {
			int connections = 0;
			while (true) {
				try {
					Socket socket = server.accept();
					System.out.println(socket);
					connections++;
					new ClientThread(connections, socket, lock, boughtProducts).start();
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

	private int connectionNumber;
	private Socket socket;
	private Lock lock;
	private Order boughtProducts;

	public ClientThread(int connectionNumber, Socket socket, Lock lock, Order boughtProducts) {
		this.connectionNumber = connectionNumber;
		this.socket = socket;
		this.lock = lock;
		this.boughtProducts = boughtProducts;
	}

	public void run(){
		try (InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream()) {
			ObjectInputStream ois = new ObjectInputStream(in);
			ObjectOutputStream oos = new ObjectOutputStream(out);
//			System.out.println("cato os bagui");
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
							System.out.println("Order eingegangen:");
							System.out.print(order);
							System.out.println("Orders Gesamt:");
							System.out.println("==========================");
							System.out.print(boughtProducts);
							System.out.println("==========================");
							System.out.println("Gesamtzahl: " + boughtProducts.getQuantity());
							System.out.println("Gesamtwert: " + boughtProducts.getSum() + "EUR");
							lock.unlock();
							oos.writeObject(order);
							oos.flush();
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
