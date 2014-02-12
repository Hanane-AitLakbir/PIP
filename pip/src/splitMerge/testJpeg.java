package splitMerge;

import java.io.FileNotFoundException;
import java.io.IOException;

import utilities.Packet;

public class testJpeg {

	public static void main(String[] args) {
		try {
			Packet[] packets = Splitter.split(1,"C:/Users/aït-lakbir/Desktop/PIPTest/uploadTest.txt",1);
			for(Packet p : packets){
				System.out.println(p.getName());
				byte[] bytes = p.getData();
				for(byte b : bytes){
					System.out.print(b + ", ");
				}
				
				
			}
			Merger.merge("C:/Users/aït-lakbir/Desktop/PIPTest/uploadTest2.txt",packets);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		

	}

}
