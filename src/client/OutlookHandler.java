package client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Taavi Metsvahi
 * Class that deals with Microsoft Outlook files
 */

public class OutlookHandler {
	private static String tutorial = "http://www.howto-outlook.com/howto/signatures.htm#newsignature";
	private static String path = System.getenv("AppData")+"/Microsoft/Signatures";

    /**
     * Returns tutorial link on how to create signatures
     * @return String tutorial
     */
	public String getTutorialLink(){
		return tutorial;
	}

	
    /**
     * Returns possuble signatures to replace
     * @param String rawList
     * @return ObservableList<String> options
     */
	public ObservableList<String> fetchSignatures() {
		//Variables
		Set<String> hs = new HashSet<>();
		File sigFolder = new File(path);
		File[] sigFiles = sigFolder.listFiles();

		//Watch for every file
		if(sigFiles != null){
			for (File file : sigFiles) {
			    if (file.isFile()) {
					//Get name, strip the extension, add to list
			    	String fileName = file.getName();
			    	String onlyFileName = stripExtension(fileName);
			    	hs.add(onlyFileName);
			    }
			}
		}

		//Set to needed format and return
		return FXCollections.observableArrayList(hs);
	}

    /**
     * Replaces signature with contents
     * @param String signature
     * @param String content
     * @return int status
     */
	public int writeSignature(String signature, String content){
		try
		{
			//Get the .htm file
		    String filename= signature+".htm";
			//Open it up
		    FileWriter fw = new FileWriter(path+"/"+filename);
			//Write the content on it
		    fw.write(content);
			//Close the file
		    fw.close();
			//Return success
			return 0;
		}
		//If error occured, return it
		catch(IOException ioe)
		{
		    return 1;
		}
	}
	
    private static String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;
        // Get position of last '.'.
        int pos = str.lastIndexOf(".");
        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;
        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }
	
}
