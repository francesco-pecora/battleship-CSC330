package boards;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ships.Ship;

/**
 * Class that extends an abstract class Board and represent the specific type of the board - Player Board.
 * Contains multiple data for GUI representation of the player board and ship placement GUI.
 * Contains methods for board initialization, manipulation, and gameplay logic.
 * 
 * @author Team 8
 * @version 1.0
 *
 */
public class PlayerBoard extends Board{

	//Colors
	private Color placedShipColor = Color.LIGHTGREEN;
	
	//Ship placement GUI 
	private Button placeAircraftCarrier;
 	private Button placeBattleship;
 	private Button placeCruiser;
 	private Button placeSubmarine;
 	private Button placeDestroyer;
 	private Button playGame;
 	private VBox placeShipsMenu;
 	
 	//Final message GUI
 	private StackPane finalMessage;
 	
 	//***************************************CONSTRUCTORS********************************************
 	
 	/**
	 * Default Constructor.
	 * Initializes a board GUI and a ship placement GUI 		 
	 */
	public PlayerBoard()
	{
		super(); //call the constructor of superclass
		
		//Ship placement GUI buttons
		placeAircraftCarrier = new Button("AircraftCarrier");
	 	placeBattleship = new Button("Battleship");
	 	placeCruiser = new Button("Cruiser");
	 	placeSubmarine = new Button("Submarine");
	 	placeDestroyer = new Button("Destroyer");
	 	playGame = new Button("PLAY!");	 	
		Button horizontalPlacementButton = new Button("Place Ship Horizontally");
    	Button verticalPlacementButton = new Button("Place Ship Vertically");    
    	
    	//Iterate through the list of ships and set the buttons
    	for(Ship ship : getListOfShips())
    	{
    		if(ship.getName() == "Aircraft Carrier")
    		{
    			placeAircraftCarrier.setOnAction(new ChoosingShipHandler(ship.getSize(), ship.getName()));
    		}
    		else if(ship.getName() == "Battleship")
    		{
    			placeBattleship.setOnAction(new ChoosingShipHandler(ship.getSize(), ship.getName()));
    		}
    		else if(ship.getName() == "Cruiser")
    		{
    			placeCruiser.setOnAction(new ChoosingShipHandler(ship.getSize(), ship.getName()));
    		}
    		else if(ship.getName() == "Submarine")
    		{
    			placeSubmarine.setOnAction(new ChoosingShipHandler(ship.getSize(), ship.getName()));
    		}
    		else if(ship.getName() == "Destroyer")
    		{
    			placeDestroyer.setOnAction(new ChoosingShipHandler(ship.getSize(), ship.getName()));
    		}
    	}      	
    	
    	//Set play game button
    	playGame.setOnAction(new playGameButtonHandler(getTextLabel()));
    	
    	//Set orientation buttons
    	horizontalPlacementButton.setOnAction(new HorizontalPlacementHandler());
    	verticalPlacementButton.setOnAction(new VerticalPlacementHandler()); 
    	
    	//Final message stack pane (end game status - win or loose)
    	finalMessage = new StackPane();
    	
    	//VBox that contains all ship placement GUI
    	placeShipsMenu = new VBox(20,horizontalPlacementButton, verticalPlacementButton,
    			placeAircraftCarrier, placeBattleship, placeCruiser, placeSubmarine, placeDestroyer, 
    			playGame);
    	
    	//Final player board GUI
		VBox vbox1 = new VBox(20, finalMessage, getCoordinadesLabel(), placeShipsMenu, getTextLabel(), getRoundLabel());
		getBoardGUI().getChildren().add(vbox1);
		
	}	
	
	//****************************************MUTATOR METHODS**********************************************
	
	/**
	 * Display the end game message based on the test parameter
	 * @param text
	 */
	public void displayFinalMessage(Text text)
    {
		//Create background rectangle
    	Rectangle rec = new Rectangle();
		rec.setWidth(300);
		rec.setHeight(100);
		rec.setFill(defaultCellColor);
		
		//add background and text to a final message stack pane
		finalMessage.getChildren().addAll(rec, text);				
    }
	
	
	//****************************************EVENT HANDLERS**********************************************
	
