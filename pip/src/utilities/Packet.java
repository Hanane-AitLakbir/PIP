package utilities;

import metadata.MetadataPacket;

public class Packet {

	private String name;
	private byte[] data;
	public Packet(String name,byte[] data){
		this.name = name;
		this.data = data;
	}
	public MetadataPacket getMetadata() {
		return new MetadataPacket(ComputeChecksum.getChecksum(data), name);
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}

	public String getExtension(){
		return name.substring(name.lastIndexOf(".")+1);
	}

}

