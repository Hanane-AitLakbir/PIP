package splitMerge;

import java.io.IOException;

import org.junit.Test;

import utilities.Packet;

public class MergerTest {

	@Test
	public void testMerge() {
		/*
		 * I create 6 packets '0000' '1111' '2222' etc. In the final file, you should get '000011112222....5555'
		 */
		Packet[] packets = new Packet[6];
		Packet p;
		String word,letter;
		for(int i=0;i<6;i++){
			p = new Packet();
			p.setName(("packet"+i));
			//made "a l'arrache", I will improve it later
			letter = String.valueOf(i);
			word = letter + letter + letter + letter ;
			p.setData(word.getBytes());
			packets[i] = p;
		}
		
		try {
			Merger.merge("C:/Users/Ludovic/Desktop/testMerge.txt", packets);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("merging done");
	}

}
