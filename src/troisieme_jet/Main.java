package troisieme_jet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.*;

public class Main {
	public static void  main(String[] args) throws IOException, DbxException{
		
		// Gets access rights from a Dropbox account
		
		// from adding an app on Dropbox website
		String APP_KEY ="fg5jq4pn2dc6vk3";
		String APP_SECRET="8xs4ulixs6pii08";
		
		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig(
            "Premier Jet", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
		
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        
        DbxAuthFinish authFinish = webAuth.finish(code);
		
        DbxClient client = new DbxClient(config, authFinish.accessToken);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
        
        
        
        
        
	}
}
