package splitAndMerge;
import java.io.File;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileSplitter splitter = new FileSplitter();
		File[] files = splitter.splitFile("test.png", 5*1024);
		splitter.assembleFiles(files, "restored.png");
	}

}
