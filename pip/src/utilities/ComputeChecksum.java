package utilities;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ComputeChecksum {
	public static String getChecksum(File file){
		return null; 
	}
	public static String getChecksum(byte[] data){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
			md.update(data);
			byte[] mdbytes = md.digest();

			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return new String(sb);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
