package boards;

import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.Node;
import ships.*;

/**
 * Abstract class that represents a board in Battleship game.
 * Contains multiple data for GUI representation of the board, ships, auxiliary booleans for a game control.
 * Contains methods for board initialization, manipulation, and gameplay logic.
 * 
 * @author Team 8
 * @version 1.0
 *
 */
public abstract class Board{

	//Labels
 	private Label coordinatesMessage; //used for testing; tracks coordinates of cells
 	private Label statusMessage; //current game status, error messages
 	private Label roundMessage; //current turn
 	
 	private final int MAX_GRID_SIZE = 11; //size of the board
 	
 	//Data about the current ship (by default - Destroyer)
 	private int currentShipSize; 
 	private int roundCounter;
 	private String currentShipName;
 	
 	//Auxiliary booleans for ship placement and game states
 	private boolean horizontally; 
 	private boolean readyToPlay;
 	private boolean turnEnded;
 	private boolean gameEnd;
 	
 	//GridPane that represent game board
 	private GridPane gameBoard; 	
 	private HBox gameBoardGUI;
 	
 	//List of ships
 	private ArrayList<Ship> listOfShips = new ArrayList<Ship>(); 	
 	
	//Colors
 	private Color gridSrtokeColor = Color.DARKRED;
 	protected Color defaultCellColor = Color.rgb(240, 248, 255, 0.5); //alice blue transparent
 	private Color defaultHoverColor = Color.DARKGREY;
 	private Color inactiveCellColor = Color.rgb(0,0,0,0); //100% transparent
 	protected Color incorrectPlacementColor = Color.PALEVIOLETRED;
 	protected Color overlapColor = Color.RED; 	
 	protected Color missColor = Color.BLUE;	
	
 	//***************************************CONSTRUCTORS********************************************
 	
 	/**
 	 * Default Constructor.
 	 * Initializes a GUI board.
	 */
 	public Board()
 	{
 		//Initialize labels
 		coordinatesMessage = new Label("x:x");
    	statusMessage = new Label("status");
    	roundMessage = new Label("1");
      	
    	//Ships
     	AircraftCarrier ac = new AircraftCarrier();
     	Battleship btls = new Battleship();
     	Cruiser cr = new Cruiser();
     	Submarine sbm = new Submarine();
     	Destroyer ds = new Destroyer();
     	
    	//Add ships to the list of ships
    	listOfShips.add(ac);
    	listOfShips.add(btls);
    	listOfShips.add(cr);
    	listOfShips.add(sbm);
    	listOfShips.add(ds);
    	
    	//Default current ship data
    	currentShipSize = 2; 
     	roundCounter = 1;
     	currentShipName = "Destroyer";
     	
     	//Default values of auxiliary booleans
     	horizontally = true; 
     	readyToPlay = false;     	
     	gameEnd = false;
    	   	
    	//Main GridPane
    	gameBoard = new GridPane();
    	gameBoard.setMinSize(40, 40);
    	
    	//Build the game board out of grid cells
    	for(int i = 0; i < MAX_GRID_SIZE; i++)
    	{
    		for(int j = 0; j < MAX_GRID_SIZE; j++)
    		{
    			//new GridCell
    			GridCell cell = new GridCell(i, j);	            
	            cell.setWidth(40);
	            cell.setHeight(40);
	            
	            //out of game area
    			if(i == 0 || j == 0)
	            {    				
	            	cell.setFill(inactiveCellColor); //set to inactive color
	            	
	            	if(i == 0 && j == 0) //upper-left corner grid cell
	            	{
	            		cell.setStroke(inactiveCellColor);
	            		gameBoard.add(cell, i, j);
	            	}
	            	else //other inactive cells
	            	{
	            		cell.setStroke(gridSrtokeColor);
	            		
	            		if(i==0) //horizontal inactive grid cells
	            		{
	            			StackPane number = new StackPane();	//stack pane to hold a grid cell and a text     
	            			
	            			//Text and its formating
	            			Text numberText = new Text(Character.toString((char)('A' + j - 1)));	            			
	            			numberText.setFont(Font.font(null, FontWeight.BOLD, 15));
	            			numberText.setFill(Color.WHITE);
	            			
	            			//Combine grid cell and text to a stack pane and add to game board
	            			number.getChildren().addAll(cell, numberText);
	            			gameBoard.add(number, i, j);
	            		}
	            		else if(j == 0) //vertical inactive grid cells
	            		{
	            			StackPane character = new StackPane(); //stack pane to hold a grid cell and a text
	            			
	            			//Text and its formating
	            			Text charaterText;
	            			if(i != 10) //if not 10 use ASCII to update the char (1 to 9)
	            			{
	            				charaterText = new Text(Character.toString((char)('1' + i - 1)));
	            			}
	            			else //if 10, pass String 10
	            			{
	            				charaterText = new Text("10");
	            			}		            			
	            			charaterText.setFont(Font.font(null, FontWeight.BOLD, 15));
	            			charaterText.setFill(Color.WHITE);
	            			
	            			//Combine grid cell and text to a stack pane and add to game board
	            			character.getChildren().addAll(cell, charaterText);
	            			gameBoard.add(character, i, j);
	            		}	            		
	            	}
	            	
	            }
	            else //game area
	            {       
	            	//set event handlers
	            	onHoverChangeColor(cell, defaultCellColor, defaultHoverColor, coordinatesMessage, gameBoard);
	            	onClickChooseGrid(cell, statusMessage, gameBoard);
	            	
	            	//set color and add cell to the game board
	            	cell.setFill(defaultCellColor);
	    	        cell.setStroke(gridSrtokeColor);
	    	        gameBoard.add(cell, i, j);
	            }   	            
    	               			
    		}
    		
    	}
    	
    	//add board to board Hbox
    	gameBoardGUI = new HBox(20, gameBoard);
 	}
    
    
 	//****************************************************ACCESSOR METHODS*****************************************************
 	
