package quatrieme_jet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class testParseJSON {
	
	public static void main(String[] args) throws JsonParseException, IOException {
		FileInputStream fileStream = new FileInputStream("D:/PIPworkspace/pipCopy/metadata/metadataStorage.json");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(fileStream);
		Iterator<String> list = node.fieldNames();
		while(list.hasNext()){
			System.out.println(list.next());
		}
		JsonNode wNode =node.path("webdav");
		Iterator<String> list2 = wNode.fieldNames();
		while(list2.hasNext()){
			System.out.println(list2.next());
		}
		
		JsonNode aNode = node.findPath("account1");
		Iterator<String> list3 = aNode.fieldNames();
		JsonNode current;
		String st;
		while(list3.hasNext()){
			st = list3.next();
			current = aNode.findValue(st);
			System.out.println("\t\t" + st + " " + current.asText());
		}
		
		JsonNode fNode = node.findPath("file");
		Iterator<String> list4 = fNode.fieldNames();
		while(list4.hasNext()){
			st = list4.next();
			current = fNode.findValue(st);
			System.out.println("\t\t\t" + st + " " + current.asText());
		}
		
		ObjectNode newFile = ((ObjectNode) fNode).putObject("name2.txt");
		newFile.put("id", 2);
		newFile.put("partition", 3);
		newFile.putObject("20");
		newFile.putObject("21");
		newFile.putObject("22");
		
		JsonNode fNode2 = node.findPath("file");
		Iterator<String> list5 = fNode2.fieldNames();
		while(list5.hasNext()){
			st = list5.next();
			current = fNode2.findValue(st);
			System.out.println("\t\t\t\t" + st + " " + current.asText());
		}
		
		mapper.writeValue(new File("D:/PIPworkspace/pipCopy/metadata/metadataStorage.json"), node);
		fileStream.close();
	}
	
}
