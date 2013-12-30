package quatrieme_jet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import org.omg.CORBA.portable.InputStream;

import oauth.signpost.OAuth;
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

		//ID et clé secrète de l'app sur Dropbox
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

		System.out.println("Access token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());

		//récupération des données du compte Dropbox
		System.out.println("-----------------------NEW REQUEST-----------------------");
		URL url = new URL("https://api.dropbox.com/1/account/info");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();

		consumer.sign(request);

		System.out.println("Sending request...");
		request.connect();

		System.out.println("Response: " + request.getResponseCode() + " "
				+ request.getResponseMessage());

		print_content(request);

		//récupération d'un fichier
		System.out.println("\n-----------------------NEW REQUEST-----------------------");
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
		
		//récupération du metadata
		
		System.out.println("\n-----------------------NEW REQUEST-----------------------");
		URL url3 = new URL("https://api.dropbox.com/1/metadata/dropbox");
		HttpURLConnection request3 = (HttpURLConnection) url3.openConnection();
		
		request3.setRequestMethod("GET");
		
		consumer.sign(request3);
		System.out.println("Sending request...");
		request3.connect();

		System.out.println("Response: " + request3.getResponseCode() + " "
				+ request3.getResponseMessage());
		print_content(request3);
		

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
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}




