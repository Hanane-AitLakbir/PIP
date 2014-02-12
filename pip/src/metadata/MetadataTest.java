package metadata;

import org.junit.Test;

public class MetadataTest {

	@Test
	public void test() {
		Metadata meta = new Metadata();
		System.out.println(meta==null);
		meta.addContent("name", "toto");
		meta.addContent("checksum", "ef4562eac56");
		System.out.println(meta.browse("name"));
		meta.serialize("D:/PIPworkspace/pipCopy2/metadata/meta.json");
		System.out.println("serialization OK");
	}

}
