package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import metadata.Metadata;
import connection.CloudNotAvailableException;
import connection.Provider;
import connection.ProviderCloud;
import connection.ProviderWebdav;
import encoding.Encoder;
import splitMerge.Splitter;
import utilities.Packet;
import allocation.AllocationStrategy;

public class Main {
	public static void main(String[] args) {
		String fileName = "testTexte.txt";
		String filePath = "files test/" + fileName;
		AllocationStrategy strategy;
		Encoder coder;
		Provider provider = new ProviderCloud("dropbox");
		Metadata fileList = new Metadata();
		
		try {
			//sending the file
			fileList.addContent("1234",fileName);
			Packet[] packets = Splitter.split(1234,filePath,4);
			//provider.connect();
			for(Packet p : packets){
				provider.upload(p);
			}
			fileList.serialize("metadata/files List.json");
			System.out.println("sending done");
			
			//upload the file
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CloudNotAvailableException e) {
			e.printStackTrace();
		}
	}
}
