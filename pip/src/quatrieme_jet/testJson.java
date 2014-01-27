package quatrieme_jet;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;

public class testJson {

	public static void main(String[] args) throws JsonParseException, IOException {
		
		JsonFactory factory = new JsonFactory();
		JsonGenerator g = factory.createGenerator(new File("D:/PIPworkspace/pipCopy/metadata/metadata.json"), JsonEncoding.UTF8);
		
		g.writeStartObject();
		g.writeFieldName("cloud");
		g.writeStartArray();
		g.writeStartObject();
		g.writeStringField("name", "");
		g.writeStringField("token", "");
		g.writeEndObject();
		g.writeStartObject();
		g.writeStringField("name", "");
		g.writeStringField("token", "");
		g.writeEndObject();
		g.writeEndArray();
		g.writeFieldName("webdav");
		g.writeStartArray();
		g.writeStartObject();
		g.writeStringField("name", "");
		g.writeStringField("token", "");
		g.writeEndObject();
		g.writeStartObject();
		g.writeStringField("name", "");
		g.writeStringField("token", "");
		g.writeEndObject();
		g.writeEndArray();
		g.writeEndObject();

		g.close();
	}

}
