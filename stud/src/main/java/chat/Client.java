package chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javafx.collections.ObservableList;

public class Client {

	private Remote client;
//	private ObservableList<String> messages;
	private ChatService server;

	public Client(String name, ObservableList<String> messages) throws RemoteException, MalformedURLException, NotBoundException {
//		this.messages = messages;
		client = new ChatClient(name, messages);
		Naming.rebind("//localhost:1099/"  + name, client);
		server = (ChatService)Naming.lookup("server");
		server.login(name);
	}

	public void send(String message) {
		try {
			server.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
