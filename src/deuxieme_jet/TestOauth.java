package deuxieme_jet;

	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;
//import oauth.signpost.signature.SignatureMethod;

	public class TestOauth {

		public static final String URL_REQUEST_TOKEN = "https://api.dropbox.com/1/oauth/request_token";
		public static final String URL_AUTH = "https://www.dropbox.com/1/oauth/authorize";
		public static final String URL_ACCESS = "https://api.dropbox.com/1/oauth/access_token";

		public static final String CONSUMER_KEY = "fg5jq4pn2dc6vk3";
		public static final String CONSUMER_SECRET = "8xs4ulixs6pii08";

		public static void main(String[] args) throws IOException{

			
			HttpURLConnection request = null;
			BufferedReader rd = null;
			StringBuilder response = null;

			try{
				URL endpointUrl = new URL(URL_AUTH);
				request = (HttpURLConnection)endpointUrl.openConnection();
				request.setRequestMethod("POST");
				
				try{
				        OAuthConsumer consumer = new DefaultOAuthConsumer(
			        		CONSUMER_KEY, CONSUMER_SECRET);
				        consumer.setTokenWithSecret("", "");
				        consumer.sign(request);
				} catch(OAuthMessageSignerException ex){
					System.out.println("OAuth Signing failed - " + ex.getMessage());
				} catch(OAuthExpectationFailedException ex){
					System.out.println("OAuth failed - " + ex.getMessage());
				}
				
				request.connect();

				rd  = new BufferedReader(new InputStreamReader(request.getInputStream()));
				response = new StringBuilder();
				String line = null;
				while ((line = rd.readLine()) != null){
					response.append(line + '\n');
				}
			} catch (MalformedURLException e) {
				System.out.println("Exception: " + e.getMessage());
				//e.printStackTrace();
			} catch (ProtocolException e) {
				System.out.println("Exception: " + e.getMessage());
				//e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Exception: " + e.getMessage());
				//e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				//e.printStackTrace();
			} finally {
				try{
					request.disconnect();
				} catch(Exception e){
	}

				if(rd != null){
					try{
						rd.close();
					} catch(IOException ex){
	}
					rd = null;
				}
			}

			System.out.println( (response != null) ? response.toString() : "No Response");
		}
	}
