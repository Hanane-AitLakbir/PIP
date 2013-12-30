package premier_jet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileConfigReader {
	String login,password;
	public void read() throws FileNotFoundException{
		File fileConfig = new File("res/mdp.txt");
		Scanner scanner = new Scanner(fileConfig);

		login = scanner.nextLine();
		password = scanner.nextLine();

		scanner.close();
	}
	
	public String getLogin(){
		return login;
	}
	
	public String getPassword(){
		return password;
	}
}
