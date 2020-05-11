import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testRunner extends Application {

 		
	public static void main(String[] args) {
        launch(args);
    }

	
	
    @Override
    public void start(Stage primaryStage) {
    	
    	GameLevelGUI glGUI = new GameLevelGUI();
    	
    	
    	
    	
    	Scene scene = new Scene(glGUI.getRoot());
        
 	   //Add the Scene to the Stage.
 	   primaryStage.setScene(scene);
       
 	   //Set the stage title.
 	   primaryStage.setTitle("TEST SCENE");
       primaryStage.setResizable(false);
 	   // Show the window.
 	   primaryStage.show();
    	
    }
    
}  
    