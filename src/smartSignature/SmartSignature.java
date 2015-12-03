package smartSignature;

import client.OutlookHandler;
import data.DataClass;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Taavi Metsvahi
 */

public class SmartSignature extends Application{
	//Create objects to work with
	DataClass connection = new DataClass();
	OutlookHandler outlook = new OutlookHandler();

	//Include images to use
    //Image logo = new Image("http://smartconnections.ee/resources/img/sig_title.png");
    //Image icon = new Image("http://smartconnections.ee/resources/img/sig_icon.png");
    Image logo = new Image(getClass().getResourceAsStream("Assets/sig_title.png"));
    Image icon = new Image(getClass().getResourceAsStream("Assets/sig_icon.png"));
    ImageView logoView = new ImageView(logo);


	//Create global variables
	private String guiTitle = "Smart Signature";
	private int guiWidth = 300;
	private int guiPadding = 10;
	private Stage window;
	private Scene loginScene;
	private Scene loggedScene;

	public static void main(String[] args) throws Exception {
    	launch(args);
	}

    /**
     * Starts application
     * @param Stage primaryStage
     */
	@Override
    public void start(Stage primaryStage) {
    	//Create window to work with
    	window = primaryStage; 	
    	//Set window title
    	window.setTitle(guiTitle);
    	//Set icon of the programm
    	window.getIcons().add(icon);
    	//Restrict user input
    	window.setResizable(false);
    	//Build and attach login screen
    	buildLogin();
    }

    /**
     * Builds login window
     */
    private void buildLogin() {   
    	//New grid
        GridPane grid = new GridPane();
        //Set padding for edges
        grid.setPadding(new Insets(guiPadding, guiPadding, guiPadding, guiPadding));
        //Set gui width
        grid.setPrefWidth(guiWidth);
        //Set vertical distance
        grid.setVgap(5); 
        //Attach stylesheet
        grid.getStylesheets().add("/smartSignature/Assets/signature.css");

    	//Insert logo to the top
        GridPane.setConstraints(logoView, 0, 0);
        
        //New name abel, add padding and set its place
        Label nameLabel = new Label("Kasutajanimi:");
        nameLabel.getStyleClass().add("top_padding_20");
        GridPane.setConstraints(nameLabel, 0, 2);

        //Name Input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 0, 3);

        //Password Label
        Label passLabel = new Label("Parool:");
        GridPane.setConstraints(passLabel, 0, 4);

        //Password Input
        PasswordField passInput = new PasswordField();
        GridPane.setConstraints(passInput, 0, 5);

        //Login
        Button loginButton = new Button("Logi sisse");
        loginButton.setPrefWidth(guiWidth);
        GridPane.setConstraints(loginButton, 0, 6);
		
        //Add everything to grid and apply
        grid.getChildren().addAll(logoView, nameLabel, nameInput, passLabel, passInput, loginButton);
        loginScene = new Scene(grid);

        //Set the scene to the window 
        window.setScene(loginScene);
        //Show the application
        window.show();        
        
        /*
         *  ACTIONS
         */
        
        //Login action
        loginButton.setOnAction(e -> {
            //If name input length is less than 3
        	if(nameInput.getText().length() < 3){
                //Give error by adding ErrorInput class to it
        		nameInput.getStyleClass().add("errorInput");
        		passInput.getStyleClass().remove("errorInput");
        	}
            //If pass input length is less than 3
        	else if(passInput.getText().length() < 3){
                //Give error by adding ErrorInput class to it
        		nameInput.getStyleClass().remove("errorInput");
        		passInput.getStyleClass().add("errorInput");
        	}
        	else{
                //If all correct, remove any error classes
        		nameInput.getStyleClass().remove("errorInput");
        		passInput.getStyleClass().remove("errorInput");

                //Set name into connection object
        		connection.setName(nameInput.getText());
                //Set password into connection object
				connection.setPassword(passInput.getText());
                //Clear the option value just in case
				connection.setOption("");

                //Connect with connection object to the web
				int status = connection.connect(0);

                //If the status is 0, it was a success
        		if(status == 0){
                    //Build a logged in veiw
        			BuildLoggedIn();
        		}else{
                    //If the status is not 0, it was a failure, display errors in inputs
            		System.out.println("Error: "+connection.getError());
            		nameInput.getStyleClass().add("errorInput");
            		passInput.getStyleClass().add("errorInput");
        		}
        	} 
        });
    }