 	/**
 	 * Get a node of the grid cell in grid pane by index
 	 * 
 	 * @param gridPane : GridPane that represents the gameboard
 	 * @param col : int that represents a column in the gameboard
 	 * @param row : int that represents a row in the gameboard
 	 * @return node : Node of the GridCell OR returns NULL
 	 */
    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        //iterate through all the position in the gridpane
    	for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) //grid found, return the Node of the grid
            {
                return node;
            }
        }
        return null; //grid wasn't found
    }
    
    /**
     * Check the current ship placement orientation
     * 
     * @return horizontally : boolean that represents the current ship placement orientation
     */
    public boolean isHorizontally()
    {
    	return horizontally;
    }
    
    
    /**
     * Get the size of the current ship
     * 
     * @return currentShipSize : String that represents the size of the current ship
     */
    public int getCurrentShipSize()
    {
    	return currentShipSize;
    }
    
    /**
     * Get the name of the current ship
     * 
     * @return currentShipName : String that represents the name of the current ship
     */
    public String getCurrentShipName()
    {
    	return currentShipName;
    }
    
    /**
     * Checks if the current ship was placed
     * 
     * @return : boolean that represents if the ship was placed
     */
    public boolean currentShipIsPlaced()
    {
    	for(Ship ship : listOfShips) //iterate through the list of ships
    	{
    		if(currentShipName == ship.getName()) //check if this ship is the current ship
    		{
    			return ship.isPlaced(); //current ship was placed
    		}
    	}
    	
    	return false; //current ship wasn't placed
    }
    
    
    /**
     * Checks if all ships were placed
     * 
     * @return : boolean that represents if all ships were placed
     */
    public boolean allShipsArePlaced()
    {
    	int placedShipsCounter = 0;
    	
    	for(Ship ship : listOfShips) //iterate through the list of ships
    	{
    		if(ship.isPlaced()) //if ship was placed - update counter
    		{
    			placedShipsCounter++;
    		}
    	}
    	
    	if(placedShipsCounter == listOfShips.size()) //all ships were placed
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}    	
    }    
    
    
    /**
     * Checks if all ships sunk
     * 
     * @return : boolean that represents if all ships sunk
     */
    public boolean allShipSunk()
    {
    	int sunkenShips = 0;
    	
    	for(Ship ship : listOfShips) //iterate through the list of ships to check the status of each ship
    	{
    		if(ship.isSunk()) //if this ship sunk - update the counter
    		{
    			sunkenShips++;
    		}	
    	}
    	
    	if(sunkenShips == listOfShips.size()) //all ship sunk
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    	
    }
    
    
    /**
     * Get a GUI representation of the game board
     * 
     * @return gameBoardGUI : HBox that represents the game board
     */
    public HBox getBoardGUI()
    {
    	return gameBoardGUI;
    }
    
    
    /**
     * Check the status of the turn (true = ended, false = non ended)
     * 
     * @return turnEnded : boolean that represents the status of the turn
     */
    public boolean getTurn()
    {
    	return turnEnded;
    }
    
    
    /**
     * Check if the board is ready for the game
     * 
     * @return readyToPlay : boolean that represents the state of the board if it is ready for the game
     */
    public boolean isReadyToPlay()
    {
    	return readyToPlay;
    }
    
    
    /**
     * Check if the game is ended
     * 
     * @return gameEnd : boolean that represents a state of the game (ended or not)
     */
    public boolean isGameEnded()
    {
    	return gameEnd;
    }
    
    
    /**
     * Get a label with status message
     * 
     * @return statusMessage : Label that represents a status message GUI
     */
    public Label getTextLabel()
    {
    	return statusMessage;
    }   
    
    
    /**
     * Get a game board
     * 
     * @return gameBoard : GridPane that represents a game board
     */
    public GridPane getGridBoard()
    {
    	return gameBoard;
    }
    
    
    /**
     * Get a size of the grid board
     * 
     * @return MAX_GRID_SIZE : int that represents a size of the grid board
     */
    public int getGridSize()
    {
    	return MAX_GRID_SIZE;
    }
    
    
    /**
     * Get a GUI label for coordinates
     * 
     * @return coordinatesMessage : Label that represents coordinated of chosen grid cell
     */
    public Label getCoordinadesLabel()
    {
    	return coordinatesMessage;
    }
    
    
    /**
     * Get a GUI label that represents the current round number
     * 
     * @return roundMessage : Label that represents the current round number
     */
    public Label getRoundLabel()
    {
    	return roundMessage;
    }
    
    
    /**
     * Get the list of ships 
     * 
     * @return listOfShips : ArrayList<Ship> that represents a list of ships in the board
     */
    public ArrayList<Ship> getListOfShips()
    {
    	return listOfShips;
    }
    
    
    //***************************************************MUTATOR METHODS*******************************************************
    
    /**
     * Set gameEnd to true
     */
    public void endGame()
    {
    	gameEnd = true;
    }
    
    
    /**
     * Reset the turn
     */
    public void resetTurn()
    {
    	turnEnded = false;
    }
    
    
    /**
     * End game turn. Check if any ship sunk. If all ship sunk - end the game.
     */
    public void endTurn()
    {    	
    	for(Ship ship : listOfShips) //iterate through the list of ships to check the status of each ship
    	{
    		ship.isSunk();	
    	}    	
    	
    	if(allShipSunk()) //if all ships sunk - end the game
    	{
    		gameEnd = true;   		
    		statusMessage.setText("GAME IS FINISHED!");
    	}
    	
    	//end the turn and update round counter
    	turnEnded = true;
    	roundCounter++;
		roundMessage.setText(String.valueOf(roundCounter));    	
    }
    
    
    /**
     * Receive a hit (or miss) in the grid cell by given coordinates
     * 
     * @param x : int that represents X coordinate
     * @param y : int that represents Y coordinate
     * @return : boolean that represents the success of the hit
     */
    public boolean getHit(int x, int y)
    {
    	if(x < 1 || x > 10 || y < 1 || y > 10) //check if out of bounds
    	{
    		return false;
    	}
    	else //in game area
    	{
    		//get chosen grid cell
    		GridCell currentGC = (GridCell)getNodeFromGridPane(gameBoard, x, y); 
    		
    		if(currentGC.isHit() || currentGC.isMiss()) //check if already was hit (or miss)
    		{
    			return false;
    		}
    		else //if not - hit it (or miss)
    		{
    			if(currentGC.isOccupied()) //has ship - hit
    			{
    				currentGC.hitCell();
    			}
    			else //no ship - miss
    			{
    				currentGC.missCell();
    			}
    		}
    	}
    	return true; //success
    }    
    
    
    /**
     * Set the orientation for the ship placement to its opposite
     */
    public void flipOrientation()
    {
    	if(horizontally)
    	{
    		horizontally = false;
    	}
    	else
    	{
    		horizontally = true;
    	}  
    }
    
    
    /**
     * Set ship placement orientation to horizontal placement
     */
    public void horizontalPlacement()
    {
    	horizontally = true;
    }
    
    
    /**
     * Set ship placement orientation to vertical placement
     */
    public void verticalPlacement()
    {
    	horizontally = false;
    }
    
    
    /**
     * Set the status of the game to ready to play
     */
    public void readyToPlay()
    {
    	readyToPlay = true;
    }
    
    
    /**
     * Set the current ship name
     *    
     * @param n : String that represents the new current ship name
     */
    public void setCurrentShipName(String n)
    {
    	currentShipName = n;		
    }
    
    
    /**
     * Set the current ship size
     * 
     * @param s : String that represents the new current ship size
     */
    public void setCurrentShipSize(int s)
    {
    	currentShipSize = s;
    }  
    
    
    
    //********************************************ABSTRACT METHODS*********************************************************   
    
    /**
     * Abstract EventHandler method that controls a change of color on mouse hover 
     * 
     * @param gc : GridCell that represents the chosen grid cell
     * @param defaultColor : Color that represents a default color of a grid cell
     * @param hoverColor : Color that represents a color of a grid cell when the mouse is hovering above it
     * @param textLabel : Label that represents a status message GUI
     * @param gp : GridPane that represents the game board
     */
    public abstract void onHoverChangeColor(GridCell gc, Color defaultColor, Color hoverColor, 
    		Label textLabel, GridPane gp);    
        
    /**
     * Abstract EventHandler method the controls actions on mouse click on chosen grid cell
     * 
     * @param gc : GridCell that represents the chosen grid cell
     * @param textLabel : Label that represents a status message GUI
     * @param gp : GridPane that represents the game board
     */
    public abstract void onClickChooseGrid(GridCell gc, 
    		Label textLabel, GridPane gp);
    
    /**
     * Abstract method that controls the passage of the list of grid cells to a current ship (ship placement)
     * 
     * @param shipCells : ArrayList<GridCell> that represents a list of grid cells that belongs to the current ship
     */
    public abstract void placeCurrentShip(ArrayList<GridCell> shipCells);
    
    /**
     * Abstract method that checks if this board belong to a player or computer
     * 
     * @return : boolean that represents if this board belongs to a player or computer
     */
    public abstract boolean isPlayer();
    
    
    
}