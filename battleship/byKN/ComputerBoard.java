
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ComputerBoard extends Board{
	
	Board enemy;
	boolean hardLevel = false;
 	
	public ComputerBoard(Board e)
	{
		super(false);	
		enemy = e;
	}
	
	//change color on mouse hover    
    public void onHoverChangeColor(GridCell gc, Color defaultColor, Color hoverColor, 
    		Label textLabel, GridPane gp) {
    	
    	//mouse entered the cell
    	gc.setOnMouseEntered( e -> {    		
    		
    		if(enemy.isReadyToPlay())
    		{    		
    			if(gc.isHit() || gc.isMiss())
    			{
    				gc.setFill(incorrectPlacementColor);
    			}
    			else
    			{
    				gc.setFill(hoverColor);
    			}    			
    		}
    		else
    		{
    			gc.setFill(hoverColor);
    		}
    		
    		textLabel.setText(gc.getXCoordinate() + ":" + gc.getYCoordinate());    		
    	});           
    	
    	//mouse exited the cell
        gc.setOnMouseExited(e -> {
        	//gc.setFill(defaultColor);
        	
        	if(enemy.isReadyToPlay())
        	{        	
        		if(gc.isHit())
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
        		else if (gc.isOccupied())
        		{
        			
        			gc.setFill(defaultColor);
        			      			
        		}
        		else if(gc.isMiss())
        		{
        			gc.setFill(missColor);
        		}
    			else
    			{
    				gc.setFill(defaultColor);
    			} 
        	} 
        	else
        	{
        		gc.setFill(defaultColor);
        	}
        	
        	
        	textLabel.setText("x:x");
        	
        });
    }
	
    //click on cell 
	public void onClickChooseGrid(GridCell gc, 
    		Label textLabel, GridPane gp) {
    		gc.setOnMouseClicked(e1 -> {
    			if(enemy.isReadyToPlay() && !enemy.isGameEnded() && !isGameEnded())
    			{
    				if(gc.isOccupied() && !gc.isHit())
    				{
    					gc.hitCell();
    					enemy.getTextLabel().setText("HIT!");
    					endTurn();
    					if(isGameEnded())
    					{
    						Text text = new Text("YOU WON!");
    						enemy.displayFinalMessage(text);
    					}
    					else
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
    				else if (gc.isHit() || gc.isMiss())
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
    			else
    			{
    				if(!isGameEnded())
    				{
    					enemy.getTextLabel().setText("You can't shoot the enemy until you place your ships!");
    				}
    				else
    				{
    					enemy.getTextLabel().setText("Game is ended!");
    				}
    				
    			}    			   			    			    	
    			
    	});
    } 
	
	public void attackEnemy()
	{
		int x = randomCoordinate();
		int y = randomCoordinate();
		
		while(!enemy.getHit(x, y))
		{
			x = randomCoordinate();
			y = randomCoordinate();
		}
		
		enemy.endTurn();
	}
	
	public void placeShipsAI()
    {
    	int x;
    	int y;
    	while(!allShipsArePlaced())
    	{
    		  
    		for(Ship ship : listOfShips)
    		{
    			setCurrentShipName(ship.getName());
    			setCurrentShipSize(ship.getSize());
    			while(!ship.isPlaced())
    			{
    				randomOrientation();
    				x = randomCoordinate();
    				y = randomCoordinate();
    				
    				boolean occupied = false;
    				
    				if(isHorizontally())
            		{
            			if(x + ship.getSize() - 1 < getGridSize())
                		{
            				//check if other ship is in the way
                			for(int i = 0; i < ship.getSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x+i, y);
                				if(currentGC.isOccupied())
                				{
                					occupied = true;
                					i = ship.getSize();
                				}
                					
                			}
                			
                			if(!occupied)
                			{
                				
                				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                				for(int i = 0; i < ship.getSize(); i++)
                        		{
                        			GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x+i, y);
                        			
                        			currentGC.occupy(true);
                        			currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells);                    			
                			}               		
                			
                		}
            		}
            		else
            		{
            			if(y + ship.getSize() - 1 < getGridSize())
                		{
            				
                			//check if other ship is in the way
                			for(int i = 0; i < ship.getSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x, y + i);
                				if(currentGC.isOccupied())
                				{
                					occupied = true;
                					i = ship.getSize();
                				}
                					
                			}
                			
                			if(!occupied)
                			{
                				
                				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                				for(int i = 0; i < ship.getSize(); i++)
                        		{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(getGridBoard(), x, y+i);
                    				currentGC.occupy(true);
                    				currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells);                    			
                			}
                			
                		}
            		}
    			}
    				
    			}
    		}
        	
    		readyToPlay();
    	}
    	
    
    
    private void randomOrientation()
    {
    	int orientation = (int)(Math.random() * 2) + 1;
    	if(orientation == 1)
    	{
    		flipOrientation();
    	}
    	
    }
    
    private int randomCoordinate()
    {
    	int c = (int)(Math.random() * 10) + 1;
    	return c;
    }
 	
}
 	
