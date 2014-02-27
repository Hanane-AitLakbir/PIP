package allocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import metadata.JSonSerializer;
import metadata.Metadata;
import metadata.MetadataSerializer;

import connection.CloudNotAvailableException;
import connection.Provider;
import connection.ProviderCloud;
import connection.ProviderFactory;

import coding.Coder;


import splitMerge.Splitter;
import utilities.Cloud;
import utilities.ComputeChecksum;
import utilities.Packet;

public class ChosenCloud implements AllocationStrategy{

	public boolean upLoad(String fileName, int nbrOfPackets, String[] clouds, Coder coder) throws FileNotFoundException, IOException{
		MetadataSerializer metadata = new JSonSerializer();
		Packet[] splittedPackets = Splitter.split(fileName, nbrOfPackets);
		Packet[] codedPackets = coder.encode(splittedPackets);
		int nbrOfClouds = clouds.length;
		Provider provider;
		int i=0;
		metadata.addContent("name", fileName);
		metadata.addContent("checksum", ComputeChecksum.getChecksum(new File(fileName)));
		metadata.addContent("number of packets", ""+codedPackets.length);
		while(i<codedPackets.length){
			provider = ProviderFactory.getProvider(clouds[i%nbrOfClouds]);
			try {
				provider.upload(codedPackets[i]);
				i++;
				metadata.addContent("cloud"+i, clouds[i%nbrOfClouds]);
			} catch (CloudNotAvailableException e) {
				clouds[i%nbrOfClouds] = askForCloud();
			}
		}
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		metadata.serialize("metadata/files/"+name+".json");
		return true;
	}

	public String askForCloud() {
		System.out.print("Specify another cloud :");
		try {
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
