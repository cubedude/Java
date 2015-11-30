package skypeBot;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

public class guiClass {
	public static int consoleInputHeight = 30;
	public static int windowElementHeight = 40;
    //SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
	public static  SimpleDateFormat timeFormat = new SimpleDateFormat ("hh:mm:ss");
	
	public static JFrame guiFrame;
	public static JTextField consoleInput;
	public static JTextArea consoleArea;
	
	guiClass(String title, int[] guiSize)  {
    	//Create GUI
		createGUI(title, guiSize);
		
    	//Make GUI working
    	addListeners();
	}
	
	public void createGUI(String title, int[] guiSize){
    	 guiFrame = new JFrame();
    	 
    	 guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	 guiFrame.setTitle(title); 
    	 guiFrame.setSize(guiSize[0],guiSize[1]); 
    	 guiFrame.setLayout(new BorderLayout());

         consoleInput = new javax.swing.JTextField();
         consoleInput.setPreferredSize(new Dimension(guiSize[0], consoleInputHeight));
         
    	 consoleArea = new javax.swing.JTextArea();
    	 consoleArea.setPreferredSize(new Dimension(guiSize[0],(guiSize[1]-consoleInputHeight-windowElementHeight)));
    	 consoleArea.setEditable(false);

         guiFrame.add(consoleInput, BorderLayout.PAGE_START);
    	 guiFrame.add(consoleArea, BorderLayout.PAGE_END);
    	 guiFrame.setVisible(true);
    }

    public void writeToConsole(String inputText)  {
    	consoleArea.setText(timeFormat.format(new Date())+": "+inputText+"\n"+consoleArea.getText());
    }

    public void addListeners()  {
    	//Focus console input
   	 	guiFrame.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		    	consoleInput.requestFocus();
		    }
		 }); 
   	 	
   	 	//Dont let console input lose focus
   	 	consoleInput.addFocusListener(new FocusListener() {
	    	 public void focusLost(FocusEvent e) {
	    		 consoleInput.requestFocus();
	    	 }

	    	 public void focusGained(FocusEvent arg0) {

	    	 };
   	 	});
   	 	
   	 	//Get text from console input and output to system
   	 	consoleInput.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	writeToConsole(consoleInput.getText());
            	consoleInput.setText("");
            }
        });
   	 
    }
    
}
