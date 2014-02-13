package quatrieme_jet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.Iterator;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class test1 {

	public static void main(String[] args) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {

		//app id and secret key ID delivered by Dropbox
		final String APP_KEY ="fg5jq4pn2dc6vk3";
		final String APP_SECRET="8xs4ulixs6pii08";

		OAuthConsumer consumer = new DefaultOAuthConsumer(APP_KEY, APP_SECRET);

		OAuthProvider provider = new DefaultOAuthProvider(
				"https://api.dropbox.com/1/oauth/request_token",
				"https://api.dropbox.com/1/oauth/access_token",
				"https://www.dropbox.com/1/oauth/authorize");

		System.out.println("Fetching request token...");

		String authUrl = provider.retrieveRequestToken(consumer, "");

		System.out.println("Request token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());

		System.out.println("Now visit:\n" + authUrl + "\n... and grant this app authorization");
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(authUrl)); //to open the web browser and the website page ;p

		System.out.println("Hit ENTER when you're done:");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String verificationCode = br.readLine();

		System.out.println("Fetching access token...");

		provider.retrieveAccessToken(consumer, verificationCode.trim());

		//Displays access token and token secret = validity until the owner of the account revokes it.
		System.out.println("Access token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());

		//Gets account data back
		System.out.println("-----------------------NEW REQUEST1-----------------------");
		URL url = new URL("https://api.dropbox.com/1/account/info");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();

		consumer.sign(request);

		System.out.println("Sending request...");
		System.out.println("Access token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());
		request.connect();

		System.out.println("Response: " + request.getResponseCode() + " "
				+ request.getResponseMessage());

		print_content(request);


		//Gets a file (only txt for now) 
		System.out.println("\n-----------------------NEW REQUEST2-----------------------");
		URL url2 = new URL("https://api-content.dropbox.com/1/files/dropbox/firstFileUpload.txt");
		HttpURLConnection request2 = (HttpURLConnection) url2.openConnection();
		request2.setDoOutput(true);

		request2.setRequestMethod("GET");
		request2.addRequestProperty("rev", "");

		consumer.sign(request2);
		System.out.println("Sending request...");
		request2.connect();

		System.out.println("Response: " + request2.getResponseCode() + " "
				+ request2.getResponseMessage());
		print_content(request2); //il ne reste plus qu'à l'écrire dans un nouveau fichier et l'enregistrer

		//Gets metadata (all files stored onto the cloud, file sizes, ...)

		System.out.println("\n-----------------------NEW REQUEST3-----------------------");
		URL url3 = new URL("https://api.dropbox.com/1/metadata/dropbox");
		HttpURLConnection request3 = (HttpURLConnection) url3.openConnection();

		request3.setRequestMethod("GET");

		consumer.sign(request3);
		System.out.println("Sending request...");
		request3.connect();

		System.out.println("Response: " + request3.getResponseCode() + " "
				+ request3.getResponseMessage());
		print_content(request3);

		//Sends a txt file
		//		File file  = new File("C:/Users/aït-lakbir/Desktop/PIPTest/uploadTest.txt");
		//		Scanner scanner = new Scanner(file);

		System.out.println("\n-----------------------NEW REQUEST4-----------------------");
		//URL url4 = new URL("https://api-content.dropbox.com/1/files/dropbox/uploadFile.txt");
		URL url4 = new URL("https://api-content.dropbox.com/1/files_put/dropbox/uploadFile.txt?param=UTF-8");
		HttpURLConnection request4 = (HttpURLConnection) url4.openConnection();
		request4.setDoOutput(true);
		
		request4.setRequestMethod("PUT");
		request4.addRequestProperty("locale ", "");
		request4.addRequestProperty("overwrite", "");
		request4.addRequestProperty("parent_rev", "");
		
		consumer.sign(request4);
		System.out.println("Sending request...");
		
		FileInputStream fileInput = new FileInputStream("C:/Users/aït-lakbir/Desktop/PIPTest/uploadTest.txt");
		DataOutputStream outputStream = new DataOutputStream(request4.getOutputStream());
		byte[] bytes = new byte[10];
		int length = fileInput.available(); //estimated length of the file
		while(length-10>0){
			fileInput.read(bytes, 0, 10);
			outputStream.write(bytes);
			length-=10;
		}
		fileInput.read(bytes, 0, length); 
		outputStream.write(bytes); //sends the rest of the file
		request4.connect();
		
		System.out.println("Response: " + request4.getResponseCode() + " "
				+ request4.getResponseMessage());
		print_content(request4);


	}


	private static void print_content(HttpURLConnection request){
		if(request!=null){

			try {

				System.out.println("****** Content of the URL ********");			
				BufferedReader br = 
						new BufferedReader(
								new InputStreamReader(request.getInputStream()));

				String input;

				while ((input = br.readLine()) != null){
					System.out.println("******TEST JSON OBJECT ******");
					JsonFactory factory = new JsonFactory();
					JsonParser parser = factory.createParser(input);


					while(parser.nextToken()!=JsonToken.END_OBJECT){
						System.out.println(parser.getCurrentName() + " " + parser.getValueAsString());
					}
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}




