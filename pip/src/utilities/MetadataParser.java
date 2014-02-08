package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The object Metadata manage the access to a file JSON with the list of storage devices used (Cloud and Webdav server) and their characteristics.
 * @author hanane
 *
 */
public class MetadataParser{

	private String metadataPath;
	private ObjectMapper mapper;
	private JsonNode root;

	public MetadataParser(String metadataPath){
		//this.metadataPath = metadataPath; // TODO remove comment in final code
		this.metadataPath = "D:/PIPworkspace/pipCopy/metadata/metadataStorage.json"; //for tests only
		FileInputStream fileStream;
		try {
			fileStream = new FileInputStream(this.metadataPath);
			 mapper = new ObjectMapper();
			 root = mapper.readTree(fileStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public TreeMap<String,String> searchWebdav(String name){
		TreeMap<String,String> result = new TreeMap<String,String>();
		JsonNode aNode = root.findPath(name);
		Iterator<String> list = aNode.fieldNames();
		JsonNode current;
		String st;
		while(list.hasNext()){
			st = list.next();
			current = aNode.findValue(st);
			System.out.println("\t\t" + st + " " + current.asText());
			result.put(st, current.asText());
		}
		return result;
		
	}
	
	public boolean addFile(String nameFile, Packet[] packets){
		int numOfFiles;
		try {
			
			JsonNode fileNode = root.findPath("file");
			numOfFiles = fileNode.path("numOfFiles").intValue();
			
			ObjectNode newFile = ((ObjectNode) fileNode).putObject(nameFile);
			
			newFile.put("id", (numOfFiles+1));
			newFile.put("checksum","");
			
			for(Packet p : packets ){
				createNodeFromPacket(newFile, p);
			}
			
			((ObjectNode) fileNode).put("numOfFiles", (numOfFiles+1));
			
			mapper.writeValue(new File(metadataPath), root);
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	private void createNodeFromPacket(JsonNode parentNode, Packet packet){
		ObjectNode newPacket = ((ObjectNode) parentNode).putObject(packet.getName());
		newPacket.put("storageLocation", packet.getCloud());
		newPacket.put("checksum", packet.getChecksum());
	}

}
