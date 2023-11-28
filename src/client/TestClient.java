package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import my_interface.IServer;
import my_interface.Itest;



public class TestClient {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			IServer chatServer = (IServer) registry.lookup("server");
			
			new ClientImp(chatServer, "hai");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
