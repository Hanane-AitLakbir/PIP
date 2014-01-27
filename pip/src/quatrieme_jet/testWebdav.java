package quatrieme_jet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;


public class testWebdav {

	public static void main(String[] args) throws HttpException, IOException {
		HostConfiguration hostConfig = new HostConfiguration();
		hostConfig.setHost("http://localhost/owncloud/remote.php/webdav/"); 
		HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		int maxHostConnections = 20;
		params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
		connectionManager.setParams(params);    
		HttpClient client = new HttpClient(connectionManager);
		Credentials creds = new UsernamePasswordCredentials("letspip", "weloveajj");
		client.getState().setCredentials(AuthScope.ANY, creds);
		client.setHostConfiguration(hostConfig);


		System.out.println("-----------------------COPY A FILE-----------------------");
		CopyMethod  copy=new CopyMethod("http://localhost/owncloud/remote.php/webdav/ownCloudUserManual.pdf", "http://localhost/owncloud/remote.php/webdav/ownCloudUserManual2.pdf", true);
		client.executeMethod(copy);

		System.out.println(copy.getStatusCode() + " "+ copy.getStatusText());

		System.out.println("-----------------------UPLOAD A FILE-----------------------");
		PutMethod upload = new PutMethod("http://localhost/owncloud/remote.php/webdav/firstFileUploaded.txt");
		File f = new File("C:/Users/aït-lakbir/Desktop/PIPTest/uploadTest.txt");
		if(f.exists()) {
			RequestEntity requestEntity = new FileRequestEntity(f,"txt");
			upload.setRequestEntity(requestEntity);
			client.executeMethod(upload);
			System.out.println(upload.getStatusCode() + " "+ upload.getStatusText());
			upload.releaseConnection();
		}
		

		System.out.println("-----------------------DOWNLOAD A FILE-----------------------");
		GetMethod httpMethod = new GetMethod("http://localhost/owncloud/remote.php/webdav/firstFileUploaded.txt");
		client.executeMethod(httpMethod);
		System.out.println(httpMethod.getStatusCode() + " "+ httpMethod.getStatusText());
		System.out.println("content response : " + httpMethod.getResponseContentLength());
		if (httpMethod.getResponseContentLength() > 0) {
			InputStream inputStream = httpMethod.getResponseBodyAsStream();
			File responseFile = new File("C:/Users/aït-lakbir/Desktop/PIPTest/firstDownload.txt");
			OutputStream outputStream = new FileOutputStream(responseFile);
			byte buf[]=new byte[1024];
			int len;
			while ( (len = inputStream.read(buf)) > 0 ) {
				outputStream.write(buf,0,len);
			}
			outputStream.close();
			inputStream.close();
		}
		System.out.println(httpMethod.getStatusCode() + " "+ httpMethod.getStatusText());
	}

}
