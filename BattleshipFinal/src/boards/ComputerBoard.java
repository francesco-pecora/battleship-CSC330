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
	private PlayerBoard enemy;
	private boolean hardLevel = false;
 	
	//Coordinates of the attack
		private int xHit = -1;
		private int yHit = -1;
		
		//Variables for smart AI to control the hit to the nearby cells
		private boolean up = false;
		private boolean right = false;
		private boolean down = false;
		private boolean left = false;
		private boolean neighborsChecked = true;
		private int hDirection = 0;
		private int vDirection = 0;
		private boolean changedDirection = false;
		
		
		private ArrayList<GridCell> bufferAI = new ArrayList<GridCell>();
		private ArrayList<GridCell> allHitCellsBuffer = new ArrayList<GridCell>();
		
		private GridCell currentGC;
	
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
	
	public void setHardLevel(boolean s)
	{
		hardLevel = s;
	}
	
	
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
		
		//save for smart AI
		if(hardLevel)
		{
			GridCell gc = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), x, y);
			if(gc.isHit())
			{
				xHit = x;
				yHit = y;	
				bufferAI.add(gc);
				allHitCellsBuffer.add(gc);
			}		
			else
			{
				bufferAI.clear();
				foundHitNotSunkCell();
			}
		}
		
		enemy.endTurn(); //end turn (check if any ships sunk, if game ended)
	}
	
	
