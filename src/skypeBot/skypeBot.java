package skypeBot;

//API - https://github.com/taksan/skype-java-api/tree/master/src/main/java/com/skype

import skypeBot.guiClass;
import skypeBot.skypeClass;

public class skypeBot {
	//Some general variables
	public static String title = "Skype Bot";
	public static int[] guiSize = {700,400};
	
    public static void main(String[] args) throws Exception {
    	//Build the GUI
    	@SuppressWarnings("unused")
		guiClass gui = new guiClass(title,guiSize);

    	@SuppressWarnings("unused")
    	skypeClass skype = new skypeClass();
    	
    	
    	/*
    	if(skypeClass.SendTo("cube.dude","Go Fuck a Duck!")){
            System.out.println("Message sent!");
    	}else{
            System.out.println("Message couldn't be deliverd...");
        }
        */
    }
    
}