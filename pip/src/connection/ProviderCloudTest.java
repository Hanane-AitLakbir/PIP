package connection;

import org.junit.Test;

import utilities.Packet;

public class ProviderCloudTest {

	public void test() {
		/*
		 * Creation of metadata Dropbox 
		 */
//		Decomment if metadata account1 doesn't exist
//		Metadata metadata = new Metadata();
//		metadata.addContent("name", "dropbox1");
//		metadata.addContent("app_key", "fg5jq4pn2dc6vk3");
//		metadata.addContent("app_secret","8xs4ulixs6pii08");
//		metadata.addContent("requestToken", "https://api.dropbox.com/1/oauth/request_token");
//		metadata.addContent("accessToken", "https://api.dropbox.com/1/oauth/access_token");
//		metadata.addContent("authorize", "https://www.dropbox.com/1/oauth/authorize");
//		metadata.addContent("upload", "https://api-content.dropbox.com/1/files_put/dropbox/");
//		metadata.addContent("download", "https://api-content.dropbox.com/1/files/dropbox/");
//		
//		metadata.serialize("C:/Users/aït-lakbir/Desktop/PIPTest/cloud/dropbox.json");
		
		Provider provider = new ProviderCloud("dropbox");
		try {
			provider.connect();
		} catch (CloudNotAvailableException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Test
	public void testUpload(){
		byte[] data = "Aujourd'hui il fait beau".getBytes();
		Packet packet = new Packet("2032.txt", data);
		Provider provider = new ProviderCloud("dropbox");
		try {
			provider.upload(packet);
			System.out.println("packet uploaded");
		} catch (CloudNotAvailableException e) {
			e.printStackTrace();
		}
	}
}
