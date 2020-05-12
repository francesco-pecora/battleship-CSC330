package ships;
import java.util.ArrayList;
import boards.GridCell;

/**
 * Abstract class that represent a ship in battleship game
 * Contains the following data about the ship:
 * 	- 	number of grids in occupies
 * 	-	boolean value that represents if the ship is sunk or not
 * 	-	boolean value that represents if the ship was placed on a board or not
 * 	-	array list of grid cells that are part of the ship
 * 
 * Contains accessor methods and method that allows to assign 
 * grid cells to the array list that represents a ship
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */
public abstract class Ship {
	
	//data fields
	private int numOfGrids;
	boolean sunk;
	boolean placed;	
	ArrayList<GridCell> shipCells;
	
	
	//******************************CONSTRUCTORS******************************
	/**
	 * Default constructor
	 * 
	 * Sets default values to numOfGrids, sunk, and placed
	 */
	public Ship()
	{
		numOfGrids = 0;
		sunk = false;
		placed = false;								
	}
	
	/**
	 * Overloaded Constructor that takes one parameter for
	 * number of grids.
	 * Sets default values of false to sunk and placed.
	 * 
	 * 
	 * @param ng : int that represents a number of grids for a ship
	 */
	public Ship(int ng)
	{
		numOfGrids = ng;
		sunk = false;
		placed = false;						
	}
	
	
	//**************************ACCESSOR METHODS**********************************
	
	/**
	 * Returns number of grids of a ship
	 * 
	 * @return numOfGrids : boolean that represents a number of grids of a ship
	 */
	public int getSize()
	{
		return numOfGrids;	
	}
	
	
	/**
	 * Returns the status of placement of the ship
	 * 
	 * @return placed : boolean that represents the status of placement of the ship
	 */
	public boolean isPlaced()
	{
		return placed;
	}
	
	/**
	 * Return the status of activity of the ship: sunk or not sunk.
	 * If not sunk, checks each grid cell if it was hit. Sets sunk to true if all grid cells
	 * were hit. 
	 * 
	 * 
	 * @return sunk : boolean that represents the status of activity of the ship: sunk or not sunk.
	 */
	public boolean isSunk()
	{
		if(sunk == true) //confirm if sunk
		{
			return sunk;
		}
		else //if not, check the grid cells
		{
			int sunkenIndex = 0;
			for(GridCell cell : shipCells)
			{
				//check if the cell was hit
				if(cell.isHit()) {
					sunkenIndex++;
				}
			}
			if(sunkenIndex == numOfGrids) //ship is sunken if all grid cells were hit
			{
				sunk = true;
				for(GridCell cell : shipCells)
				{
					//sunk the cell
					cell.sunkCell();
				}				
			}		
			return sunk;
		}		
		
	}
	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Receives an ArrayList of grid cells from main game GUI as a parameter that represents where the ship was placed.
	 * Assign that list to the shipCells ArrayList. Changes status of placed to true.
	 * 
	 * @param shipGrid : ArrayList<GridCell> that represents grid cells where the ship was placed
	 */
	public void placeShip(ArrayList<GridCell> shipGrid)
	{			
		shipCells = shipGrid;
		placed = true;
	}	
	
	//******************************ABSTRACT METHODS********************************
	/**
	 * Abstract method that returns name of a ship
	 */
	public abstract String getName();
	
}
