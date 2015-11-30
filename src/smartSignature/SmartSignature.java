package smartSignature;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import smartSignature.DataClass;
import smartSignature.OutlookHandler;

/**
 * Created by Taavi Metsvahi
 */

public class SmartSignature extends Application{
	//Create objects to work with
	DataClass connection = new DataClass();
	OutlookHandler outlook = new OutlookHandler();

	//Create global variables
	private String guiTitle = "Smart Signature";
	private int guiWidth = 300;
	private int guiPadding = 10;
	private Stage window;
	private Scene loginScene;
	private Scene loggedScene;

	//Include images to use
    Image logo = new Image("http://smartconnections.ee/resources/img/sig_title.png");
    Image icon = new Image("http://smartconnections.ee/resources/img/sig_iconpng");
    ImageView logoView = new ImageView(logo);
    

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
        grid.getStylesheets().add("/smartSignature/signature.css");

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
				int status = connection.connect();

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
        grid.getStylesheets().add("/smartSignature/signature.css");

    	//Insert logo to the top
        GridPane.setConstraints(logoView, 0, 0);
    	

        //Add everything to grid and apply
        grid.getChildren().addAll(logoView);
        loggedScene = new Scene(grid);

        //Set the scene to the window 
        window.setScene(loggedScene);
        //Show the application
        window.show();        
    }

        
}
