package connection;

import metadata.JSonSerializer;
import metadata.Metadata;
import utilities.Packet;

public class ProviderFactory {

	static public Provider getProvider(String name){
		Metadata meta = new JSonSerializer("metadata/cloud/list.json").deserialize();
		System.out.println((meta.browse(name)));
		if(meta.browse(name).equals("cloud")){
			Provider provider =  new ProviderCloud(name);
			try {
				provider.connect();
				return provider;
			} catch (CloudNotAvailableException e) {
				e.printStackTrace();
			}
		}
		else if(meta.browse(name).equals("webdav")){
			Provider provider = new ProviderWebdav(name);
			try {
				provider.connect();
				return provider;
			} catch (CloudNotAvailableException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	public static void main(String[] args) {
		Provider provider = ProviderFactory.getProvider("account1");
		Packet packet;
		try {
			provider.connect();
			packet = provider.download("firstFileUploaded.txt");
			System.out.println( new String(packet.getData()));
		} catch (CloudNotAvailableException e) {
			e.printStackTrace();
		}
	}
	
}
