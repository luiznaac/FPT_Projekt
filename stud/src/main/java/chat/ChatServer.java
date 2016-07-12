package chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatService {

	private static final long serialVersionUID = 1L;
	private List<String> users;

	public ChatServer() throws RemoteException {
		super();
		users = new ArrayList<String>();
	}

	@Override
	public void login(String name) throws RemoteException {
		users.add(name);
	}

	@Override
	public void logout(String name) throws RemoteException {
		users.remove(name);
	}

	@Override
	public void send(String message) throws RemoteException {
		System.out.println(message);
		for(String user : users){
			try {
				((ClientService)Naming.lookup(user)).send(message);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> getUserList() throws RemoteException {

		return null;
	}

}
