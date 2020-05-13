import players.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import boards.Board;
import boards.ComputerBoard;
import boards.GridCell;
import boards.PlayerBoard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Implements more or less all of the User Interface functionality. Includes the saving/loading implementation
 * as well.
 * 
 * A veritable smorgasboard of javaFX utilities/so forth. 
 * @author Group 8
 *
 */
public class GameLevelGUI {
	/**
	 * Basic UI Foundations.
	 */
	private BorderPane window;
	private AnchorPane mainMenu = new AnchorPane();
	boolean mode = false; 
	BackgroundImage backImage = new BackgroundImage(new Image("file:///Users/13476/eclipse-workspace"
			+ "/BattleshipGame/src/assets/Background/stars_texture.png"),  BackgroundRepeat.REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	          BackgroundSize.DEFAULT);
	Stage DISPLAY; 
	Scene scene1, scene1Sub, scene1SubSub, sceneIDK, scene2, sceneName, scene3;

	/**
	 * Necessary values and instantiations for the Player/Enemy AI Boards. 
	 */
	public String PlayerName; 
	HumanPlayer player = new HumanPlayer();
	PlayerBoard playerBoard = player.getPlayerBoard();
	ComputerPlayer computerAI = new ComputerPlayer(player);
	ComputerBoard aiBoard = computerAI.getComputerBoard();
	HBox aiHbox = aiBoard.getBoardGUI();    	
	HBox playerHbox2 = playerBoard.getBoardGUI();	
	
	/**
	 * Initializes the GUI.
	 */
	public GameLevelGUI() {
		window = new BorderPane();
		window.setPrefSize(1200, 1000);
		initMainMenu();
		initGrid();
	}
	
