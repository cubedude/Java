package smartSignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataClass {
	private static String link = "http://smartconnections.ee/sig/";
	private static String uname;
	private static String pwd;
	private static String error;
	private static String response;

    public void setName(String name) {
    	uname = name;
    }
    
    public void setPassword(String password) {
    	pwd = password;
    }

    public String getError() {
    	return error;
    }

    public String getResponse() {
    	return response;
    }
    
    public int connect()  {
    	if(uname.length() < 3 || pwd.length() < 3) return 1;
    		
    	try {
    		response = sendPost();
    	} 
    	catch (MalformedURLException e) { 
    		error = e.getMessage();
	    	return 2;
    	} 
    	catch (IOException e) {  
    		error = e.getMessage(); 
    		return 3;
    	}
    	catch (Exception e) {   
    		error = e.getMessage();
    		return 4;
    	}
    	return 0;
    }
    
    private String sendPost() throws Exception {
		URL obj = new URL(link);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "uname="+uname+"&pwd="+pwd;
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + link);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer resp = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			resp.append(inputLine);
		}
		in.close();
		
		if(resp.toString().length() < 3) throw new Exception();
		
		return resp.toString();
	}
}