public void smartAttackEnemy() {
		 
		// the x and y value have not been set before
		if(xHit == -1 && yHit == -1) {
			attackEnemy();
			return;
		}
		
		currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit);
		
		//if the ship from the previous turn sunk, attack randomly
		if(currentGC.isSunk())		
		{			
			//check if other cells in buffer haven't sunk
			clearAIBuffer();			
			
			
			if(!foundHitNotSunkCell()) //check if there are hit cells that has not sunk yet, if not, attack randomly
			{
				attackEnemy();
				return;
			}
			
		}
		
		if(bufferAI.isEmpty()) //if current pattern buffer is empty, check if there are hit cells that has not sunk yet, add it to buffer
		{
			if(foundHitNotSunkCell())
			{				
				bufferAI.add(currentGC);				
			}
		}		
		
		
		// if first guess is a spot occupied by a ship, we check neighbors
		if(currentGC.isHit()) neighborsChecked = false;
		
		if(!neighborsChecked) {
			
			// handles out of bounds coordinates
			if(xHit == 1) left = true;
			if(yHit == 1) up = true;
			if(xHit == 10) right = true;
			if(yHit == 10) down = true;
			
			
			// checking if cell above was visited
			if(!up) {
				currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit - 1);
				if(currentGC.isHit() || currentGC.isMiss() || currentGC.isSunk()) {
					up = true;	// skip the neighbor above 
				}
			}
			
			
			// checking if cell to the right was visited
			if(!right) {
				currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit + 1, yHit);
				if(currentGC.isHit() || currentGC.isMiss() || currentGC.isSunk()) {
					right = true;	// skip the neighbor to the right
				}
			}
			
			
			// checking if cell below was visited
			if(!down) {
				currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit + 1);
				if(currentGC.isHit() || currentGC.isMiss() || currentGC.isSunk()) {
					down = true;	// skip the neighbor below
				}
			}
			
			
			// checking if cell to the left was visited
			if(!left) {
				currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit - 1, yHit);
				if(currentGC.isHit() || currentGC.isMiss() || currentGC.isSunk()) {
					left = true;	// skip the neighbor to the left
				}
			}

			
			
			if(bufferAI.size() >= 2) //if pattern found, follow the buffer
			{
				smartAIBufferHandler();
			}
			else //pattern is not found, shoot around the cell				
			{
				if(!up) {
					up = true;
					enemy.getHit(xHit, yHit - 1);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit - 1);
					if(currentGC.isHit() || currentGC.isSunk()) {
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						yHit--;
					}
				}
				else if (!right) {
					right = true;
					enemy.getHit(xHit + 1, yHit);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit + 1, yHit);
					if(currentGC.isHit() || currentGC.isSunk()) {
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						xHit++;
					}
				}
				else if (!down) {
					down = true;
					enemy.getHit(xHit, yHit + 1);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit + 1);
					if(currentGC.isHit() || currentGC.isSunk()) {
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						yHit++;
					}
				}
				else if (!left) {
					left = true;
					enemy.getHit(xHit - 1, yHit);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit - 1, yHit);
					if(currentGC.isHit() || currentGC.isSunk()) {
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						xHit--;
					}
				}
				else {
					up = false;
					right = false;
					down = false;
					left = false;
					neighborsChecked = true;
					attackEnemy();
					return;			// avoid calling endTurn two times
				}
			}
			
			
			

			up = false;
			right = false;
			down = false;
			left = false;
			
		}		
		// if we haven't met a hit, we keep checking randomly
		else {
			attackEnemy();
			currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit);
			if(currentGC.isHit()) {
				neighborsChecked = false;
			}
			return;
		}
		
		enemy.endTurn();
	}
	


	/**
	 * Pattern recognition buffer handler. If finds a pattern (horizontal or vertical) it follows it.
	 * Has a dead end avoidance logic (goes the other way), and a block exit logic (go back to random)
	 */
	public void smartAIBufferHandler()
	{
		//Pattern support
		boolean blockedHit = false;
		int[] xC = new int[bufferAI.size()];
		int[] yC = new int[bufferAI.size()];
		int index = 0;	
		int diffX = -2;
		int diffY = -2;
		GridCell neighborGC;
		
		if(vDirection == 0 && hDirection == 0) //if the first time
		{
			for(GridCell cell : bufferAI)
			{
				xC[index] = cell.getXCoordinate();
				yC[index] = cell.getYCoordinate();
				index++;
			}
			
			diffX = xC[1] - xC[0];
			diffY = yC[1] - yC[0];
		}
		
		
		if(vDirection != 0 || diffX == 0) //vertical direction
		{
						
			if(vDirection == -1 || diffY == -1) //shoot up
			{
				vDirection = -1;
				if(!up) 
				{
					up = true;
					enemy.getHit(xHit, yHit - 1);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit - 1);
					if(currentGC.isHit() || currentGC.isSunk()) 
					{
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						yHit--;
					}
				}
				else //can't shoot up
				{
					//get the opposite side of the known hits and shoot down 
					if(changedDirection) //if already changed direction, blocked
					{
						blockedHit = true;
					}
					else
					{
						changedDirection = true;
						if(bufferAI.get(0).getYCoordinate() + 1 > 10) //out of bounds, block 
						{
							currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
							yHit = bufferAI.get(0).getYCoordinate();
							xHit = bufferAI.get(0).getXCoordinate();
							
							blockedHit = true;
						}
						else
						{
							neighborGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate() + 1);
							if(neighborGC.isMiss()) //miss cell, block
							{
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
								yHit = bufferAI.get(0).getYCoordinate();
								xHit = bufferAI.get(0).getXCoordinate();
								
								blockedHit = true;
							}
							else
							{
								enemy.getHit(bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate() + 1);
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate() + 1);
								if(currentGC.isHit() || currentGC.isSunk()) 
								{						
									yHit = bufferAI.get(0).getYCoordinate() + 1;
									bufferAI.add(currentGC);
									allHitCellsBuffer.add(currentGC);
									vDirection = 1;
								}
							}					
							
						}
					}
					
										
				}
			}
			else if (vDirection == 1 || diffY == 1) //shoot down
			{
				vDirection = 1;
				if (!down) 
				{
					down = true;
					enemy.getHit(xHit, yHit + 1);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit, yHit + 1);
					if(currentGC.isHit() || currentGC.isSunk()) 
					{
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						yHit++;
					}
				}
				else //can't shoot down
				{
					if(changedDirection) //if already changed direction, blocked 
					{
						blockedHit = true;
					}
					else
					{
						//get the opposite side of the known hits and shoot up 
						changedDirection = true;
						
						if(bufferAI.get(0).getYCoordinate() - 1 < 1)  //out of bounds, block 
						{
							currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
							yHit = bufferAI.get(0).getYCoordinate();
							xHit = bufferAI.get(0).getXCoordinate();
							
							blockedHit = true;
						}
						else
						{
							neighborGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate() - 1);
							if(neighborGC.isMiss())  //miss cell, block
							{
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
								yHit = bufferAI.get(0).getYCoordinate();
								xHit = bufferAI.get(0).getXCoordinate();
								
								blockedHit = true;
							}
							else
							{
								enemy.getHit(bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate() - 1);
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate() - 1);
								if(currentGC.isHit() || currentGC.isSunk()) 
								{						
									yHit = bufferAI.get(0).getYCoordinate() - 1;
									bufferAI.add(currentGC);
									allHitCellsBuffer.add(currentGC);
									vDirection = -1;
								}
							}
							
						}
					}				
					
				}
			}			
			
		}
		else if(hDirection != 0 || diffY == 0) //horizontal direction
		{			
			
			if(hDirection == - 1 || diffX == -1) //shoot to the left
			{
				hDirection = - 1;
				if (!left) 
				{
					left = true;
					enemy.getHit(xHit - 1, yHit);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit - 1, yHit);
					if(currentGC.isHit() || currentGC.isSunk()) 
					{
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						xHit--;
					}
				}
				else //can't shoot to the left
				{
					if(changedDirection) //if already changed direction, blocked 
					{
						blockedHit = true;
					}
					else
					{
						//get the opposite side of the known hits and shoot right 
						changedDirection = true;
						
						if(bufferAI.get(0).getXCoordinate() + 1 > 10)  //out of bounds, block 
						{
							currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
							yHit = bufferAI.get(0).getYCoordinate();
							xHit = bufferAI.get(0).getXCoordinate();
							
							blockedHit = true;
						}
						else
						{
							neighborGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate() + 1, bufferAI.get(0).getYCoordinate());
							if(neighborGC.isMiss()) //miss cell, block
							{
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
								yHit = bufferAI.get(0).getYCoordinate();
								xHit = bufferAI.get(0).getXCoordinate();
								
								blockedHit = true;
							}
							else
							{
								enemy.getHit(bufferAI.get(0).getXCoordinate() + 1, bufferAI.get(0).getYCoordinate());
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate() + 1, bufferAI.get(0).getYCoordinate());
								if(currentGC.isHit() || currentGC.isSunk()) 
								{						
									xHit = bufferAI.get(0).getXCoordinate() + 1;
									bufferAI.add(currentGC);
									allHitCellsBuffer.add(currentGC);
									hDirection = 1;
								}
							}
							
						}	
					}
									
					
				}
				
			}
			else if (hDirection == 1 || diffX == 1) //shoot to the right
			{
				hDirection = 1;
				if (!right) 
				{
					right = true;
					enemy.getHit(xHit + 1, yHit);
					currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), xHit + 1, yHit);
					if(currentGC.isHit() || currentGC.isSunk()) 
					{
						bufferAI.add(currentGC);
						allHitCellsBuffer.add(currentGC);
						xHit++;
					}
				}
				else //can't shoot to the right
				{
					if(changedDirection) //if already changed direction, blocked
					{
						blockedHit = true;
					}
					else
					{
						//get the opposite side of the known hits and shoot left 
						changedDirection = true;
						if(bufferAI.get(0).getXCoordinate() - 1 < 1) //out of bounds
						{						
							currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
							yHit = bufferAI.get(0).getYCoordinate();
							xHit = bufferAI.get(0).getXCoordinate();
							
							blockedHit = true;
						}
						else
						{
							neighborGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate() - 1, bufferAI.get(0).getYCoordinate());
							if(neighborGC.isMiss()) //miss cell, block
							{
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate(), bufferAI.get(0).getYCoordinate());
								yHit = bufferAI.get(0).getYCoordinate();
								xHit = bufferAI.get(0).getXCoordinate();
								
								blockedHit = true;
							}
							else
							{
								enemy.getHit(bufferAI.get(0).getXCoordinate() - 1, bufferAI.get(0).getYCoordinate());
								currentGC = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), bufferAI.get(0).getXCoordinate() - 1, bufferAI.get(0).getYCoordinate());
								if(currentGC.isHit() || currentGC.isSunk()) 
								{						
									xHit = bufferAI.get(0).getXCoordinate() - 1;
									bufferAI.add(currentGC);
									allHitCellsBuffer.add(currentGC);
									hDirection = - 1;
								}
							}
							
						}
					}
					
					
				}
			}
				
		}
		
		if(blockedHit) //if blocked in patter, break, go random again (bug fix - has to go random for at least one turn)
		{	
			attackEnemy();
			clearAIBuffer();			
		}
		
		
	}
	
	/**
	 * Clears pattern buffer
	 */
	public void clearAIBuffer()
	{
		hDirection = 0;
		vDirection = 0;
		changedDirection = false;		
		
		bufferAI.clear();
		
		foundHitNotSunkCell(); //check if there are hit but not sunk cells exist
	}
	
	
	/**
	 * Find if there are hit but not sunk cells exist
	 * 
	 * @return foundHitbNotSunk : boolean that represents if there are hit but not sunk cells exist
	 */
	public boolean foundHitNotSunkCell()
	{
		//Search support
		boolean foundHitbNotSunk = false;
		boolean blocked = false;
		GridCell[] neighbors = new GridCell[4];
		bufferAI.clear();
		ArrayList<GridCell> availableCells = new ArrayList<GridCell>();
		
		for(GridCell cell : allHitCellsBuffer) //iterate through all hit cells
		{
			if(cell.isHit() && !cell.isSunk()) //check if it not sunk
			{
				if(cell.getXCoordinate() == 1 && cell.getYCoordinate() == 1) //upper left corner
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate()+1);
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate()+1, cell.getYCoordinate());
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss())) //blocked
					{
						blocked = true; 
					}
				}
				else if(cell.getXCoordinate() == 10 && cell.getYCoordinate() == 1) //upper right corner
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate()+1);
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() - 1, cell.getYCoordinate());
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss())) //blocked
					{
						blocked = true; 
					}
				}
				else if(cell.getXCoordinate() == 1 && cell.getYCoordinate() == 10) //bottom left corner
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() - 1);
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() + 1, cell.getYCoordinate());
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss())) //blocked
					{
						blocked = true; 
					}
				}
				else if(cell.getXCoordinate() == 10 && cell.getYCoordinate() == 10) //bottom right corner
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() - 1);
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() - 1, cell.getYCoordinate());
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss())) //blocked
					{
						blocked = true; 
					}
				}
				else if(cell.getXCoordinate() == 1) //left side
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate()+1); //down
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() + 1, cell.getYCoordinate()); //right
					neighbors[2] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() - 1); //up
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss()) //blocked
							&& (neighbors[2].isHit() ||  neighbors[2].isMiss()))
					{
						blocked = true; 
					}
				}
				else if(cell.getXCoordinate() == 10) //right side
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate()+1); //down
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() - 1, cell.getYCoordinate()); //left
					neighbors[2] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() - 1); //up
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss()) //blocked
							&& (neighbors[2].isHit() ||  neighbors[2].isMiss()))
					{
						blocked = true; 
					}
				}
				else if(cell.getYCoordinate() == 1) //upper side
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() + 1, cell.getYCoordinate()); //right
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() + 1); //down
					neighbors[2] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() - 1, cell.getYCoordinate()); //left
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss()) //blocked
							&& (neighbors[2].isHit() ||  neighbors[2].isMiss()))
					{
						blocked = true; 
					}
				}
				else if(cell.getYCoordinate() == 10) //bottom side
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() + 1, cell.getYCoordinate()); //right
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() - 1); //up
					neighbors[2] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() - 1, cell.getYCoordinate()); //left
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss()) //blocked
							&& (neighbors[2].isHit() ||  neighbors[2].isMiss()))
					{
						blocked = true; 
					}
				}
				else //no out of bounds limitation
				{
					neighbors[0] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() + 1, cell.getYCoordinate()); //right
					neighbors[1] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() - 1); //up
					neighbors[2] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate() - 1, cell.getYCoordinate()); //left
					neighbors[3] = (GridCell)getNodeFromGridPane(enemy.getGridBoard(), cell.getXCoordinate(), cell.getYCoordinate() + 1); //down
					
					if((neighbors[0].isHit() || neighbors[0].isMiss()) && (neighbors[1].isHit() ||  neighbors[1].isMiss()) 
							&& (neighbors[2].isHit() ||  neighbors[2].isMiss()) && (neighbors[3].isHit() ||  neighbors[3].isMiss())) //blocked
					{
						blocked = true; 
					}
				}
				
				if(!blocked) //if not block add current cell
				{					
					availableCells.add(cell);
				}						
			}
		}
		
		if(!availableCells.isEmpty()) //cells are available, pick random
		{
			int randomMax = availableCells.size();
			
			int c = (int)(Math.random() * randomMax); //get random number (1 to 10)
			System.out.println("Size: " + randomMax + " choice: "+ c);
			currentGC = availableCells.get(c);
			xHit = currentGC.getXCoordinate();
			yHit = currentGC.getYCoordinate();
			bufferAI.add(currentGC);
			foundHitbNotSunk = true;
		}
		
		
		return foundHitbNotSunk;
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
  							smartAttackEnemy();
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
  						smartAttackEnemy();
  					}
  				} 
  				
  				
  			}
  			else //game is either ended or not started yet
  			{
  				if(!enemy.isReadyToPlay()) //game didn't start, send a message to enemy
  				{
  					enemy.getTextLabel().setText("You can't shoot the enemy until you place your ships!");
  				}
  				else if(isGameEnded() || enemy.isGameEnded()) //game ended  				
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
 	
