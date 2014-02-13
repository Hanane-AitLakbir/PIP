package utilities;

import metadata.Metadata;

public class Packet {

	private String name;
	private byte[] data;
	public Packet(String name,byte[] data){
		this.name = name;
		this.data = data;
	}
	public Metadata getMetadata() {
		Metadata meta = new Metadata();
		meta.addContent("name", name);
		meta.addContent("checksum", ComputeChecksum.getChecksum(data));
		return meta;
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

