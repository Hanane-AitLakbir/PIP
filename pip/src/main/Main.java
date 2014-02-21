package main;

import coding.Coder;
import coding.EmptyCoder;
import allocation.AllocationStrategy;
import allocation.ChosenCloud;

public class Main {
	public static void main(String[] args) {
		String fileName = "testTexte.txt";
		String filePath = "files test/" + fileName;
		Coder coder = new EmptyCoder();
		String[] listStorageLocation = new String[]{"dropbox"};
		AllocationStrategy strategy = new ChosenCloud();

		strategy.upLoad(fileName, 4, listStorageLocation, coder);
		
//		Provider provider = new ProviderCloud("dropbox");
//		Metadata fileList = new Metadata();
//		
//		try {
//			//sending the file
//			fileList.addContent("1234",fileName);
//			Packet[] packets = Splitter.split(filePath,4);
//			//provider.connect();
//			for(Packet p : packets){
//				provider.upload(p);
//			}
//			fileList.serialize("metadata/files List.json");
//			System.out.println("sending done");
//			
//			//upload the file
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (CloudNotAvailableException e) {
//			e.printStackTrace();
//		}
		
	}
}
