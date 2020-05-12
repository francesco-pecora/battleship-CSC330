import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlayerBoard extends Board{

	private Color placedShipColor = Color.LIGHTGREEN;
	
	public PlayerBoard()
	{
		super(true);
	}
	
	//change color on mouse hover
    //used for planning the chip placement
    public void onHoverChangeColor(GridCell gc, Color defaultColor, Color hoverColor, 
    		Label textLabel, GridPane gp) {
    	
    	gc.setOnMouseEntered( e -> {
    		gc.setFill(hoverColor);
    		
    		if(!isReadyToPlay())
    		{
    			boolean occupied = false;
        		
        		if(isHorizontally()) //horizontal placement
        		{
        			
        			if(gc.getXCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //if out of bounds
            		{
            			for(int i = 0; i < (getGridSize() - gc.getXCoordinate()); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
            				currentGC.setFill(incorrectPlacementColor);
            			}
            		}
            		else //in bounds
            		{
            			//check if other ship is in the way
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
            			else //highlight as available spot
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
        			if(gc.getYCoordinate() + getCurrentShipSize() - 1 >= getGridSize()) //if out of bounds
            		{
            			for(int i = 0; i < (getGridSize() - gc.getYCoordinate()); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				currentGC.setFill(incorrectPlacementColor);
            			}
            		}
            		else //in bounds
            		{
            			//check if other ship is in the way
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
            			else //highlight as available spot
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
    		else
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
    		
    		textLabel.setText(gc.getXCoordinate() + ":" + gc.getYCoordinate());    		
    	});           
    	
        gc.setOnMouseExited(e -> {
        	//gc.setFill(defaultColor);
        	
        	if(!isReadyToPlay())
        	{
        		if(isHorizontally())
            	{
            		if(gc.getXCoordinate() + getCurrentShipSize() - 1 >= getGridSize())
            		{
                		for(int i = 0; i < (getGridSize() - gc.getXCoordinate()); i++)
            			{
                			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                			if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(placedShipColor);
                			}
            			}
            		}
            		else
            		{
            			
            			for(int i = 0; i < getCurrentShipSize(); i++)
                		{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                			if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(placedShipColor);
                			}
                		}
            		}
            	}
            	else
            	{
            		if(gc.getYCoordinate() + getCurrentShipSize() - 1 >= getGridSize())
            		{
                		for(int i = 0; i < (getGridSize() - gc.getYCoordinate()); i++)
            			{
                			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(placedShipColor);
                			}
            			}
            		}
            		else
            		{
            			for(int i = 0; i < getCurrentShipSize(); i++)
                		{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                			if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(placedShipColor);
                			}
                		}
            		}
            	}
        	}
        	else
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
        			if(isPlayer())
        			{
        				gc.setFill(placedShipColor);
        			}
        			else
        			{
        				gc.setFill(defaultColor);
        			}        			
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
        	
        	
        	textLabel.setText("x:x");
        	
        });
    }

    public void onClickChooseGrid(GridCell gc, 
    		Label textLabel, GridPane gp) {
    		gc.setOnMouseClicked(e1 -> {
    			
    			if(!isReadyToPlay())
    			{
    				if(!currentShipIsPlaced())
        			{
        				boolean occupied = false;
        				
        				if(isHorizontally())
                		{
                			if(gc.getXCoordinate() + getCurrentShipSize() - 1 >= getGridSize())
                    		{
                				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                    		}
                    		else
                    		{
                    			//check if other ship is in the way
                    			for(int i = 0; i < getCurrentShipSize(); i++)
                    			{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                    				if(currentGC.isOccupied())
                    				{
                    					occupied = true;
                    					i = getCurrentShipSize();
                    				}
                    					
                    			}
                    			
                    			if(occupied)
                    			{
                    				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                    			}
                    			else
                    			{
                    				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                    				for(int i = 0; i < getCurrentShipSize(); i++)
                            		{
                            			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, 
                            					gc.getYCoordinate());
                            			
                            			currentGC.occupy(false);
                            			currentShipCells.add(currentGC);
                            		}
                        			
                        			placeCurrentShip(currentShipCells);
                        			textLabel.setText(getCurrentShipName() + " was placed");
                    			}
                    			
                    		
                    			
                    		}
                		}
                		else
                		{
                			if(gc.getYCoordinate() + getCurrentShipSize() - 1 >= getGridSize())
                    		{
                				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                    		}
                    		else
                    		{
                    			//check if other ship is in the way
                    			for(int i = 0; i < getCurrentShipSize(); i++)
                    			{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                    				if(currentGC.isOccupied())
                    				{
                    					occupied = true;
                    					i = getCurrentShipSize();
                    				}
                    					
                    			}
                    			
                    			if(occupied)
                    			{
                    				textLabel.setText(getCurrentShipName() + " can't be placed here!");
                    			}
                    			else
                    			{
                    				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                    				for(int i = 0; i < getCurrentShipSize(); i++)
                            		{
                        				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                        				currentGC.occupy(false);
                        				currentShipCells.add(currentGC);
                            		}
                        			
                        			placeCurrentShip(currentShipCells);
                        			textLabel.setText(getCurrentShipName() + " was placed");
                    			}
                    			
                    		}
                		}
        			}
        			else
        			{
        				textLabel.setText(getCurrentShipName() + " was already placed!");
        			}
    			}
    			else
    			{
    				textLabel.setText("You can't shoot you own board!");    				
    			}    			   			    			    	
    			
    	});
    }    
}
