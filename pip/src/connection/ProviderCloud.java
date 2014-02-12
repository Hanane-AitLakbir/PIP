package connection;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import utilities.Cloud;
import utilities.Packet;

public class ProviderCloud implements Provider{

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private String nameCloud; 
	
	public ProviderCloud(String nameCloud) {
		this.nameCloud = nameCloud;
	}
	


	@Override
	public void connect() {
		
		
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
