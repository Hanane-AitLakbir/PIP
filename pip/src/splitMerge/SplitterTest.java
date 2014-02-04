package splitMerge;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import utilities.Packet;

public class SplitterTest {

	@Test
	public void testSplit() {
		
		/*
		 * For the test, you need to create a file .txt with '0000111122223333'. You should get 4 packets '0000' '1111' '2222' '3333'
		 */
			try {
				Packet[] packets = Splitter.split(1, "C:/Users/aït-lakbir/Desktop/PIPTest/testSplit.txt", 4);
				for(Packet p : packets){
					System.out.println("packet : " + p.getName());
					System.out.println(new String(p.getData()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("splitting done");
		
	}

}
