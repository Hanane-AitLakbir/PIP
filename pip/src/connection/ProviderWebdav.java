package connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import metadata.JSonSerializer;
import metadata.Metadata;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;

import utilities.Packet;

public class ProviderWebdav implements Provider {

	private HttpClient client;
	private String serverName;

	public ProviderWebdav(String serverName){
		this.serverName = serverName;
	}

	@Override
	public void connect() throws CloudNotAvailableException {
		HostConfiguration hostConfig = new HostConfiguration();
		hostConfig.setHost("http://localhost/owncloud/remote.php/webdav/"); 
		HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		int maxHostConnections = 20;
		params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
		connectionManager.setParams(params);    
		client = new HttpClient(connectionManager);
		TreeMap<String, String> id = getId();
		Credentials creds = new UsernamePasswordCredentials(id.get("id"), id.get("password"));
		client.getState().setCredentials(AuthScope.ANY, creds);
		client.setHostConfiguration(hostConfig);
	}

	@Override
	public void upload(Packet packet) throws CloudNotAvailableException{
		Metadata meta = packet.getMetadata();
		File mFile;
		PutMethod upload = new PutMethod("http://localhost/owncloud/remote.php/webdav/" + packet.getName());
		PutMethod uploadMeta = new PutMethod("http://localhost/owncloud/remote.php/webdav/" + packet.getName()+".json");
		File f;
		try {
			mFile = File.createTempFile("meta", ".tmp");
			f = File.createTempFile(packet.getName(), ".tmp");
			meta.serialize(mFile.getPath());
			
			FileOutputStream output = new FileOutputStream(f);
			
			output.write(packet.getData());
			output.close();

			if(f.exists() && mFile.exists()) {
				//Sending of the packet file
				RequestEntity requestEntity = new FileRequestEntity(f,packet.getExtension());
				upload.setRequestEntity(requestEntity);
				client.executeMethod(upload);
				System.out.println(upload.getStatusCode() + " "+ upload.getStatusText());
				upload.releaseConnection();
				//Sending of the metadata file
				RequestEntity requestEntity2 = new FileRequestEntity(mFile,".json");
				uploadMeta.setRequestEntity(requestEntity2);
				client.executeMethod(uploadMeta);
				System.out.println(uploadMeta.getStatusCode() + " "+ uploadMeta.getStatusText());
				uploadMeta.releaseConnection();
			}
			
			
		} catch (IOException e1) {
			throw new CloudNotAvailableException();
		}


	}

	@Override
	public Packet download(String name) throws CloudNotAvailableException{
		Packet packet=null;
		String url = "http://localhost/owncloud/remote.php/webdav/"+name;
		System.out.println(url);
		GetMethod httpMethod = new GetMethod(url);
		try {
			client.executeMethod(httpMethod);
			System.out.println(httpMethod.getStatusCode() + " "+ httpMethod.getStatusText());
			System.out.println("content response : " + httpMethod.getResponseContentLength());
			ArrayList<Byte> dataList = new ArrayList<Byte>();

			if (httpMethod.getResponseContentLength() > 0) {
				InputStream inputStream = httpMethod.getResponseBodyAsStream();
				byte buf[]=new byte[1024];
				int len;
				while ( (len = inputStream.read(buf)) > 0 ) {
					for(byte b : buf){
						dataList.add(b);
					}
				}
				inputStream.close();
			}
			byte[] data = new byte[dataList.size()];
			for(int i = 0;i<data.length;i++){
				data[i] = dataList.get(i);
			}
			packet = new Packet(name.substring(0, name.lastIndexOf('.')),data);
		} catch (HttpException e) {
			throw new CloudNotAvailableException();
		} catch (IOException e) {
			throw new CloudNotAvailableException();
		}
		
		return packet;
	}

	private TreeMap<String,String> getId(){
		TreeMap<String, String> tree = new TreeMap<String,String>();
		Metadata meta = new JSonSerializer("C:/Users/a�t-lakbir/Desktop/PIPTest/cloud/"+serverName+".json").deserialize();
		tree.put("id", meta.browse("id"));
		tree.put("password", meta.browse("password"));
		return tree;
	}
}

