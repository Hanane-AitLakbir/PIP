package connection;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.Packet;

public class ProviderWebdavTest {

	@Test
	public void testGetNameExtension() {
		String name = "aTesSouhait.jpeg";
		assertEquals("aTesSouhait", name.substring(0, name.lastIndexOf('.')));
		assertEquals("jpeg",name.substring(name.lastIndexOf('.')+1, name.length()));
	}
	
	@Test
	public void testDownloadFile(){
		ProviderWebdav provider = new ProviderWebdav("account1");
		provider.connect();
		Packet packet = provider.download("firstFileUploaded.txt");
		assertEquals("txt", packet.getExtension());
		assertEquals("firstFileUploaded", packet.getName());
		System.out.println( new String(packet.getData()));
		
	}

}
