package splitMerge;

import java.io.FileNotFoundException;
import java.io.IOException;

import utilities.Packet;

public class testJpeg {

	public static void main(String[] args) {
		try {
			Packet[] packets = Splitter.split(1,"C:/Users/Ludovic/Desktop/testDossier.rar",8);
//			for(Packet p : packets){
//				System.out.println(p.getName());
//				byte[] bytes = p.getData();
//				for(byte b : bytes){
//					System.out.print(b + ", ");
//				}
//				
//				
//			}
			Merger.merge("C:/Users/Ludovic/Desktop/testDossier1.rar",packets);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		

	}

}
