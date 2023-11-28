package client;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import my_interface.Client;
import my_interface.IClient;
import my_interface.IServer;
import my_interface.Room;
public class ClientImp implements IClient, Serializable  { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String myName;

	public IClient client;
    
    ClientView clientView;
    List<Client> onlineClients = new ArrayList<Client>();
    IServer chatServer;
   
    List<Client> chattersClients = new ArrayList<Client>();
    List<Room> listRooms = new ArrayList<>();
    
    Map<String, byte[]> listFileContents = new HashMap<String, byte[]>();
    
    public ClientImp(IServer chatServer, String myName) throws RemoteException {
        clientView = new ClientView(this, myName);
        this.chatServer = chatServer;
        this.myName = myName;
        UnicastRemoteObject.exportObject(this, 0);
        Client client = new Client(myName, this);
        this.chatServer.registerClient(client);
        this.chatServer.unicastMessage(myName + " is connected");
	}
	// đăng ký client để nhắn tín
	@Override
    public void registerClient(IClient client) throws RemoteException {
        this.client = client;
    }
	@Override
	public void retriveMessage(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("retcive: "+msg);
		if (msg.startsWith("[")) {
			System.out.println("recive message: " + msg);
			String groupName = msg.substring(1, msg.indexOf("]"));
			this.clientView.setMessage(groupName, msg.substring(msg.indexOf("]") + 1));
		} else {
			String nameSender = msg.substring(0, msg.indexOf(":"));
			this.clientView.setMessage(nameSender, msg);
		}
	}
	@Override
	public void updateOnlineClients(List<Client> onlineClients) throws RemoteException {
		this.onlineClients = onlineClients;
		Vector<String> listNames = new Vector<>();
		
		for (Client object : onlineClients) {
			listNames.add(object.getName());
		}
		System.out.println(listNames.toString());
		this.clientView.listChatterOnline.setListData(listNames);
	}
	
	
	// add ban đầu
	public boolean addChatter(String name) throws RemoteException {
		// TODO Auto-generated method stub
		Client clientSelected = null;
		for (Client client : onlineClients) {
			if (name.equals(client.getName())) {
				clientSelected = client;
				break;
			}
		}
		IClient iClient = clientSelected.getiClient();
		boolean a = iClient.connectChatter(myName);
		if (!a) return false;
		chattersClients.add(clientSelected);
		// lấy 
		
		return true;
	}
	
	public boolean addChatter(String name, int i) throws RemoteException{
		Client clientSelected = null;
		for (Client client : onlineClients) {
			if (name.equals(client.getName())) {
				clientSelected = client;
				break;
			}
		}
		chattersClients.add(clientSelected);
		return true;
	}
	
	public boolean isChatter(String name) {
		for (Client client : chattersClients) {
			if (name.equals(client.getName())) {	
				return true;
			}
		}
		return false;
	}
	
	public void sendMessage(String nameReciver, String msg) throws RemoteException {
		
		
		// check is client or group
		if (isChatter(nameReciver)) {
			// send to chatter
			Client client = null;
			for (Client item : chattersClients) {
				if (item.getName().equals(nameReciver)) {
					client = item;
					break;
				}
			}
			client.getiClient().retriveMessage(msg);
		} else {
			// send to group
			Room room = null;
			for (Room item: listRooms) {
				if (item.getRoomName().equals(nameReciver)) {
					room = item;
					break;
				}
			}
			
			String message = "[" + nameReciver + "]" + msg; 		
			System.out.println(message);
			for (Client item : room.getListClients()) {
				Client client = getChatterByName(item.getName());
				System.out.println(onlineClients.indexOf(client));
				client.getiClient().retriveMessage(message);
			}	
		}	
	}
	
	// send file
	public void sendFile(String nameReciver, byte[] data, String fileName) throws RemoteException {
		
		if (isChatter(nameReciver)) {
			// send to chatter
			Client client = getChatterByName(nameReciver);
			client.getiClient().retriveFile(data, fileName, myName);
			
		} else {
			// send to group
			
		}
	}
	
	@Override
	public boolean connectChatter(String nameChatter) throws RemoteException {
		if (isChatter(nameChatter)) return true;
		// thông báo muốn coonect
		return this.clientView.notifyNewChatter(nameChatter);
	}
	
	public void openCreateGroupView() {
		new GroupView(onlineClients, this);
	}
	
	public void addGroup(String roomName, List<String> listNames) throws RemoteException {
		List<Client> listClients = new ArrayList<Client>();
		
		for (String nameChatter : listNames) {
			Client chatter = getChatterByName(nameChatter);
			if (chatter != null) listClients.add(chatter);
		}
		
		Room room = new Room(roomName, listClients);
		
		listRooms.add(room);
		
		// update view room
		clientView.updateGroupList(listRooms);
		clientView.insertTabChat(room);
		
		// update view cho những thằng còn lại
		
		Client thisClient = new Client(myName, this);
		
		for (Client client : listClients) {
			IClient iClient = client.getiClient();
			List<Client> newList = new ArrayList<Client>(listClients);
			newList.remove(client);
			newList.add(thisClient);
			iClient.createGroup(roomName, newList);
		}
	}
	
	private Client getChatterByName(String nameChatter) {
		for (Client client : onlineClients) {
			if (client.getName().equals(nameChatter)) return client;
		}
		return null;
	}
	
	// tạo group từ xa 
	@Override
	public void createGroup(String roomName, List<Client> listClients) throws RemoteException {
		// TODO Auto-generated method stub
		Room room = new Room(roomName, listClients);
		listRooms.add(room);
		// update view room
		clientView.updateGroupList(listRooms);
		clientView.insertTabChat(room);
	}
	
	@Override
	public void addClient(Client client) throws RemoteException {
		onlineClients.add(client);
		Vector<String> listNames = new Vector<>();
		for (Client object : onlineClients) {
			listNames.add(object.getName());
		}
		this.clientView.listChatterOnline.setListData(listNames);
	}
	
	@Override
	public void retriveFile(byte[] data, String fileName, String sender) throws RemoteException {
		
		// TODO Auto-generated method stub
		System.out.println("File name sender: " + sender + fileName);
		this.listFileContents.put(fileName, data);
		
		// set into ui
		this.clientView.setMessageFile(sender, fileName);
		
	}
	
	
	
	
}