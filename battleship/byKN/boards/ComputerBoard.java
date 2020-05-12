package boards;


import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import ships.Ship;

/**
 * Class that extends an abstract class Board and represent the specific type of the board - Computer Board.
 * Contains enemy board and auxiliary data for AI fight.
 * Contains methods for board initialization, manipulation, and gameplay logic.
 * 
 * @author Team 8
 * @version 1.0
 *
 */
public class ComputerBoard extends Board{
	
	//Data fields
	PlayerBoard enemy;
	boolean hardLevel = false;
 	
	
	//***************************************CONSTRUCTORS********************************************
	
	/**
	 * Overloaded constructor 
	 * 
	 * @param e : PlayerBoard that represents a player enemy
	 */
	public ComputerBoard(PlayerBoard e)
	{
		super(); //call the constructor of superclass
		
		//Initialization
		enemy = e;
		VBox vbox1 = new VBox(20, getCoordinadesLabel());
		getBoardGUI().getChildren().add(vbox1);
		placeShipsAI();	
	}	
	
	 
	//******************************MUTATOR METHODS***********************************
	/**
	 * Simple fighting AI. Attacks randomly.
	 */
	public void attackEnemy()
	{
		//Get random coordinates
		int x = randomCoordinate();
		int y = randomCoordinate();
		
		while(!enemy.getHit(x, y)) //choose grid on enemy board until attack succeed
		{
			x = randomCoordinate();
			y = randomCoordinate();
		}
		
		enemy.endTurn(); //end turn (check if any ships sunk, if game ended)
	}
	
	
	/**
	 * Random ship placement AI.
	 */
	public void placeShipsAI()
    {
		//coordinated for grid cell
    	int x;
    	int y;
    	
    	//Main ship placement loop
    	while(!allShipsArePlaced())
    	{
    		//Iterate through the list of ships 
    		for(Ship ship : getListOfShips())
    		{
    			//set current ship name and size
    			setCurrentShipName(ship.getName());
    			setCurrentShipSize(ship.getSize());
    			
    			//loop until current ship placed
    			while(!ship.isPlaced())
    			{
    				//get random orientation and coordinates
    				randomOrientation();
    				x = randomCoordinate();
    				y = randomCoordinate();
    				
    				boolean occupied = false; //used to check if chosen grid cells are occupied
    				
    				if(isHorizontally()) //horizontal orientation
            		{
            			if(x + ship.getSize() - 1 < getGridSize()) //in bounds of game board
                		{
            				//check if other ship is in the way
                			for(int i = 0; i < ship.getSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x+i, y);
                				if(currentGC.isOccupied()) //another ship is in the way
                				{
                					occupied = true;
                					i = ship.getSize();
                				}
                					
                			}
                			
                			if(!occupied) //if space is available, place the ship
                			{
                				
                				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>(); //list of grid cells for the ship
                				
                				//Add every chosen grid cell to the list of the grid cells for the ship
                				for(int i = 0; i < ship.getSize(); i++)
                        		{
                        			GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x+i, y);                        			
                        			currentGC.occupy(true);
                        			currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells);  //pass list to the ship                  			
                			}               		
                			
                		}
            		}
            		else //vertical orientation
            		{
            			if(y + ship.getSize() - 1 < getGridSize()) //in bounds of game board
                		{
            				
                			//check if other ship is in the way
                			for(int i = 0; i < ship.getSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x, y + i);
                				if(currentGC.isOccupied()) //another ship is in the way
                				{
                					occupied = true;
                					i = ship.getSize();
                				}
                					
                			}
                			
                			if(!occupied) //if space is available, place the ship
                			{
                				
                				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>(); //list of grid cells for the ship
                				
                				//Add every chosen grid cell to the list of the grid cells for the ship
                				for(int i = 0; i < ship.getSize(); i++)
                        		{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x, y+i);
                    				currentGC.occupy(true);
                    				currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells); //pass list to the ship                   			
                			}
                			
                		}
            		}
    			}
    				
    		}
    	}
        	
    	readyToPlay();
    }
    	
    
    /**
     * Set the current orientation status randomly
     */
    private void randomOrientation()
    {
    	int orientation = (int)(Math.random() * 2) + 1; //get random number (1 or 2)
    	if(orientation == 1) //if 1, flip the orientation, otherwise do nothing
    	{
    		flipOrientation();
    	}
    	
    }
    
    
    
    //*************************************AUXILIARY METHODS*********************************************
    /**
     * Get random coordinate 
     * @return c : int that represents a random coordinate
     */
    private int randomCoordinate()
    {
    	int c = (int)(Math.random() * 10) + 1; //get random number (1 to 10)
    	
    	return c;
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
      	//mouse pointer entered the cell
      	gc.setOnMouseEntered( e -> 
      	{       		
      		if(enemy.isReadyToPlay()) //game stage
      		{    		
      			if(gc.isHit() || gc.isMiss()) //grid cell was already shot
      			{
      				gc.setFill(incorrectPlacementColor);
      			}
      			else //grid cell wasn't hit yet (or miss)
      			{
      				gc.setFill(hoverColor);
      			}    			
      		}
      		else //enemy didn't finish placing ships
      		{
      			gc.setFill(hoverColor);
      		}
      		
      		//Update grid cell coordinated label message
      		textLabel.setText(gc.getXCoordinate() + ":" + gc.getYCoordinate());    		
      	});           
      	
      	//mouse pointer exited the cell
          gc.setOnMouseExited(e -> 
          {        	
          	if(enemy.isReadyToPlay()) //game stage
          	{        	
          		if(gc.isHit()) //if hit, return to sunk or hit color
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
          		else if (gc.isOccupied()) //if occupied, return to default color
          		{
          			
          			gc.setFill(defaultColor);
          			      			
          		}
          		else if(gc.isMiss()) //if miss, return to miss color
          		{
          			gc.setFill(missColor);
          		}
      			else //return to default color
      			{
      				gc.setFill(defaultColor);
      			} 
          	} 
          	else //enemy didn't finish placing ships
          	{
          		gc.setFill(defaultColor);
          	}
          	
          	//Return grid cell coordinated label text to default
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
  			if(enemy.isReadyToPlay() && !enemy.isGameEnded() && !isGameEnded()) //game in progress
  			{
  				if(gc.isOccupied() && !gc.isHit()) //cell is occupied by a ship and wasn't hit yet
  				{
  					//receive hit, send message to enemy, and end turn
  					gc.hitCell();
  					enemy.getTextLabel().setText("HIT!");
  					endTurn();
  					
  					if(isGameEnded()) //lost the game, send the end game state to enemy
  					{
  						enemy.endGame();
  					}
  					else //game continues, hit the enemy
  					{    
  						if(!hardLevel)
  						{
  							attackEnemy();
  						}
  						else
  						{
  							//TODO
      						//call smart AI
  						}
      					
  					}    					
  				}
  				else if (gc.isHit() || gc.isMiss()) //grid cell was hit (or miss), send message to enemy
  				{
  					enemy.getTextLabel().setText("You already tried to hit this cell!");
  				}
  				else
  				{
  					enemy.getTextLabel().setText("MISS!");
  					gc.missCell();      					
  					if(!hardLevel)
  					{
  						attackEnemy();
  					}
  					else
  					{
  						//TODO
  						//call smart AI
  					}
  				} 
  				
  				
  			}
  			else //game is either ended or not started yet
  			{
  				if(!isGameEnded()) //game didn't start, send a message to enemy
  				{
  					enemy.getTextLabel().setText("You can't shoot the enemy until you place your ships!");
  				}
  				else //game ended
  				{
  					enemy.getTextLabel().setText("Game is ended!");
  				}				
  			}  			   			    			    	
  			
  		});
     }
    
    
    /**
     * Implementation of an abstract method from Board class.
     * Checks if this board belongs to a player or computer
     * 
     * @return true : boolean that states that this board belongs to player
     */
    public boolean isPlayer()
    {
    	return false;
    }
    
        
    /**
     * Implementation of an abstract method from Board class.
     * Controls the passage of the list of grid cells to a current ship (ship placement)
     * 
     * @param shipCells : ArrayList<GridCell> that represents a list of grid cells that belongs to the current ship
     */
    public void placeCurrentShip(ArrayList<GridCell> shipCells)
    {
    	//Iterate through the list of ships
    	for(Ship ship : getListOfShips())
    	{
    		if(getCurrentShipName() == ship.getName()) //current ship found
    		{
    			ship.placeShip(shipCells); //pass the list
    			break;
    		}
    	}    	   	
    	
    }
     	
}
 	
