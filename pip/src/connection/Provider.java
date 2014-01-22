package connection;

import java.io.File;

public interface Provider {

	public void upload(File file);
	public File download(String name);
	public void connect();
}
