package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import my_interface.Itest;

public class TestServer extends UnicastRemoteObject implements Itest {

	protected TestServer() throws RemoteException {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sayHello() throws RemoteException {
		System.out.println("in tren server");
		
	}
	
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			TestServer server = new TestServer();
			registry.rebind("test", server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
