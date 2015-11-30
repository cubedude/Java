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

    Image logo = new Image("http://smartconnections.ee/resources/img/sig_title.png");
    Image icon = new Image("http://smartconnections.ee/resources/img/sig_iconpng");
    ImageView logoView = new ImageView(logo);
    
	
	public static void main(String[] args) throws Exception {
    	launch(args);
	}

	@Override
    public void start(Stage primaryStage) {
    	//Create window to work with
    	window = primaryStage; 	
    	window.setTitle(guiTitle);
    	window.getIcons().add(icon);
    	window.setResizable(false);
    	//Build and attach login screen
    	buildLogin();
    }

    private void buildLogin() {   
    	//New grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(guiPadding, guiPadding, guiPadding, guiPadding));
        grid.setPrefWidth(guiWidth);
        grid.setVgap(5); 
        grid.getStylesheets().add("/smartSignature/signature.css");

    	//Logo 
        GridPane.setConstraints(logoView, 0, 0);
        
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
		
        //Login action
        loginButton.setOnAction(e -> {
        	if(nameInput.getText().length() < 3){
        		System.out.println("name input error");
        		nameInput.getStyleClass().add("errorInput");
        		passInput.getStyleClass().remove("errorInput");
        	}
        	else if(passInput.getText().length() < 3){
        		System.out.println("password input error");
        		nameInput.getStyleClass().remove("errorInput");
        		passInput.getStyleClass().add("errorInput");
        	}
        	else{
        		nameInput.getStyleClass().remove("errorInput");
        		passInput.getStyleClass().remove("errorInput");
        		
        		connection.setName(nameInput.getText());
				connection.setPassword(passInput.getText());
				int status = connection.connect();
				
        		if(status == 0){
        			if(false) BuildLoggedIn();
            		System.out.println("success");
        		}else{
            		System.out.println("connection error");
            		nameInput.getStyleClass().add("errorInput");
            		passInput.getStyleClass().add("errorInput");
        		}
        	} 
        });
        
        //Add everything to grid and apply
        grid.getChildren().addAll(logoView, nameLabel, nameInput, passLabel, passInput, loginButton);
        loginScene = new Scene(grid);
        
        window.setScene(loginScene);
        window.show();        
    }

    private void BuildLoggedIn() {   
    	//New grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(guiPadding, guiPadding, guiPadding, guiPadding));
        grid.setPrefWidth(guiWidth);
        grid.setVgap(5); 
        
    	//Logo ofc
        GridPane.setConstraints(logoView, 0, 0);
    	

        //Add everything to grid and apply
        grid.getChildren().addAll(logoView);
        grid.getStylesheets().add("smartSignature/signature.css");
        loggedScene = new Scene(grid);
        
        window.setScene(loggedScene);
        window.show();
    }

        
}
