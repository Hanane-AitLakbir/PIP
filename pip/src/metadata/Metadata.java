package metadata;

/*
 * Replaces metadataElement and metadataPacket 
 */
public class Metadata {
	private MetadataSerializer serializer;
	
	public Metadata(){
		serializer = new JSonSerializer();
	}
	
	public Metadata(MetadataSerializer serializer){
		this.serializer = serializer;
	}
	
	public boolean addContent(String key, String value){
		return serializer.addContent(key,value);
	}

	public String browse(String string){
		return serializer.browse(string);
	}
	
	public void serialize(String metadataPath){
		serializer.serialize(metadataPath);
	}
}
