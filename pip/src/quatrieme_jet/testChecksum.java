package quatrieme_jet;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class testChecksum {

	public static void main(String[] args) throws Exception {
		File file = new File("C:/Users/aït-lakbir/Desktop/PIPTest/uploadTest.txt");
		MessageDigest md = MessageDigest.getInstance("SHA1");
		@SuppressWarnings("resource")
		FileInputStream input = new FileInputStream(file);
		byte[] data = new byte[1024];

		int bytesRead = 0;
		while((bytesRead=input.read(data))!=-1){
			md.update(data, 0, bytesRead);
		}
		byte[] mdbytes = md.digest();

		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		System.out.println("Digest(in hex format) " + sb.toString());


	}

}
