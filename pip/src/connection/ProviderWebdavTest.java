package connection;

import static org.junit.Assert.*;
import metadata.Metadata;

import org.junit.Test;

import utilities.Packet;

public class ProviderWebdavTest {

	
	public void testGetNameExtension() {
		String name = "aTesSouhait.jpeg";
		assertEquals("aTesSouhait", name.substring(0, name.lastIndexOf('.')));
		assertEquals("jpeg",name.substring(name.lastIndexOf('.')+1, name.length()));
	}

	
	public void testDownloadFile(){
		ProviderWebdav provider = new ProviderWebdav("account1");
		Packet packet;
		try {
			provider.connect();
			packet = provider.download("firstFileUploaded.txt");
			assertEquals("txt", packet.getExtension());
			assertEquals("firstFileUploaded", packet.getName());
			System.out.println( new String(packet.getData()));
		} catch (CloudNotAvailableException e) {
			e.printStackTrace();
		}


	}

	@Test
	public void testUploadPacket(){
		/*
		 * Creation of metadata Webdav 
		 */
//		Decomment if metadata account1 doesn't exist
//		Metadata metadata = new Metadata();
//		metadata.addContent("name", "account1");
//		metadata.addContent("id", "letspip");
//		metadata.addContent("password","weloveajj");
//		metadata.serialize("C:/Users/aït-lakbir/Desktop/PIPTest/cloud/account1.json");
		
		byte[] data = "Aujourd'hui il fait beau".getBytes();
		Packet packet = new Packet("2032.txt", data);
		Provider provider = new ProviderWebdav("account1");
		try {
			provider.connect();
			provider.upload(packet);
		} catch (CloudNotAvailableException e) {
			e.printStackTrace();
		}
	}

}
