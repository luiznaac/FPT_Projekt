package chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javafx.collections.ObservableList;

public class Client {

	private Remote client;
	private ChatService server;
	private String name;

	public Client(String name, ObservableList<String> messages) throws RemoteException, MalformedURLException, NotBoundException {
		client = new ChatClient(name, messages);
		this.name = name;
		Naming.rebind("//localhost:1099/benutzername/"  + name, client);
		server = (ChatService)Naming.lookup("server");
		server.login(name);
	}

	public void send(String message) {
		try {
			server.send(name + ": " + message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
