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


public abstract class Board{

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
 	
 	private AircraftCarrier ac = new AircraftCarrier();
 	private Battleship btls = new Battleship();
 	private Cruiser cr = new Cruiser();
 	private Submarine sbm = new Submarine();
 	private Destroyer ds = new Destroyer();
 	
 	protected ArrayList<Ship> listOfShips = new ArrayList<Ship>();
 	
 	private Button placeAircraftCarrier = new Button("AircraftCarrier");
 	private Button placeBattleship = new Button("Battleship");
 	private Button placeCruiser = new Button("Cruiser");
 	private Button placeSubmarine = new Button("Submarine");
 	private Button placeDestroyer = new Button("Destroyer");
	
	//Colors
 	private Color gridSrtokeColor = Color.DARKRED;
 	private Color defaultCellColor = Color.rgb(240, 248, 255, 0.5); //alice blue transparent
 	private Color defaultHoverColor = Color.DARKGREY;
 	private Color inactiveCellColor = Color.rgb(0,0,0,0); //100% transparent
 	protected Color incorrectPlacementColor = Color.PALEVIOLETRED;
 	protected Color overlapColor = Color.RED; 	
 	protected Color missColor = Color.BLUE;
	
 	private Button playGame = new Button("PLAY!");
 	private VBox placeShipsMenu;
 	private StackPane finalMessage;
 	
 	
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
    	            	onClickChooseGrid(cell, statusMessage, boardTest);
    	            	cell.setFill(defaultCellColor);
    	    	        cell.setStroke(gridSrtokeColor);
    	            }   	         
    	            
    	            boardTest.add(cell, i, j);   			
    		}
    		
    	}
    	
    	HBox boardHbox;
    	
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
    		boardHbox = new HBox(20, boardTest, vbox1);  
    	}
    	else
    	{
    		VBox vbox1 = new VBox(20, testCoordinates);
    		boardHbox = new HBox(20, boardTest, vbox1);  
    		placeShipsAI();
    	}
    	
    	
    	
    	 	
    	return boardHbox;	
    }
    
    
    //**********************************EVENT HANDLERS*******************************
    
    
    //change color on mouse hover
    //used for planning the chip placement
    public abstract void onHoverChangeColor(GridCell gc, Color defaultColor, Color hoverColor, 
    		Label textLabel, GridPane gp);
    
        
    //place ship on click 
    public abstract void onClickChooseGrid(GridCell gc, 
    		Label textLabel, GridPane gp);
    
    
    
    
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
    
    public boolean isPlayer()
    {
    	return belongsToPlayer;
    }
    
    public GridPane getGridBoard()
    {
    	return boardTest;
    }
    
    public int getGridSize()
    {
    	return MAX_GRID_SIZE;
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
    
    public boolean isHorizontally()
    {
    	return horizontally;
    }
    
    public void readyToPlay()
    {
    	readyToPlay = true;
    }
    
    public void placeShipsAI()
    {
    	//do nothing
    }
    
    public void setCurrentShipName(String n)
    {
    	currentShipName = n;
		
    }
    
    public void setCurrentShipSize(int s)
    {
    	currentShipSize = s;
    }
    
    public int getCurrentShipSize()
    {
    	return currentShipSize;
    }
    
    public String getCurrentShipName()
    {
    	return currentShipName;
    }
    
    
    
}