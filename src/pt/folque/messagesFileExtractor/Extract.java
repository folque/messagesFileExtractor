package pt.folque.messagesFileExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Extract {

	public static void main(String[] args) {
		if(args.length > 1){
			extract(args[0], args[1]);
			System.out.println("Success");
		}
		else {
			System.err.println("You're missing one or more arguments");
			System.err.println("Arguments: <source file path> <destination path>");
		}
	}
	
	public static void extract(String filename, String output){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = null;
			Set<String> collectionOfStrings = new LinkedHashSet<String>();
			while ((line = reader.readLine()) != null) {
				if(line.length() > 0 && !line.substring(0, 1).equals("#")){
					if(line.contains("=")){
					    String extracted = line.substring(line.indexOf("=") + 1).trim();
					    if(extracted.length() > 0){
					    	collectionOfStrings.add(extracted);
					    }
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			for (String elem : collectionOfStrings) {
				sb.append(elem);
				sb.append("\n");
			}
			sb.deleteCharAt(sb.length() - 1);
			writeToFile(sb.toString(), output);
		} catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch(IOException e){}
		}
		
	}
	
	public static void writeToFile(String content, String output){
	    File file = new File(output);
	    try {
	        file.createNewFile();
	        Files.write(content, file, Charsets.UTF_8);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

}
