package data;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Taavi Metsvahi
 * Class that communicates with web
 */

public class DataClass {
	//Object variables
	private static String link = "http://smartconnections.ee/sig/";
	private static String sigLink = "http://smartconnections.ee/";
	private static String uname;
	private static String pwd;
	private static String option;
	private static String error;
	private static String list;
	private static String signature;

    /**
     * Sets this objects uname
     * @param String name
     */
    public void setName(String name) {
    	uname = name;
    }

    /**
     * Sets this objects password
     * @param String password
     */
    public void setPassword(String password) {
    	pwd = password;
    }

    /**
     * Sets this objects selected option
     * @param String opt
     */
    public void setOption(String opt) {
    	option = opt;
    }

    /**
     * Returns latest error
     * @return String error
     */
    public String getError() {
    	return error;
    }

    /**
     * Returns latest list
     * @return String list
     */
    public String getList() {
    	return list;
    }

    /**
     * Returns latest signature
     * @return String signature
     */
    public String getSignature() {
    	return signature;
    }

    /**
     * Returns the link where you can add signatures
     * @return String sigLink
     */
    public String getSigLink() {
    	return sigLink;
    }
    /**
     * Connects to web and respond a status code
     * @param int type => 1 = get signature; 0 = get list of signatures
     * @return int status
     */
    public int connect(int type)  {
    	//If data is to short, return error
    	if(uname.length() < 3 || pwd.length() < 3) return 1;

    	//Try to post with an option
    	try {
    		if(type == 1){ //If option has been selected
    			signature = sendPost(type);
    		}
    		else{
    			list = sendPost(type);
    		}
    	} 
    	//Catch error, save the message and return status code
    	catch (MalformedURLException e) { 
    		error = e.getMessage();
	    	return 2;
    	} 
    	//Catch error, save the message and return status code
    	catch (IOException e) {  
    		error = e.getMessage(); 
    		return 3;
    	}
    	//Catch error, save the message and return status code
    	catch (Exception e) {   
    		error = e.getMessage();
    		return 4;
    	}
    	//Return 0 (success)
    	return 0;
    }

    /**
     * Connects to web using POST and returns the output
     * @param int type => 0 = get list; 1 = get signature
     * @return String response
     */
    private String sendPost(int type) throws Exception {
    	//New URL object
		URL obj = new URL(link);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//Add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    	//Give the data as post
		String urlParameters = "uname="+uname+"&pwd="+pwd;
    	//Add option if it is specifyed
		if(type == 1){
	    	//If there is an option, add it to post
			if(option.length() > 0){
				urlParameters += "&option="+option;
			} else{ 
				//if not, throw an error
				throw new Exception("No option selected");
			}
		}
		
		//Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

    	//Read response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer resp = new StringBuffer();

    	//Proccess it line by line
		while ((inputLine = in.readLine()) != null) {
			resp.append(inputLine);
		}
		in.close();

    	//If responce was too short, return error
		if(resp.toString().length() < 1) throw new Exception("Failed authentication");
		
    	//Return the responce
		return resp.toString();
	}

    /**
     * Opens webpage if possuble
     * @param String links - Link to go to
     */
	public void openLinks(String links) {
		//get enviroment
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	        	//Follow the link
	            desktop.browse(new URL(links).toURI());
	        } catch (Exception e) {
	        	//If failed, print it
	            e.printStackTrace();
	        }
	    }
	}

    /**
     * Processing raw list into actual list
     * @param String rawList
     * @return ObservableList<String> options
     */
	public ObservableList<String> processList(String rawList) {
		//initiate variable
		ObservableList<String> options = null;
		
		//if raw list is an actual list
		if(list.length() > 1){
			//Split the string 
			String[] optArray = rawList.split(";");
			//Add to array
	        options = FXCollections.observableArrayList(optArray);
		}
        //Return
	    return options;
	}
	

}