	/**
	 * Horizontal placement button event handler inner class
	 * 
	 * @author Team 8
	 *
	 */
    class HorizontalPlacementHandler implements EventHandler<ActionEvent>
    {
 	   public void handle(ActionEvent event)
 	   {
 		  horizontalPlacement(); //change the current placement orientation to horizontal
 	   }
    }
    
    
    /**
	 * Vertical placement button event handler inner class
	 * 
	 * @author Team 8
	 *
	 */
    class VerticalPlacementHandler implements EventHandler<ActionEvent>
    {
 	   public void handle(ActionEvent event)
 	   {
 		  verticalPlacement(); //change the current placement orientation to vertical
 	   }
    }
    
    
    /**
	 * Play game button event handler inner class
	 * 
	 * @author Team 8
	 *
	 */
    class playGameButtonHandler implements EventHandler<ActionEvent>
    {
    	Label playMessage; //message GUI label
    	
    	/**
    	 * Default constructor that takes a label as parameter
    	 * 
    	 * @param textLabel : Label that represents a message label
    	 */
    	public playGameButtonHandler(Label textLabel)
    	{
    		playMessage = textLabel;
    	}
    	
 	   public void handle(ActionEvent event)
 	   {
 		  if(allShipsArePlaced()) //all ships are placed, ready to play
 		  {
 			 readyToPlay();
 			 placeShipsMenu.getChildren().clear();
 			 playMessage.setText("Ready to Play!");
 		  }
 		  else //some ships weren't placed
 		  {
 			 playMessage.setText("Can't start the game! Some ships weren't placed!");
 		  }
 	   }
    }
        
    
    /**
	 * Choosing Ship game button event handler inner class
	 * 
	 * @author Team 8
	 *
	 */
    class ChoosingShipHandler implements EventHandler<ActionEvent>
    {
    	//Data about the ship
    	int sizeOfShip;
    	String typeOfShip;
    	
    	/**
    	 * Default constructor that takes the name and size of ship as parameters
    	 * 
    	 * @param size : int that represents the size of the ship
    	 * @param name : String that represents the name of the ship
    	 */
    	public ChoosingShipHandler(int size, String name)
    	{
    		sizeOfShip = size;
    		typeOfShip = name;    		
    	}
    	
 	   	public void handle(ActionEvent event)
 	   	{
 	   		//Set the size and the name of the current ship
 	   		setCurrentShipSize(sizeOfShip);
 	   		setCurrentShipName(typeOfShip);
 	   	}
    }
    
    
    
