import java.util.function.Function;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class test extends Application {

 	private Label testCoordinates;
 	private Label statusMessage;
 	private Label roundMessage;
 	
 	private int MAX_GRID_SIZE = 11;
 	private int currentShipSize = 2;
 	private int roundCounter = 1;
 	private String currentShipName = "Destroyer";
 	private boolean horizontally = true;
 	private boolean readyToPlay = false;
 	
 	AircraftCarrier ac = new AircraftCarrier();
 	Battleship btls = new Battleship();
 	Cruiser cr = new Cruiser();
 	Submarine sbm = new Submarine();
 	Destroyer ds = new Destroyer();
 	
 	Button placeAircraftCarrier = new Button("AircraftCarrier");
	Button placeBattleship = new Button("Battleship");
	Button placeCruiser = new Button("Cruiser");
	Button placeSubmarine = new Button("Submarine");
	Button placeDestroyer = new Button("Destroyer");
	
	Button playGame = new Button("PLAY!");
 	
 	
 	
	public static void main(String[] args) {
        launch(args);
    }

	/*
	public class GridCell extends Rectangle {
		private int xCoordinate;
		private int yCoordinate;
		private boolean occupied;
		
		GridCell(int x, int y)
		{
			super();
			xCoordinate = x;
			yCoordinate = y;
			occupied = false;
		}
		
		public int getXCoordinate() {
			return xCoordinate;
		}
		
		public int getYCoordinate() {
			return yCoordinate;
		}
		
		public boolean isOccupied()
		{
			return occupied;
		}
		
		public void occupy()
		{
			occupied = true;
			setFill(Color.LIGHTGREEN);
		}
	}
	*/
	
    @Override
    public void start(Stage primaryStage) {
    	
    	Rectangle testCell = new Rectangle();
    	testCell.setFill(Color.ALICEBLUE);
    	testCell.setStroke(Color.DARKBLUE);
    	
    	testCoordinates = new Label("x:x");
    	statusMessage = new Label("status");
    	roundMessage = new Label("");
    	
    	testCell.setWidth(40);
    	testCell.setHeight(40);
    	
    	
    	Button horizontalPlacementButton = new Button("Place Ship Horizontally");
    	Button verticalPlacementButton = new Button("Place Ship Vertically");
    	
    	
    	
    	placeAircraftCarrier.setOnAction(new ChoosingShipHandler(ac.getSize(), ac.getName()));
    	placeBattleship.setOnAction(new ChoosingShipHandler(btls.getSize(), btls.getName()));
    	placeCruiser.setOnAction(new ChoosingShipHandler(cr.getSize(), cr.getName()));
    	placeSubmarine.setOnAction(new ChoosingShipHandler(sbm.getSize(), sbm.getName()));
    	placeDestroyer.setOnAction(new ChoosingShipHandler(ds.getSize(), ds.getName()));
    	
    	playGame.setOnAction(new playGameButtonHandler(statusMessage));
    	
    	horizontalPlacementButton.setOnAction(new HorizontalPlacementHandler());
    	verticalPlacementButton.setOnAction(new VerticalPlacementHandler());
    	
    	
    	
    	
    	GridPane boardTest = new GridPane();
    	boardTest.setMinSize(40, 40);
    	
    	for(int i = 0; i < MAX_GRID_SIZE; i++)
    	{
    		for(int j = 0; j < MAX_GRID_SIZE; j++)
    		{
    			 
    			GridCell cell = new GridCell(i, j);
    	            
    	            cell.setWidth(40);
    	            cell.setHeight(40);
    	            if(i == 0 || j == 0)
    	            {
    	            	cell.setFill(Color.WHITE);
    	            	if(i == 0 && j == 0)
    	            	{
    	            		cell.setStroke(Color.WHITE);
    	            	}
    	            	else
    	            	{
    	            		cell.setStroke(Color.DARKBLUE);
    	            	}
    	            	
    	            }
    	            else
    	            {
    	            	onHoverChangeColor(cell, Color.ALICEBLUE, Color.GREY, testCoordinates, boardTest);
    	            	onClickPlaceShip(cell, statusMessage, boardTest);
    	            	
    	            	
    	            	 cell.setFill(Color.ALICEBLUE);
    	    	         cell.setStroke(Color.DARKBLUE);
    	            }   	         
    	            
    	            boardTest.add(cell, i, j);   			
    		}
    		
    	}
    	
    	
    	VBox vbox1 = new VBox(20, testCoordinates, horizontalPlacementButton, verticalPlacementButton,
    			placeAircraftCarrier, placeBattleship, placeCruiser, placeSubmarine, placeDestroyer, 
    			playGame, statusMessage, roundMessage);
    	
    	
    	HBox testHbox = new HBox(20, boardTest, vbox1);
    	
    	
    	
    	Scene scene = new Scene(testHbox, 800, 600);
        
 	   //Add the Scene to the Stage.
 	   primaryStage.setScene(scene);
       
 	   //Set the stage title.
 	   primaryStage.setTitle("TEST SCENE");
       
 	   // Show the window.
 	   primaryStage.show();
    	
    }
    
    
    //**********************************EVENT HANDLERS*******************************
    
    
    //change color on mouse hover
    //used for planning the chip placement
    private void onHoverChangeColor(GridCell gc, Color defaultColor, Color hoverColor, 
    		Label textLabel, GridPane gp) {
    	
    	gc.setOnMouseEntered( e -> {
    		gc.setFill(hoverColor);
    		
    		if(!readyToPlay)
    		{
    			boolean occupied = false;
        		
        		if(horizontally) //horizontal placement
        		{
        			
        			if(gc.getXCoordinate() + currentShipSize - 1 >= MAX_GRID_SIZE) //if out of bounds
            		{
            			for(int i = 0; i < (MAX_GRID_SIZE - gc.getXCoordinate()); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
            				currentGC.setFill(Color.PALEVIOLETRED);
            			}
            		}
            		else //in bounds
            		{
            			//check if other ship is in the way
            			for(int i = 0; i < currentShipSize; i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
            				if(currentGC.isOccupied())
            				{
            					occupied = true;
            					i = currentShipSize;
            				}
            					
            			}
            			
            			if (occupied) //highlight pink and red if another ship is in the way
            			{
            				for(int i = 0; i < currentShipSize; i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                				if(currentGC.isOccupied())//overlap
                				{
                					currentGC.setFill(Color.RED);
                				}
                				else
                				{
                					currentGC.setFill(Color.PALEVIOLETRED);
                				}
                				
                			}
            			}
            			else //highlight as available spot
            			{
            				for(int i = 0; i < currentShipSize; i++)
                    		{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                				currentGC.setFill(hoverColor);
                    		}
            			}
            			
            			
            		}
        		}
        		else //vertical placement
        		{
        			if(gc.getYCoordinate() + currentShipSize - 1 >= MAX_GRID_SIZE) //if out of bounds
            		{
            			for(int i = 0; i < (MAX_GRID_SIZE - gc.getYCoordinate()); i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				currentGC.setFill(Color.PALEVIOLETRED);
            			}
            		}
            		else //in bounds
            		{
            			//check if other ship is in the way
            			for(int i = 0; i < currentShipSize; i++)
            			{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				if(currentGC.isOccupied())
            				{
            					occupied = true;
            					i = currentShipSize;
            				}
            					
            			}
            			
            			if (occupied) //highlight pink and red if another ship is in the way
            			{
            				for(int i = 0; i < currentShipSize; i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                				if(currentGC.isOccupied()) //overlap
                				{
                					currentGC.setFill(Color.RED);
                				}
                				else
                				{
                					currentGC.setFill(Color.PALEVIOLETRED);
                				}
                				
                			}
            			}
            			else //highlight as available spot
            			{
            				for(int i = 0; i < currentShipSize; i++)
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
    				gc.setFill(Color.PALEVIOLETRED);
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
        	
        	if(!readyToPlay)
        	{
        		if(horizontally)
            	{
            		if(gc.getXCoordinate() + currentShipSize - 1 >= MAX_GRID_SIZE)
            		{
                		for(int i = 0; i < (MAX_GRID_SIZE - gc.getXCoordinate()); i++)
            			{
                			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                			if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(Color.LIGHTGREEN);
                			}
            			}
            		}
            		else
            		{
            			
            			for(int i = 0; i < currentShipSize; i++)
                		{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                			if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(Color.LIGHTGREEN);
                			}
                		}
            		}
            	}
            	else
            	{
            		if(gc.getYCoordinate() + currentShipSize - 1 >= MAX_GRID_SIZE)
            		{
                		for(int i = 0; i < (MAX_GRID_SIZE - gc.getYCoordinate()); i++)
            			{
                			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
            				if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(Color.LIGHTGREEN);
                			}
            			}
            		}
            		else
            		{
            			for(int i = 0; i < currentShipSize; i++)
                		{
            				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                			if(!currentGC.isOccupied())
                			{
                				currentGC.setFill(defaultColor);
                			}
                			else
                			{
                				currentGC.setFill(Color.LIGHTGREEN);
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
        				gc.setFill(Color.RED);
        			}
        			else
        			{
        				gc.setFill(Color.PALEVIOLETRED);
        			}
    				
    			}
        		else if (gc.isOccupied())
        		{
        			gc.setFill(Color.LIGHTGREEN);
        		}
        		else if(gc.isMiss())
        		{
        			gc.setFill(Color.BLUE);
        		}
    			else
    			{
    				gc.setFill(defaultColor);
    			} 
        	}   	
        	
        	
        	textLabel.setText("x:x");
        	
        });
    }
    
    
    
    
    
    //place ship on click 
    private void onClickPlaceShip(GridCell gc, 
    		Label textLabel, GridPane gp) {
    		gc.setOnMouseClicked(e1 -> {
    			
    			if(!readyToPlay)
    			{
    				if(!currentShipIsPlaced())
        			{
        				boolean occupied = false;
        				
        				if(horizontally)
                		{
                			if(gc.getXCoordinate() + currentShipSize - 1 >= MAX_GRID_SIZE)
                    		{
                				textLabel.setText(currentShipName + " can't be placed here!");
                    		}
                    		else
                    		{
                    			//check if other ship is in the way
                    			for(int i = 0; i < currentShipSize; i++)
                    			{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, gc.getYCoordinate());
                    				if(currentGC.isOccupied())
                    				{
                    					occupied = true;
                    					i = currentShipSize;
                    				}
                    					
                    			}
                    			
                    			if(occupied)
                    			{
                    				textLabel.setText(currentShipName + " can't be placed here!");
                    			}
                    			else
                    			{
                    				ArrayList<GridCell> currentShipCells = new ArrayList();
                    				for(int i = 0; i < currentShipSize; i++)
                            		{
                            			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, 
                            					gc.getYCoordinate());
                            			
                            			currentGC.occupy();
                            			currentShipCells.add(currentGC);
                            		}
                        			
                        			placeCurrentShip(currentShipCells);
                        			textLabel.setText(currentShipName + " was placed");
                    			}
                    			
                    		
                    			
                    		}
                		}
                		else
                		{
                			if(gc.getYCoordinate() + currentShipSize - 1 >= MAX_GRID_SIZE)
                    		{
                				textLabel.setText(currentShipName + " can't be placed here!");
                    		}
                    		else
                    		{
                    			//check if other ship is in the way
                    			for(int i = 0; i < currentShipSize; i++)
                    			{
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                    				if(currentGC.isOccupied())
                    				{
                    					occupied = true;
                    					i = currentShipSize;
                    				}
                    					
                    			}
                    			
                    			if(occupied)
                    			{
                    				textLabel.setText(currentShipName + " can't be placed here!");
                    			}
                    			else
                    			{
                    				ArrayList<GridCell> currentShipCells = new ArrayList();
                    				for(int i = 0; i < currentShipSize; i++)
                            		{
                        				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                        				currentGC.occupy();
                        				currentShipCells.add(currentGC);
                            		}
                        			
                        			placeCurrentShip(currentShipCells);
                        			textLabel.setText(currentShipName + " was placed");
                    			}
                    			
                    		}
                		}
        			}
        			else
        			{
        				textLabel.setText(currentShipName + " was already placed!");
        			}
    			}
    			else
    			{
    				if(gc.isOccupied() && !gc.isHit())
    				{
    					gc.hitCell();
    					textLabel.setText("HIT!");
    					endTurn();
    					roundCounter++;
    					roundMessage.setText(String.valueOf(roundCounter));
    				}
    				else if (gc.isHit() || gc.isMiss())
    				{
    					textLabel.setText("You already tried to hit this cell!");
    				}
    				else
    				{
    					textLabel.setText("MISS!");
    					gc.missCell();
    					roundCounter++;
    					roundMessage.setText(String.valueOf(roundCounter));
    				}
    			}
    			
    			    			    	
    			
    	});
    }    
    
    //horizontal placement button handler
    class HorizontalPlacementHandler implements EventHandler<ActionEvent>
    {
 	   public void handle(ActionEvent event)
 	   {
 		  horizontally = true;
 	   }
    }
    
    //vertical placement button handler
    class VerticalPlacementHandler implements EventHandler<ActionEvent>
    {
 	   public void handle(ActionEvent event)
 	   {
 		  horizontally = false;
 	   }
    }
    
    class playGameButtonHandler implements EventHandler<ActionEvent>
    {
    	Label testMessage;
    	public playGameButtonHandler(Label textLabel)
    	{
    		testMessage = textLabel;
    	}
 	   public void handle(ActionEvent event)
 	   {
 		  if(allShipsArePlaced())
 		  {
 			 readyToPlay = true;
 			 testMessage.setText("Ready to Play!");
 		  }
 		  else
 		  {
 			 testMessage.setText("Can't start the game! Some ships weren't placed!");
 		  }
 	   }
    }
    
    //choosing current ship type to be placed button handler
    class ChoosingShipHandler implements EventHandler<ActionEvent>
    {
    	int sizeOfShip;
    	String typeOfShip;
    	
    	public ChoosingShipHandler(int size, String name)
    	{
    		sizeOfShip = size;
    		typeOfShip = name;    		
    	}
    	
 	   	public void handle(ActionEvent event)
 	   	{
 	   		currentShipSize = sizeOfShip;
 	   		currentShipName = typeOfShip;
 	   	}
    }
    
    
   //********************************AXULUARY METHODS***********************************
    
  //get node of the grid cell in grid pane by index
    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
    //check if the current ship was placed
    public boolean currentShipIsPlaced()
    {
    	if(currentShipName == ac.getName())
    	{
    		return ac.isPlaced();
    	}
    	if(currentShipName == btls.getName())
    	{
    		return btls.isPlaced();
    	}
    	if(currentShipName == cr.getName())
    	{
    		return cr.isPlaced();
    	}
    	if(currentShipName == sbm.getName())
    	{
    		return sbm.isPlaced();
    	}
    	if(currentShipName == ds.getName())
    	{
    		return ds.isPlaced();
    	}
    	else
    	{
    		return false;
    	}
    	
    }
    
    //place the current ship
    public void placeCurrentShip(ArrayList<GridCell> shipCells)
    {
    	if(currentShipName == ac.getName())
    	{
    		ac.placeShip(shipCells);
    		placeAircraftCarrier.setDisable(true);
    	}
    	if(currentShipName == btls.getName())
    	{
    		btls.placeShip(shipCells);
    		placeBattleship.setDisable(true);
    	}
    	if(currentShipName == cr.getName())
    	{
    		cr.placeShip(shipCells);
    		placeCruiser.setDisable(true);
    	}
    	if(currentShipName == sbm.getName())
    	{
    		sbm.placeShip(shipCells);
    		placeSubmarine.setDisable(true);
    	}
    	if(currentShipName == ds.getName())
    	{
    		ds.placeShip(shipCells);
    		placeDestroyer.setDisable(true);
    	}    	
    	
    }
    
    public boolean allShipsArePlaced()
    {
    	if(ac.isPlaced() && btls.isPlaced() && cr.isPlaced() && sbm.isPlaced() && ds.isPlaced())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}    	
    }
    
    public void endTurn()
    {
    	
    	ac.isSunk();
    	btls.isSunk();
    	cr.isSunk();
    	sbm.isSunk();
    	ds.isSunk();

    	if(ac.isSunk() &&
    	btls.isSunk() &&
    	cr.isSunk() &&
    	sbm.isSunk() &&
    	ds.isSunk())
    	{
    		statusMessage.setText("GAME IS FINISHED!");
    	}
    	
    }
}