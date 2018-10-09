package demo2.V2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class FileReadIn {

	public static String readInFile(String FilePath) throws Exception {

		FileReader file = new FileReader(FilePath);
		BufferedReader reader = new BufferedReader(file);

		String text = "";
		String line = reader.readLine();
		while (line != null) {
			text += line;
			line = reader.readLine();
		}
		reader.close();
		return text;

	}

}