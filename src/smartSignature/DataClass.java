package smartSignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Taavi Metsvahi
 */

public class DataClass {
	//Object variables
	private static String link = "http://smartconnections.ee/sig/";
	private static String uname;
	private static String pwd;
	private static String option;
	private static String error;
	private static String response;

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
     * Returns latest response
     * @return String response
     */
    public String getResponse() {
    	return response;
    }

    /**
     * Connects to web and respond a status code
     * @return int status
     */
    public int connect()  {
    	//If data is to short, return error
    	if(uname.length() < 3 || pwd.length() < 3) return 1;

    	//Try to post with an option
    	try {
    		response = sendPost(option);
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
     * @param String option
     * @return String response
     */
    private String sendPost(String option) throws Exception {
    	//New URL object
		URL obj = new URL(link);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//Add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    	//Give the data as post
		String urlParameters = "uname="+uname+"&pwd="+pwd;
		
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
		if(resp.toString().length() < 3) throw new Exception("Failed authentication");

    	//Return the responce
		return resp.toString();
	}
}