    /**
     * Builds the logged in screen
     */
    private void BuildLoggedIn() {   
    	//New grid
        GridPane grid = new GridPane();
        //Set padding for edges
        grid.setPadding(new Insets(guiPadding, guiPadding, guiPadding, guiPadding));
        //Set gui width
        grid.setPrefWidth(guiWidth);
        //Set vertical distance
        grid.setVgap(5); 
        //Attach stylesheet
        grid.getStylesheets().add("/smartSignature/Assets/signature.css");


        //Log out button
        Button logoutButton = new Button("Logi välja");
        logoutButton.getStyleClass().add("logout_button");
        logoutButton.setPrefWidth(guiWidth);
        GridPane.setConstraints(logoutButton, 0, 0);
        
    	//Insert logo to the top
        logoView.getStyleClass().add("top_padding_20");
        GridPane.setConstraints(logoView, 0, 1);

        //Web signature label
        Label sigLabel = new Label("SC signatuur:");
        sigLabel.getStyleClass().add("top_padding_20");
        GridPane.setConstraints(sigLabel, 0, 3);

        //Web signature select box
        Button sigLink = new Button("Loo signatuure juurde");
        sigLink.setPrefWidth(guiWidth);
        GridPane.setConstraints(sigLink, 0, 4);

        //Process the options
        ObservableList<String> sigOptions = connection.processList(connection.getList());
        //Web signature select box
        ComboBox<String> sigBox = new ComboBox<String>(sigOptions);
        sigBox.setPrefWidth(guiWidth);
        GridPane.setConstraints(sigBox, 0, 4);
        
        //Outlook signature label
        Label systemLabel = new Label("Outlook signatuur:");
        GridPane.setConstraints(systemLabel, 0, 5);

        //Outlook signature 
        Button sysRefresh = new Button("Loo Outlooki signatuur mida asendada");
        sysRefresh.setPrefWidth(guiWidth);
        GridPane.setConstraints(sysRefresh, 0, 6);

        //Process the options
        ObservableList<String> sigOutOptions = outlook.fetchSignatures();
        //Web signature select box
        ComboBox<String> sigOutBox = new ComboBox<String>(sigOutOptions);
        sigOutBox.setPrefWidth(guiWidth);
        GridPane.setConstraints(sigOutBox, 0, 6);
        

        //Label just in case there should occur an error
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("padding_top_bottom_10");
        errorLabel.setPrefWidth(guiWidth);
        GridPane.setConstraints(errorLabel, 0, 7);


        //Apply web signature to outlook
        Button applySig = new Button("Asenda!");
        applySig.setPrefWidth(guiWidth);
        GridPane.setConstraints(applySig, 0, 8);

        //Refresh button
        Button refreshButton = new Button("Lae väljad uuesti");
        refreshButton.getStyleClass().add("green_button");
        refreshButton.setPrefWidth(guiWidth);
        GridPane.setConstraints(refreshButton, 0, 9);

        //Check which one to display
        if(sigOptions.isEmpty()){
            grid.getChildren().add(sigLink);
        }else{
            grid.getChildren().add(sigBox);
        }
        
        //Check which one to display
        if(sigOutOptions.isEmpty()){
            grid.getChildren().add(sysRefresh);
        }else{
            grid.getChildren().add(sigOutBox);
        }
        
        //Add everything to grid and apply
        grid.getChildren().addAll(logoView,logoutButton,refreshButton,sigLabel,systemLabel,applySig, errorLabel);
        loggedScene = new Scene(grid);

        //Set the scene to the window 
        window.setScene(loggedScene);
        //Show the application
        window.show();        

        //Logout action
        logoutButton.setOnAction(e -> {
            //Destroy old classes and create new
    		connection = new DataClass();
    		outlook = new OutlookHandler();
    		buildLogin();
        });
        
        //Refresh button action
        refreshButton.setOnAction(e -> {
            //Connect with connection object to the web to renew the list
			int status = connection.connect(0);
			if(status != 0) System.out.println("oh damn...");
			//Rebuild the scene
    		BuildLoggedIn();
        });
        
        //Link to web page action
        sigLink.setOnAction(e -> {
            //Open web browser on click
    		connection.openLinks(connection.getSigLink());
        });
        
        //Link to web page action
        sysRefresh.setOnAction(e -> {
            //Open web browser on click
    		connection.openLinks(outlook.getTutorialLink());
        });

        //Apply signature button action
        applySig.setOnAction(e -> {
            //If there isnt any SC signature options, display error
            if(sigOptions.isEmpty()){
            	errorLabel.setText("Lisa signatuure SC-sse ja uuenda välju");
                errorLabel.getStyleClass().add("finalErrorLabel");
            }
            //If there isnt any Outlook signature options, display error
            else if(sigOutOptions.isEmpty()){
            	errorLabel.setText("Lisa signatuure Outlooki ja uuenda välju");
                errorLabel.getStyleClass().add("finalErrorLabel");
        	}
            //If there isnt any SC signature options selected, display error
            else if(sigBox.getValue() == null){
            	errorLabel.setText("Selekteeri SC signatuur mida tahad kasutada");
                errorLabel.getStyleClass().add("finalErrorLabel");
            }
            //If there isnt any Outlook signature options selected, display error
            else if(sigOutBox.getValue() == null){
            	errorLabel.setText("Selekteeri Outlooki signatuur mida tahad kasutada");
                errorLabel.getStyleClass().add("finalErrorLabel");
            }
            //If there isnt any problems, try to replace
            else{
            	//Set the option
            	connection.setOption(sigBox.getValue().toString());
            	//Get the signature
    			int status = connection.connect(1);
    			//If error occured, show it
    			if(status != 0){
                	//sigOutBox.getValue().toString().isEmpty()
                	errorLabel.setText("Signatuuri ei leitud!");
                    errorLabel.getStyleClass().add("finalErrorLabel");
    			}
    			else{
        			//If not, try to replace the contents
    				int statusOut = outlook.writeSignature(sigOutBox.getValue().toString(), connection.getSignature());

        			//If there was an error, display it
        			if(statusOut != 0){
                    	errorLabel.setText("Signatuuri asendamisega tekkis probleem!");
                        errorLabel.getStyleClass().add("finalErrorLabel");
        			}
        			//if status = 0, it was an success
        			else{
	                	errorLabel.setText("Signatuur on asendatud!");
	                    errorLabel.getStyleClass().add("finalSuccessLabel");
        			}
    			}
            }
        });
        
    }

        
}
