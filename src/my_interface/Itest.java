package my_interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Itest extends Remote {

	public void sayHello() throws RemoteException;
}