	//******************************IMPLEMENTATION OF ABSTRACT METHODS************************************
	/**
	 * Implementation of an abstract method from Board class.
	 * EventHandler method that controls a change of color on mouse hover.
	 * Different stated for the game stage and ship placement stage. 
     * 
     * @param gc : GridCell that represents the chosen grid cell
     * @param defaultColor : Color that represents a default color of a grid cell
     * @param hoverColor : Color that represents a color of a grid cell when the mouse is hovering above it
     * @param textLabel : Label that represents a status message GUI
     * @param gp : GridPane that represents the game board
	 */
    public void onHoverChangeColor(GridCell gc, Color defaultColor, Color hoverColor, 
    		Label textLabel, GridPane gp) 
    {    	
    	//mouse pointer on the grid cell
    	gc.setOnMouseEntered( e -> 
    	{    		
    		//ship placement stage
    		if(!isReadyToPlay())
    		{
    			boolean occupied = false; //used to check if the spot for the ship (next few grid cells) is occupied
        		
        		if(isHorizontally()) //horizontal placement
        		{
        			
        			if(gc.getXCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //ship out of bounds of game area
            		{
            			for(int i = 0; i < (getGridSize() - gc.getXCoordinate()); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
            				currentGC.setFill(incorrectPlacementColor);
            			}
            		}
            		else //ship in game area
            		{
            			//iterate through the next few grid cells and check if other ship is in the way
            			for(int i = 0; i < getCurrentShipSize(); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
            				if(currentGC.isOccupied())
            				{
            					occupied = true;
            					i = getCurrentShipSize();
            				}            					
            			}
            			
            			if (occupied) //highlight pink and red if another ship is in the way
            			{
            				for(int i = 0; i < getCurrentShipSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                				if(currentGC.isOccupied())//overlap
                				{
                					currentGC.setFill(overlapColor);
                				}
                				else
                				{
                					currentGC.setFill(incorrectPlacementColor);
                				}                				
                			}
            			}
            			else //highlight the next few grid cells as available spot
            			{
            				for(int i = 0; i < getCurrentShipSize(); i++)
                    		{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                				currentGC.setFill(hoverColor);
                    		}
            			}        			
            			
            		}
        		}
        		else //vertical placement
        		{
        			if(gc.getYCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //ship out of bounds of game area
            		{
            			for(int i = 0; i < (getGridSize() - gc.getYCoordinate()); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				currentGC.setFill(incorrectPlacementColor);
            			}
            		}
            		else //ship in game area
            		{
            			//iterate through the next few grid cells and check if other ship is in the way
            			for(int i = 0; i < getCurrentShipSize(); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				if(currentGC.isOccupied())
            				{
            					occupied = true;
            					i = getCurrentShipSize();
            				}
            					
            			}
            			
            			if (occupied) //highlight pink and red if another ship is in the way
            			{
            				for(int i = 0; i < getCurrentShipSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                				if(currentGC.isOccupied()) //overlap
                				{
                					currentGC.setFill(overlapColor);
                				}
                				else
                				{
                					currentGC.setFill(incorrectPlacementColor);
                				}
                				
                			}
            			}
            			else //highlight the next few grid cells as available spot
            			{
            				for(int i = 0; i < getCurrentShipSize(); i++)
                    		{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                				currentGC.setFill(hoverColor);
                    		}
            			}
            			
            		}
        		}
    		}
    		else //game stage
    		{
    			if(gc.isHit() || gc.isMiss()) //grid cell was already hit (or miss)
    			{
    				gc.setFill(incorrectPlacementColor);
    			}
    			else //default hover highlight
    			{
    				gc.setFill(hoverColor);
    			}    			
    		}
    		
    		//update the current grid cell coordinates message
    		textLabel.setText(gc.getXCoordinate() + ":" + gc.getYCoordinate());    		
    	});           
    	
    	//mouse pointer is out of the grid cell (return color back to normal)
        gc.setOnMouseExited(e -> 
        {            	
        	if(!isReadyToPlay()) //ship placement stage
        	{
        		if(isHorizontally()) //horizontal orientation
            	{
            		if(gc.getXCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //out of bounds of game area highlight removal
            		{
                		for(int i = 0; i < (getGridSize() - gc.getXCoordinate()); i++)
            			{
                			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                			if(!currentGC.isOccupied()) //return back to default color
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else //return back to ship placed color
                			{
                				currentGC.setFill(placedShipColor);
                			}
            			}
            		}
            		else //in game area highlight removal
            		{
            			
            			for(int i = 0; i < getCurrentShipSize(); i++)
                		{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                			if(!currentGC.isOccupied()) //return back to default color
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else //return back to ship placed color
                			{
                				currentGC.setFill(placedShipColor);
                			}
                		}
            		}
            	}
            	else //vertical placement
            	{
            		if(gc.getYCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //out of bounds of game area highlight removal
            		{
                		for(int i = 0; i < (getGridSize() - gc.getYCoordinate()); i++)
            			{
                			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				if(!currentGC.isOccupied()) //return back to default color
                			{
                				currentGC.setFill(defaultColor); 
                			}
                			else //return back to ship placed color
                			{
                				currentGC.setFill(placedShipColor);
                			}
            			}
            		}
            		else //in game area highlight removal
            		{
            			for(int i = 0; i < getCurrentShipSize(); i++)
                		{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                			if(!currentGC.isOccupied()) //return back to default color
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else //return back to ship placed color
                			{
                				currentGC.setFill(placedShipColor);
                			}
                		}
            		}
            	}
        	}
        	else //game stage
        	{
        		if(gc.isHit()) //if the cell was hit, return the color to sunk color or hit color
    			{
        			if(gc.isSunk())
        			{
        				gc.setFill(overlapColor);
        			}
        			else
        			{
        				gc.setFill(incorrectPlacementColor);
        			}
    				
    			}
        		else if (gc.isOccupied()) //if cell is occupied
        		{
        			if(isPlayer()) //return to player's placed ship color
        			{
        				gc.setFill(placedShipColor);
        			}
        			else //return to computer's default cell color
        			{
        				gc.setFill(defaultColor);
        			}        			
        		}
        		else if(gc.isMiss()) //if the cell was hit but has no ship (miss), return the color to miss color
        		{
        			gc.setFill(missColor);
        		}
    			else //return to default cell color
    			{
    				gc.setFill(defaultColor);
    			} 
        	}   	
        	
        	//Return coordinated message to it's default value
        	textLabel.setText("x:x");
        	
        });
    }

	
    /**
     * Implementation of an abstract method from Board class. 
     * EventHandler method the controls actions on mouse click on chosen grid cell
     * 
     * @param gc : GridCell that represents the chosen grid cell
     * @param textLabel : Label that represents a status message GUI
     * @param gp : GridPane that represents the game board
     */
    public void onClickChooseGrid(GridCell gc, Label textLabel, GridPane gp) 
    {
    	gc.setOnMouseClicked(e1 ->
    	{
    		if(!isReadyToPlay()) //ship placement stage
			{
				if(!currentShipIsPlaced()) //ship wasn't placed yet
    			{
    				boolean occupied = false; //used to check if the spot for the ship (next few grid cells) is occupied
    				
    				if(isHorizontally()) //horizontal placement
            		{
            			if(gc.getXCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //out of bounds of the game area
                		{
            				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                		}
                		else //in game area
                		{
                			//check if other ship is in the way
                			for(int i = 0; i < getCurrentShipSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                				if(currentGC.isOccupied()) //cell is occupied by another ship
                				{
                					occupied = true;
                					i = getCurrentShipSize();
                				}
                					
                			}
                			
                			if(occupied) //can't place the ship
                			{
                				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                			}
                			else //place the current ship
                			{
                				//Set the chosen grid cells as occupied as player and add in array list
                				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                				for(int i = 0; i < getCurrentShipSize(); i++)
                        		{
                        			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, 
                        					gc.getYCoordinate());
                        			
                        			currentGC.occupy(false);
                        			currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells); //pass the array list to the ship and set and placed
                    			textLabel.setText(getCurrentShipName() + " was placed");
                			}                		
                			
                		}
            		}
            		else //vertical placement
            		{
            			if(gc.getYCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //out of bounds of the game area
                		{
            				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                		}
                		else //in game area
                		{
                			//check if other ship is in the way
                			for(int i = 0; i < getCurrentShipSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                				if(currentGC.isOccupied()) //cell is occupied by another ship
                				{
                					occupied = true;
                					i = getCurrentShipSize();
                				}
                					
                			}
                			
                			if(occupied) //can't place the ship
                			{
                				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                			}
                			else //place the current ship
                			{
                				//Set the chosen grid cells as occupied as player and add in array list
                				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                				for(int i = 0; i < getCurrentShipSize(); i++)
                        		{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                    				currentGC.occupy(false);
                    				currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells); //pass the array list to the ship and set and placed
                    			textLabel.setText(getCurrentShipName() + " was placed");
                			}
                			
                		}
            		}
    			}
    			else //ship was already placed
    			{
    				textLabel.setText(getCurrentShipName() + " was already placed!");
    			}
			}
			else //game stage (click = shoot)
			{
				textLabel.setText("You can't shoot your own board!");    				
			}    			    			   			    			    	
    			
    	});
    }  
    
    /**
     * Implementation of an abstract method from Board class.
     * Controls the passage of the list of grid cells to a current ship (ship placement) and disables ship placement buttons
     * 
     * @param shipCells : ArrayList<GridCell> that represents a list of grid cells that belongs to the current ship
     */
    public void placeCurrentShip(ArrayList<GridCell> shipCells)
    {
    	//Iterate through the list of ships
    	for(Ship ship : getListOfShips())
    	{
    		//Find the current ship
    		if(getCurrentShipName() == ship.getName())
    		{
    			ship.placeShip(shipCells); //pass the list of grid cells to the ship
    			
    			//Disable the ship placement GUI button
    			if(ship.getName() == "Aircraft Carrier")
        		{    				
    	    		placeAircraftCarrier.setDisable(true);
        		}
        		else if(ship.getName() == "Battleship")
        		{
        			placeBattleship.setDisable(true);
        		}
        		else if(ship.getName() == "Cruiser")
        		{
        			placeCruiser.setDisable(true);
        		}
        		else if(ship.getName() == "Submarine")
        		{
        			placeSubmarine.setDisable(true);
        		}
        		else if(ship.getName() == "Destroyer")
        		{
        			placeDestroyer.setDisable(true);
        		}
    			break;
    		}
    	}    	   	
    	
    }
   
    /**
     * Implementation of an abstract method from Board class.
     * Checks if this board belongs to a player or computer
     * 
     * @return true : boolean that states that this board belons to player
     */
    public boolean isPlayer()
    {
    	return true;
    }
    
    
    
    //**********************************************OVERRIDEN METHODS**************************************************************
    
    @Override
    /**
     * End the game and print an appropriate final message
     */
    public void endGame()
    {
    	super.endGame(); //set gameEnd to true
    	
    	Text text; //message
    	
    	if(allShipSunk()) //lost
    	{
    		text = new Text("YOU LOST!");			
    	}
    	else //won
    	{
    		text = new Text("YOU WON!");
    	}
    	
    	displayFinalMessage(text); //send the message
    }    
  
}
    
 
