package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {

	void send(String message) throws RemoteException;
	String getName() throws RemoteException;

}
