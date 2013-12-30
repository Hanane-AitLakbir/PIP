package deuxieme_jet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test {

	public static void main(String[] args) throws IOException {
		URL url = new URL("http://www.google.fr");
		URLConnection conn = url.openConnection();
		conn.connect();
		InputStreamReader content 
		    = new InputStreamReader(conn.getInputStream());
		for (int i=0; i != -1; i = content.read())
		{
		    System.out.print((char) i);  
		} 

	}

}
