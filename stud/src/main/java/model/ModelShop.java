package model;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import serialization.*;

public class ModelShop extends ModifiableObservableListBase<Product> {

	private ProductList list;
	private Context context;
	private Order order;
	private ObservableList<Product> bought;
	private ObservableList<String> messages;
	private Client client;
	private chat.Client chatClient;
	private boolean orderReset;

	public ModelShop(){
		list = new ProductList();
		bought = FXCollections.observableArrayList(new ArrayList<Product>());
		client = null;
		orderReset = false;
		messages = FXCollections.observableArrayList(new ArrayList<String>());
	}

	@Override
	public Product get(int index) {
		return (Product) list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	protected void doAdd(int index, Product e) {
		list.add(index, e);
	}

	@Override
	protected Product doSet(int index, Product element) {
		return null;
	}

	@Override
	protected Product doRemove(int index) {
		return (Product) list.remove(index);
	}

	public void setStrategy(int option){
		switch(option){
			case 0:
				context = new Context(new BinaryStrategy("products.ser"));
				break;
			case 1:
				context = new Context(new XMLStrategy("products.xml"));
				break;
			case 2:
				context = new Context(new XStreamStrategy("products.xml"));
				break;
			case 3:
				context = new Context(new JDBCConnector());
				break;
			case 4:
				context = new Context(new OpenJPAConnector());
				break;
		}
	}

	public void load(){
		ProductList read = new ProductList();
		boolean flag = true;
		while(flag){
			try {
				read.add((Product)context.load());
			} catch (Exception e) {
				flag = false;
			}
		}
		if(!read.isEmpty()){
			list.clear();
			for(fpt.com.Product p : read)
				this.add((Product) p);
		}
		context.close();
	}

	public void save(){
		for(fpt.com.Product p : list)
			context.save((Product)p);
		context.close();
	}

	public Order getOrder() {
		return order;
	}

	public void addToOrder(Product p, String quantity){
		if(order == null || orderReset){
			order = new Order();
			orderReset = false;
		}
		try {
			int q = Integer.parseInt(quantity);
			if(p.getQuantity() < q)
				System.out.println("Incorrect amount");
			else{
				Product toAdd = new Product(p.getId(), p.getName(), p.getPrice(), q);
				order.add(toAdd);
				System.out.println(toAdd.getName() + " x " + toAdd.getQuantity() + " added to the order");
			}
		} catch(Exception e){
			System.out.println("Bad Input");
		}
	}

	public ObservableList<Product> getBoughtList() {
		return bought;
	}

	public ObservableList<String> getMessagesList() {
		return messages;
	}

	public void placeOrder(Optional<Pair<String, String>> login) {
		if(client == null){
			client = new Client();
			client.getHistory(bought);
		}
		if(login.isPresent()){
			Pair<Pair<String, String>, Order> preparedOrder = new Pair<>(login.get(), order);
			client.placeOrder(preparedOrder);
			orderReset = true;
		}
		else
			System.out.println("Abgebrochen");
	}

	public void login(String username){
		if(chatClient != null)
			System.out.println("Already logged in");
		else {
			if(username.equals(""))
				System.out.println("Invalid username");
			else {
				try {
					chatClient = new chat.Client(username, messages);
					System.out.println(username + " logged in");
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void sendMessage(String message) {
		if(chatClient == null)
			System.out.println("Please login");
		else
			chatClient.send(message);
	}

}
