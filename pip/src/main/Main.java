package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import metadata.Metadata;
import connection.CloudNotAvailableException;
import connection.Provider;
import connection.ProviderCloud;
import connection.ProviderWebdav;
import coding.Coder;
import coding.EmptyCoder;
import splitMerge.Splitter;
import utilities.Packet;
import allocation.AllocationStrategy;
import allocation.ChosenCloud;

public class Main {
	public static void main(String[] args) {
		if(args.length<4){
			System.out.print("fileName filePath clouds ?");
			try {
				String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
				args = line.split("[ ]");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String fileName = args[0];
		String filePath = args[1] + "/" + fileName;
		String[] clouds = new String[args.length-2];
		for(int i=0; i<clouds.length; i++){
			clouds[i] = args[i+2];
		}
		Coder coder = new EmptyCoder();
		AllocationStrategy strategy = new ChosenCloud();
		
		try {
			strategy.upLoad(filePath, 2*(clouds.length), clouds, coder);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
