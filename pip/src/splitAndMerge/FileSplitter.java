package splitAndMerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileSplitter {

	public File[] splitFile(String fileName, int chunk){
		File bigFile = new File(fileName);
		assert(!bigFile.isDirectory());
		String name = fileName.split("[.]")[0];
		String extension = "."+fileName.split("[.]")[1];
		long size = bigFile.length();
		int filesNbr = (int) (size/chunk) + 1;
		File[] files = new File[filesNbr];
		try{
			FileInputStream input = new FileInputStream(bigFile);
			FileOutputStream output;
			File smallFile;
			byte[] data = new byte[chunk];
			for(int i=0; i<filesNbr-1; i++){
				input.read(data, 0, chunk);
				smallFile = new File(name+"_"+i+extension);
				smallFile.createNewFile();
				output = new FileOutputStream(smallFile);
				output.write(data);
				output.flush();
				output.close();
				files[i] = new File(smallFile.getPath());
			}
			int extra = (int) (size - chunk*(filesNbr-1));
			data = new byte[extra];
			input.read(data, 0, extra);
			smallFile = new File(name+"_"+(filesNbr-1)+extension);
			smallFile.createNewFile();
			output = new FileOutputStream(smallFile);
			output.write(data);
			output.flush();
			output.close();
			input.close();
			files[filesNbr-1] = smallFile;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return files;
	}
	
	public File assembleFiles(File[] files, String newName){
		File newFile = new File(newName);
		try{
			newFile.createNewFile();
			FileOutputStream output = new FileOutputStream(newFile);
			FileInputStream input;
			byte[] data;
			for(int i=0; i<files.length; i++){
				input = new FileInputStream(files[i]);
				data = new byte[(int) files[i].length()];
				input.read(data);
				output.write(data);
				output.flush();
				input.close();
			}
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return newFile;
	}
}
