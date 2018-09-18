package demo1;

import java.util.*;
import java.security.MessageDigest;
import java.io.File;
//import java.util.Scanner; //for old scanner implementation
import java.io.BufferedReader;
import java.io.FileReader;



public class SHA256HashDemoV2 {
	public static void main(String[] args) throws Exception {
		
		
		List<BufferedReader> XMLFiles = new ArrayList<BufferedReader>();
		List<String> HashList = new ArrayList<String>();
		
		
		File dir = new File("C:/Users/mburg/Desktop/Capstone Project"); //for testing purposes, change this line to your directory path
		File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		    BufferedReader reader = new BufferedReader(new FileReader(child));
		    XMLFiles.add(reader);
		    }
		  }
	

		for(BufferedReader XMLFile : XMLFiles) {
			String text = "";
			String line = XMLFile.readLine();
			while(line!=null) {
					text += line;
					line = XMLFile.readLine();
				}
			XMLFile.close();
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
					sb.append(String.format("%02x", b & 0xff));
					}
			System.out.println("SHA256 Hash: " + sb.toString());
			HashList.add(sb.toString());
		}
		
		//Note: this has N^2 runtime and as such should only be used for testing purposes on small data sets.
		for (int i = 0; i <HashList.size(); i++) {
//			System.out.println(HashList.get(i)); // used for testing
			for (int j = 0; j < HashList.size(); j++) {
				if (HashList.get(i).equals(HashList.get(j)) && i!=j) {
					System.out.format("hash %d verified with hash %d \n", i,j);	
					}
			}
		
		//System.out.println(text); //used for testing
		}

	}
}
