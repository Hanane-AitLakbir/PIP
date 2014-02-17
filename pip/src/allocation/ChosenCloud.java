package allocation;

import java.io.FileNotFoundException;
import java.io.IOException;

import connection.CloudNotAvailableException;
import connection.Provider;
import connection.ProviderCloud;

import coding.Coder;


import splitMerge.Splitter;
import utilities.Cloud;
import utilities.Packet;

public class ChosenCloud implements AllocationStrategy{
	
	public boolean upLoad(String fileName, int nbrOfPackets, String[] clouds, Coder coder){
		try {
			Packet[] splittedPackets = Splitter.split(fileName, nbrOfPackets);
			Packet[] codedPackets = coder.encode(splittedPackets);
			int nbrOfClouds = clouds.length;
			Provider provider;
			int i=0;
			while(i<codedPackets.length){
				provider = new ProviderCloud(clouds[i%nbrOfClouds]);
				try {
					provider.upload(codedPackets[i]);
					i++;
				} catch (CloudNotAvailableException e) {
					clouds[i%nbrOfClouds] = askForCloud();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String askForCloud() {
		return null;
	}
}
