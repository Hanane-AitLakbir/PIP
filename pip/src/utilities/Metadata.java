package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The object Metadata manage the access to a file JSON with the list of storage devices used (Cloud and Webdav server) and their characteristics.
 * @author hanane
 *
 */
public class Metadata{

	private String metadataPath;

	public Metadata(String metadataPath){
		// TODO remove comment b_slash
		//this.metadataPath = metadataPath;
		this.metadataPath = "D:/PIPworkspace/pipCopy/metadata/metadataStorage.json"; //for tests only
	}

	public boolean addFile(String nameFile, Packet[] packets){
		FileInputStream fileStream;
		int numOfFiles;
		try {
			fileStream = new FileInputStream("D:/PIPworkspace/pipCopy/metadata/metadataStorage.json");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(fileStream);
			
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
