package connection;

import utilities.Packet;

public interface Provider {

	public void upload(Packet packet);
	public Packet download(String name);
	public void connect();
}
