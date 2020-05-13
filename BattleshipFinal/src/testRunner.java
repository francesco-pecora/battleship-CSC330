import javafx.application.Application;
import javafx.stage.Stage;

public class testRunner extends Application {
		GameLevelGUI glGUI = new GameLevelGUI();    
 		
	public static void main(String[] args) {
        launch(args);
    }
	
    @Override
    public void start(Stage primaryStage) {
    	glGUI.DISPLAY = primaryStage;
        
 	    //Add the Scene to the Stage.
 	    primaryStage.setScene(glGUI.getRoot());
 	    
 	    //Set the stage title.
 	    primaryStage.setTitle("BattleShip - CSC330");
        primaryStage.setResizable(false);
        
 	    // Show the window.
 	    primaryStage.show();
    	
    }
    
}  
    