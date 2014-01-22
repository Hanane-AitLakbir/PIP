package connection;

import java.io.File;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import utilities.Cloud;

public class ProviderCloud implements Provider{

	OAuthConsumer consumer;
	OAuthProvider provider;
	public ProviderCloud(Cloud cloud) {
		
	}
	
	@Override
	public void upload(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File download(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

}
