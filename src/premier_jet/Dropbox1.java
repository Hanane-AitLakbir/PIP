package premier_jet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class Dropbox1 {
	private SecureRandom random = new SecureRandom();
	private Date date = new Date();
	public static void main(String[] args)
	{
		new Dropbox1().testIt();
	}

	private void testIt(){

		String https_url = "https://api.dropbox.com/1/oauth/request_token";
		URL url;

//		int millis = (int) System.currentTimeMillis() * -1;
//        int time = (int) millis / 1000;
		
		String oauth_signature = "8xs4ulixs6pii08";
		String oauth_signature_method = "PLAINTEXT";
		String oauth_consumer_key = "fg5jq4pn2dc6vk3";
		String oauth_nonce = getNonce();
		String oauth_timestamp = getTimestamp();

//		String oauth_nonce = String.valueOf(millis);
//		String oauth_timestamp = String.valueOf(time);
		
		try {

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

			String signatureBaseString = "POST&" + 
					URLEncoder.encode(https_url, "UTF-8")+ 
					"&" + "%26oauth_consumer_key%3D"+ URLEncoder.encode(oauth_consumer_key, "UTF-8")+ 
					"%26oauth_nonce%3D"+ URLEncoder.encode(oauth_nonce, "UTF-8")+ 
					"%26oauth_signature_method%3D"+ URLEncoder.encode(oauth_signature_method, "UTF-8")+ 
					"%26oauth_timestamp%3D"+ URLEncoder.encode(oauth_timestamp, "UTF-8")+ 
					"%26oauth_version%3D" + URLEncoder.encode("1.0", "UTF-8");

			System.out.println("Signature Base: "+signatureBaseString);

			String signingKey = oauth_signature + "&";
			String signature = null;
			try {
				Mac mac = Mac.getInstance("HmacSHA1");
				SecretKeySpec secret = new SecretKeySpec(signingKey.getBytes(),
						"HmacSHA1");
				mac.init(secret);
				byte[] digest = mac.doFinal(signatureBaseString.getBytes());
				//We then use the composite signing key to create an oauth_signature from the signature base string
				signature = digest.toString();
				System.out.println("The resultant oauth_signature is: " +signature);
			} catch (Exception ex1) {
				ex1.printStackTrace();
				System.exit(0);
			}

			String oAuthParameters = "OAuth " 
					+ ", oauth_consumer_key=\""+ URLEncoder.encode(oauth_consumer_key, "UTF-8") + "\""
					+ ", oauth_nonce=\""+ URLEncoder.encode(oauth_nonce, "UTF-8") + "\""
					+ ", oauth_signature=\""+ URLEncoder.encode(oauth_signature, "UTF-8") + "\""
					+ ", oauth_signature_method=\""+ URLEncoder.encode(oauth_signature_method, "UTF-8") + "\""
					+ ", oauth_timestamp=\""+ URLEncoder.encode(oauth_timestamp, "UTF-8") + "\""
					+ ", oauth_version=\"" + URLEncoder.encode("1.0", "UTF-8")+ "\"";
			System.out.println("oAuth Parameters are " +oAuthParameters);


			con = (HttpsURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			//Set the oAuth params in header
			con.addRequestProperty("Authorization", oAuthParameters);

			String bodyParams =
	                  "&oauth_consumer_key="+ URLEncoder.encode(oauth_consumer_key, "UTF-8") 
	                 + "&oauth_nonce="+ URLEncoder.encode(oauth_nonce, "UTF-8")
	                  + "&oauth_signature=" + URLEncoder.encode(signature, "UTF-8")
	                  + "&oauth_signature_method="+URLEncoder.encode(oauth_signature_method, "UTF-8") 
	                    + "&oauth_timestamp="+URLEncoder.encode(oauth_timestamp, "UTF-8")
	                 + "&oauth_version=" + URLEncoder.encode("1.0", "UTF-8");
	         System.out.println("Parameters for body are " +oAuthParameters);
	         
	         OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
	         writer.write(bodyParams);
	         writer.flush();

	          
			
			//System.out.println(con.getHeaderFields());
			
			
			//			System.out.println(con.getRequestProperties());

			//dumpl all cert info
			//print_https_cert(con);

			System.out.println(con.getResponseMessage());
			//dump all the content
			//print_content(con);
//						InputStreamReader content 
//						= new InputStreamReader(con.getInputStream());
//						for (int i=0; i != -1; i = content.read())
//						{
//							System.out.print((char) i);  
//						} 

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void print_https_cert(HttpsURLConnection con){

		if(con!=null){

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs){
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " 
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " 
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}

		}

	}

	private void print_content(HttpsURLConnection con){
		if(con!=null){

			try {

				System.out.println("****** Content of the URL ********");			
				BufferedReader br = 
						new BufferedReader(
								new InputStreamReader(con.getInputStream()));

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



	public String getNonce()
	{
		return new BigInteger(130, random).toString(32);
	}

	public String getTimestamp(){
		return String.valueOf(date.getTime()/1000);
	}
}
