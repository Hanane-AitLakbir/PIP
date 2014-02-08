package utilities;

import java.util.TreeMap;

import org.junit.Test;

public class MetadataParserTest {

	@Test
	public void testSearchWebdav() {
		MetadataParser parser = new MetadataParser("");
		TreeMap<String, String> map =parser.searchWebdav("account1");
		System.out.println(map);
		System.out.println("parsing done");
	}

}
