package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import model.Order;
import model.Product;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class Client {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public Client() {
		try{
			Socket serverCon = new Socket("localhost", 6666);
			this.oos = new ObjectOutputStream(serverCon.getOutputStream());
			this.ois = new ObjectInputStream(serverCon.getInputStream());
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void placeOrder(Pair<Pair<String, String>, Order> preparedOrder){
		(new OutcomingThread(oos, preparedOrder)).start();
	}

	public void getHistory(ObservableList<Product> bought) {
		(new IncomingThread(ois, bought)).start();
	}

}

class OutcomingThread extends Thread {

	private ObjectOutputStream oos;
	private Pair<Pair<String, String>, Order> preparedOrder;

	public OutcomingThread(ObjectOutputStream oos, Pair<Pair<String, String>, Order> preparedOrder) {
		this.oos = oos;
		this.preparedOrder = preparedOrder;
	}

	public void run() {
		try {
			oos.writeObject(this.preparedOrder);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class IncomingThread extends Thread {

	private ObjectInputStream ois;
	private ObservableList<Product> bought;

	public IncomingThread(ObjectInputStream ois, ObservableList<Product> bought) {
		this.ois = ois;
		this.bought = bought;
	}

	public void run() {
		while(true){
			try {
				final Order order = (Order)ois.readObject();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(order != null){
							for(fpt.com.Product p : order) {
								bought.add((Product)p);
							}
						}
					}
				});
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}

}