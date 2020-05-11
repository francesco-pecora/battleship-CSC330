import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Board{

 	private Label testCoordinates;
 	private Label statusMessage;
 	private Label roundMessage;
 	
 	private int MAX_GRID_SIZE = 11;
 	private int currentShipSize = 2;
 	private int roundCounter = 1;
 	private String currentShipName = "Destroyer";
 	private boolean horizontally = true;
 	private boolean readyToPlay = false;
 	private boolean belongsToPlayer;
 	private boolean turnEnded;
 	private boolean gameEnd = false;
 	
 	private GridPane boardTest;
 	
 	AircraftCarrier ac = new AircraftCarrier();
 	Battleship btls = new Battleship();
 	Cruiser cr = new Cruiser();
 	Submarine sbm = new Submarine();
 	Destroyer ds = new Destroyer();
 	
 	ArrayList<Ship> listOfShips = new ArrayList<Ship>();
 	
 	Button placeAircraftCarrier = new Button("AircraftCarrier");
	Button placeBattleship = new Button("Battleship");
	Button placeCruiser = new Button("Cruiser");
	Button placeSubmarine = new Button("Submarine");
	Button placeDestroyer = new Button("Destroyer");
	
	//Colors
	Color gridSrtokeColor = Color.DARKRED;
	Color defaultCellColor = Color.rgb(240, 248, 255, 0.5); //alice blue transparent
	Color defaultHoverColor = Color.DARKGREY;
	Color inactiveCellColor = Color.rgb(0,0,0,0); //100% transparent
	Color incorrectPlacementColor = Color.PALEVIOLETRED;
	Color overlapColor = Color.RED;
	Color placedShipColor = Color.LIGHTGREEN;
	Color missColor = Color.BLUE;
	
	Button playGame = new Button("PLAY!");
	VBox placeShipsMenu;
 	StackPane finalMessage;
 	
 	
	public Board(boolean playerBoard)
	{
		belongsToPlayer = playerBoard;				
	}
 	
 	public HBox start()
 	{    	    	
    	
    	testCoordinates = new Label("x:x");
    	statusMessage = new Label("status");
    	roundMessage = new Label("1");
      	
    	listOfShips.add(ac);
    	listOfShips.add(btls);
    	listOfShips.add(cr);
    	listOfShips.add(sbm);
    	listOfShips.add(ds);
    	   	
    	//Main GridPane
    	boardTest = new GridPane();
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
    	            	cell.setFill(inactiveCellColor);
    	            	if(i == 0 && j == 0)
    	            	{
    	            		cell.setStroke(inactiveCellColor);
    	            	}
    	            	else
    	            	{
    	            		cell.setStroke(gridSrtokeColor);
    	            	}
    	            	
    	            }
    	            else
    	            {   	            	   	            	
    	            	onHoverChangeColor(cell, defaultCellColor, defaultHoverColor, testCoordinates, boardTest);
    	            	onClickPlaceShip(cell, statusMessage, boardTest);
    	            	cell.setFill(defaultCellColor);
    	    	        cell.setStroke(gridSrtokeColor);
    	            }   	         
    	            
    	            boardTest.add(cell, i, j);   			
    		}
    		
    	}
    	
    	HBox testHbox;
    	
    	if(belongsToPlayer)
    	{
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
        	finalMessage = new StackPane();
        	
        	placeShipsMenu = new VBox(20,horizontalPlacementButton, verticalPlacementButton,
        			placeAircraftCarrier, placeBattleship, placeCruiser, placeSubmarine, placeDestroyer, 
        			playGame);
    		VBox vbox1 = new VBox(20, finalMessage, testCoordinates, placeShipsMenu, statusMessage, roundMessage);
    		testHbox = new HBox(20, boardTest, vbox1);  
    	}
    	else
    	{
    		VBox vbox1 = new VBox(20, testCoordinates);
    		testHbox = new HBox(20, boardTest, vbox1);  
    		placeShipsAI();
    	}
    	
    	
    	
    	 	
    	return testHbox;	
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
            				currentGC.setFill(incorrectPlacementColor);
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
            				currentGC.setFill(incorrectPlacementColor);
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
                				currentGC.setFill(placedShipColor);
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
                				currentGC.setFill(placedShipColor);
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
                				currentGC.setFill(placedShipColor);
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
        			if(belongsToPlayer)
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
    
        
    //place ship on click 
    public void onClickPlaceShip(GridCell gc, 
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
                    				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                    				for(int i = 0; i < currentShipSize; i++)
                            		{
                            			GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate()+i, 
                            					gc.getYCoordinate());
                            			
                            			currentGC.occupy(false);
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
                    				ArrayList<GridCell> currentShipCells = new ArrayList<GridCell>();
                    				for(int i = 0; i < currentShipSize; i++)
                            		{
                        				GridCell currentGC = (GridCell)getNodeFromGridPane(gp, gc.getXCoordinate(), gc.getYCoordinate() + i);
                        				currentGC.occupy(false);
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
    				textLabel.setText("You can't shoot you own board!");    				
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
 			 placeShipsMenu.getChildren().clear();
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
    	
    	for(Ship ship : listOfShips)
    	{
    		ship.isSunk();	
    	}
    	
    	int sunkenShips = 0;
    	for(Ship ship : listOfShips)
    	{
    		if(ship.isSunk())
    		{
    			sunkenShips++;
    		}
	
    	}
    	
    	if(sunkenShips == listOfShips.size())
    	{
    		gameEnd = true;
    		
    		
    		if(belongsToPlayer)
    		{
    			Text text = new Text("YOU LOST");
    			displayFinalMessage(text);
    		} 					
    			
    		
    		
    		statusMessage.setText("GAME IS FINISHED!");
    	}
    	
    	turnEnded = true;
    	roundCounter++;
		roundMessage.setText(String.valueOf(roundCounter));
    	
    }
    public void displayFinalMessage(Text text)
    {
    	Rectangle rec = new Rectangle();
		rec.setWidth(300);
		rec.setHeight(100);
		rec.setFill(defaultCellColor);
		
		finalMessage.getChildren().addAll(rec, text);
		gameEnd = true;
		
    }
    
    public void resetTurn()
    {
    	turnEnded = false;
    }
    
    public boolean getTurn()
    {
    	return turnEnded;
    }
    
    public boolean isReadyToPlay()
    {
    	return readyToPlay;
    }
    
    public boolean isGameEnded()
    {
    	return gameEnd;
    }
    
    public Label getTextLabel()
    {
    	return statusMessage;
    }
    
    public GridPane getGridBoard()
    {
    	return boardTest;
    }
    
    public boolean getHit(int x, int y)
    {
    	if(x < 1 || x > 10 || y < 1 || y > 10)
    	{
    		return false;
    	}
    	else
    	{
    		GridCell currentGC = (GridCell)getNodeFromGridPane(boardTest, x, y);
    		if(currentGC.isHit() || currentGC.isMiss())
    		{
    			return false;
    		}
    		else
    		{
    			if(currentGC.isOccupied())
    			{
    				currentGC.hitCell();
    			}
    			else
    			{
    				currentGC.missCell();
    			}
    		}
    	}
    	return true;
    }
    
    private void placeShipsAI()
    {
    	int x;
    	int y;
    	while(!allShipsArePlaced())
    	{
    		  
    		for(Ship ship : listOfShips)
    		{
    			currentShipName = ship.getName();
    			currentShipSize = ship.getSize();
    			while(!ship.isPlaced())
    			{
    				randomOrientation();
    				x = randomCoordinate();
    				y = randomCoordinate();
    				
    				boolean occupied = false;
    				
    				if(horizontally)
            		{
            			if(x + ship.getSize() - 1 < MAX_GRID_SIZE)
                		{
            				//check if other ship is in the way
                			for(int i = 0; i < ship.getSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(boardTest, x+i, y);
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
                        			GridCell currentGC = (GridCell)getNodeFromGridPane(boardTest, x+i, y);
                        			
                        			currentGC.occupy(true);
                        			currentShipCells.add(currentGC);
                        		}
                    			
                    			placeCurrentShip(currentShipCells);                    			
                			}               		
                			
                		}
            		}
            		else
            		{
            			if(y + ship.getSize() - 1 < MAX_GRID_SIZE)
                		{
            				
                			//check if other ship is in the way
                			for(int i = 0; i < ship.getSize(); i++)
                			{
                				GridCell currentGC = (GridCell)getNodeFromGridPane(boardTest, x, y + i);
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
                    				GridCell currentGC = (GridCell)getNodeFromGridPane(boardTest, x, y+i);
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
        	
    		readyToPlay = true;
    	}
    	
    
    
    private void randomOrientation()
    {
    	int orientation = (int)(Math.random() * 2) + 1;
    	if(orientation == 1)
    	{
    		horizontally = true;
    	}
    	else if(orientation == 2)
    	{
    		horizontally = false;
    	}
    }
    
    public int randomCoordinate()
    {
    	int c = (int)(Math.random() * 10) + 1;
    	return c;
    }
    
    
}