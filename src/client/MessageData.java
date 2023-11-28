package client;

public class MessageData {
	public String sender;
	public String type;
	public String content;

	public MessageData(String sender, String type, String content) {
		this.sender = sender;
		this.type = type;
		this.content = content;
	}
}