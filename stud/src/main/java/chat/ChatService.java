package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {

	void login(String name) throws RemoteException;

	void logout(String name) throws RemoteException;

	void send(String message) throws RemoteException;

	List<String> getUserList() throws RemoteException;

}