	/**
	 * Initializes the Main Menu
	 */
	public void initMainMenu() {
		//Initial Menu
		Label label1 = new Label("BATTLESHIP");
    	Button button1 = new Button("New Game");
    	Button button2 = new Button("Options");
    	Button button3 = new Button("Tutorial");
    	VBox menuLayout = new VBox(20, label1, button1, button2, button3);
		mainMenu.getChildren().add(menuLayout);
		
    	scene1 = new Scene(mainMenu, 600, 400);
 
    	//Options Menu
    	Label label2 = new Label("Options");
    	Button button1Sub = new Button("Difficulty Options");
    	Button button2Sub = new Button("Color Options");
    	Button button3Sub = new Button("Back");
    	VBox optionsLayout = new VBox(20, label2, button1Sub, button2Sub, button3Sub);
		AnchorPane optionsMenu = new AnchorPane(optionsLayout);
		
    	scene1Sub = new Scene(optionsMenu, 600, 400);
    	
    	//Difficulty Menu
    	final Label label3 = new Label();
    	label3.setText("Please Select One of The Following Difficulty Options (They're Self Explanatory):");
    	Button button1SubSub = new Button("Brain Dead Opponent");
    	Button button2SubSub= new Button("Actually Functional AI");
    	Button button3SubSub = new Button("Back");
    	VBox difficultyLayout = new VBox(20, label3, button1SubSub, button2SubSub, button3SubSub);
		AnchorPane difficultyMenu = new AnchorPane(difficultyLayout);
		
    	scene1SubSub = new Scene(difficultyMenu, 600, 400);
    	
    	//Mode Selection Menu
    	Label labelIDK = new Label("Select a Color Theme:");
    	Button button1IDK = new Button("Deep Space");
    	Button button2IDK= new Button("Just White");
    	Button button3IDK = new Button("Back");
    	VBox modeLayout = new VBox(20, labelIDK, button1IDK, button2IDK, button3IDK);
		AnchorPane modeMenu = new AnchorPane(modeLayout);
		
    	sceneIDK = new Scene(modeMenu, 600, 400);
    	
    	
    	//Tutorial Menu
    	String TutorialMessage = "Battleship is a strategy (pseudo-guessing) game played "
				+ "between two players (in our case, between a player and an AI opponent). "
				+ "It’s played on two separate grids, one for each player, \nwith each"
				+ " grid-component having its own explicable coordinate. Each player "
				+ "initiates the game by placing various ships of various sizes onto "
				+ "their respective grids (at valid coordinates);\n the aim of the game "
				+ "is to, in turns, correctly guess which coordinates contain a part of "
				+ "one of the opposing side’s ships, with the ultimate goal of wiping "
				+ "out all of the opposing-sides ships.\nNaturally, the winner of the game "
				+ "is determined by who wipes out all of whose ships first.";
		
		Label label4 = new Label(TutorialMessage);
		label4.setWrapText(true);
    	Button button4 = new Button("Alright I get the idea. Take me Back.");
		VBox tutorialLayout = new VBox(20, label4, button4);
		AnchorPane tutorialMenu = new AnchorPane(tutorialLayout);
		
		scene2 = new Scene(tutorialMenu, 1100, 400);
		
		
		//Name Submission Menu
		GridPane nameSpace = new GridPane();
		final TextField name = new TextField();
		name.setPromptText("Enter your first name.");
		name.setPrefColumnCount(10);
		GridPane.setConstraints(name, 0, 0);
		nameSpace.getChildren().add(name);

		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 1, 0);
		nameSpace.getChildren().add(submit);

		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 1, 1);
		nameSpace.getChildren().add(clear);
		
		final Label label = new Label("Enter in Your Name");
		GridPane.setConstraints(label, 0, 3);
		GridPane.setColumnSpan(label, 2);
		nameSpace.getChildren().add(label);
		
		sceneName = new Scene(nameSpace);
    	 
        scene3 = new Scene(window);
        
        //Misc Button Functionality
    	button1.setOnAction(e -> DISPLAY.setScene(sceneName));
    	button2.setOnAction(e -> DISPLAY.setScene(scene1Sub));
    	button1Sub.setOnAction(e -> DISPLAY.setScene(scene1SubSub));
      	button3.setOnAction(e -> DISPLAY.setScene(scene2));
      	button3Sub.setOnAction(e -> DISPLAY.setScene(scene1));
      	button3SubSub.setOnAction(e -> DISPLAY.setScene(scene1Sub));
    	button2Sub.setOnAction(e -> DISPLAY.setScene(sceneIDK));
      	button3IDK.setOnAction(e -> DISPLAY.setScene(scene1Sub));
      	button4.setOnAction(e -> DISPLAY.setScene(scene1));
      	
      	
      	/**
      	 * Simple switches for the AI's difficulty. 
      	 */
      	button1SubSub.setOnAction(e -> {
			this.aiBoard.setHardLevel(false);
			label3.setText("AI set to: Easy");
		}); 
      	
      	button2SubSub.setOnAction(e -> {
			this.aiBoard.setHardLevel(true); 
			label3.setText("AI set to: Hard");
		}); 
      	
      	/**
      	 * Sets the color mode for the game board. 
      	 */
      	button1IDK.setOnAction(e -> {
			mode = true;
			labelIDK.setText("Mode set to: Deep Space");
		}); 
      	
      	button2IDK.setOnAction(e -> {
			mode = false; 
			labelIDK.setText("Mode set to: Just White");
		}); 
      	
      	submit.setOnAction(e -> {
      		PlayerName = name.getText();
      		player.setName(PlayerName);
      		DISPLAY.setScene(scene3);
      	});
      	
      	clear.setOnAction(e -> {
      		name.clear();
      	});
	}

	/**
	 * Initializes the game board. 
	 */
	private void initGrid() {
		
		if(mode) {
			window.setStyle("-fx-background-Color: WHITE");
		} else {
			window.setBackground(new Background(backImage));;
		}
        Menu menu1 = new Menu("Save/Load");
        
        MenuItem menuItem1 = new MenuItem("Save State");
        MenuItem menuItem2 = new MenuItem("Load State");
        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu1);

		VBox finalBox = new VBox(20, aiHbox, playerHbox2);
		AnchorPane GUISetup = new AnchorPane(menuBar, finalBox);
		
		finalBox.setLayoutX(200);
		finalBox.setLayoutY(45);	
		
		menuItem1.setOnAction(e -> {
			try {
				saveGame();
				System.out.println("Saved Successfully!");
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		});
		
		menuItem2.setOnAction(e -> {
			try {
				loadGame();
				System.out.println("Saved Successfully!");
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		});    
		
		window.getChildren().add(GUISetup);
	}
	
	/**
	 * The Save Functionality. Creates three different files all in all.
	 * @throws IOException
	 */
	public void saveGame() throws IOException {
		FileOutputStream playerOutFile = new FileOutputStream(new File("playerState.data"));
	    ObjectOutputStream playerOut = new ObjectOutputStream(playerOutFile);
	    playerOut.writeObject(playerBoard.listOfCells);
	    playerOut.close();
	    
		FileOutputStream AIOutFile = new FileOutputStream(new File("AIState.data"));
	    ObjectOutputStream AIOut = new ObjectOutputStream(AIOutFile);
	    AIOut.writeObject(aiBoard.listOfCells);
	    AIOut.close();
	    
		FileOutputStream miscOutFile = new FileOutputStream(new File("MiscState.data"));
	    ObjectOutputStream miscOut = new ObjectOutputStream(miscOutFile);
	    miscOut.writeObject(((Board) playerBoard).getRoundCounter());
	    miscOut.close();
	}
	
	/**
	 * The Load functionality. 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void loadGame() throws IOException, ClassNotFoundException {
		FileInputStream playerInFile = new FileInputStream("playerState.data");
		ObjectInputStream playerIn = new ObjectInputStream(playerInFile);
		Object playerReadIn = playerIn.readObject();
		@SuppressWarnings("unchecked")
		ArrayList<GridCell> man = (ArrayList<GridCell>) playerReadIn;
		
		for(GridCell s: man) {
			((GridCell)playerBoard.getNodeFromGridPane(playerBoard.getGridBoard(), s.getXCoordinate(), s.getYCoordinate())).reset(s);
		}
		playerIn.close();
		
		FileInputStream AIInFile = new FileInputStream("AIState.data");
		ObjectInputStream AIIn = new ObjectInputStream(AIInFile);
		Object AIReadIn = AIIn.readObject();
		@SuppressWarnings("unchecked")
		ArrayList<GridCell> woman = (ArrayList<GridCell>) AIReadIn;
		
		for(GridCell s: woman) {
				((GridCell)aiBoard.getNodeFromGridPane(aiBoard.getGridBoard(), s.getXCoordinate(), s.getYCoordinate())).reset(s);
			}
		AIIn.close();
		
		FileInputStream miscInFile = new FileInputStream("MiscState.data");
		ObjectInputStream miscIn = new ObjectInputStream(miscInFile);
		Object miscReadIn = miscIn.readObject();
		((Board) playerBoard).resetRoundCounter((int)miscReadIn);
		miscIn.close();
	}
	
	/**
	 * Enters into the initial game state starting with the menu, in essence. 
	 * @return
	 */
	
	public Scene getRoot() {
		return scene1; 
	} 
}