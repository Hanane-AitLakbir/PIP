package connection;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import utilities.Cloud;
import utilities.Packet;

public class ProviderCloud implements Provider{

	OAuthConsumer consumer;
	OAuthProvider provider;
	public ProviderCloud(Cloud cloud) {
		
	}
	


	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void upload(Packet packet) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Packet download(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
