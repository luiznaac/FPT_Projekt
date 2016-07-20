package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class ChatClient extends UnicastRemoteObject implements ClientService {

	private static final long serialVersionUID = 1L;
	private ObservableList<String> messages;
	private String name;

	protected ChatClient(String name, ObservableList<String> messages) throws RemoteException {
		super();
		this.messages = messages;
		this.name = name;
	}

	@Override
	public void send(String message) throws RemoteException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				messages.add(message);
			}
		});
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

}
