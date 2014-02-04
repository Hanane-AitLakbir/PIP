package splitMerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utilities.Packet;

public class Splitter {

	public static Packet[] split(int fileId, String fileName, int nbrOfPackets) throws FileNotFoundException, IOException{
		Packet[] packets = new Packet[nbrOfPackets];
		File file = new File(fileName);
		assert(file.isFile());
		//TODO throw exception
		int size = (int) (file.length()/nbrOfPackets + 1);
		FileInputStream stream = new FileInputStream(file);
		byte[] data;
		for(int i=0; i<nbrOfPackets-1; i++){
			packets[i] = new Packet();
			data = new byte[size];
			stream.read(data, 0, size);
			packets[i].setData(data);
			packets[i].setChecksum(getChecksum(data));
			packets[i].setName(fileId+""+i);
		}
		packets[nbrOfPackets-1] = new Packet();
		data = new byte[(int) (file.length()-size*(nbrOfPackets-1.))];
		stream.read(data, 0, data.length);
		packets[nbrOfPackets-1].setData(data);
		packets[nbrOfPackets-1].setChecksum(getChecksum(data));
		packets[nbrOfPackets-1].setName(fileId+""+(nbrOfPackets-1));
		stream.close();
		return packets;
	}
	
	private static String getChecksum(byte[] data){
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
