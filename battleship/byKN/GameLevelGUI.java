import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import boards.*;


public class GameLevelGUI {
	private BorderPane window;	
	
	
	PlayerBoard playerBoard = new PlayerBoard();
	ComputerBoard aiBoard = new ComputerBoard(playerBoard);
	HBox aiHbox = aiBoard.getBoardGUI();    	
	HBox playerHbox2 = playerBoard.getBoardGUI();	
	
	
	double offsetX, offsetY;	 
	
	ImageView base = new ImageView(new Image("file:src/assets/station.png"));
	
	public GameLevelGUI() {
		window = new BorderPane();
		BackgroundImage backImage = new BackgroundImage(new Image("file:src/assets/Background/stars_texture.png"),  BackgroundRepeat.REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		window.setBackground(new Background(backImage));
		window.setPrefSize(1200, 1000);
		initGrid();
	}
	
	private void initGrid() {
		// TODO Auto-generated method stub
		VBox finalBox = new VBox(20, aiHbox, playerHbox2);
		AnchorPane GUISetup = new AnchorPane(finalBox);
		
		finalBox.setLayoutX(200);
		finalBox.setLayoutY(20);	
		
		
		
        base.setLayoutX(30);
        base.setLayoutY(630);
        base.setScaleX(1.25);
        base.setScaleY(1.25);        
        
        
		window.getChildren().add(GUISetup);
	}
	
	public Pane getRoot() {
		return window; 
	} 
	
	

}