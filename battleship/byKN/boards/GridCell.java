package boards;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class that extends Rectangle class (javafx.scene.shape) that represent a grid cell in battleship game
 * Contains the following data about the grid cell:
 * 	- 	x coordinate.
 * 	- 	y coordinate.
 * 	-	boolean value that represents if this cell is occupied by a ship or not.
 * 	-	boolean value that represents if this cell was a hit shot (cell is part of the ship) or not.
 * 	-	boolean value that represents if this cell was a missed shot (cell is not part of the ship) or not.
 * 	-	boolean value that represents if the ship is sunk or not.
 * 
 * Contains accessor methods and mutator methods.
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */
public class GridCell extends Rectangle{
	
	//data fields
	private int xCoordinate;
	private int yCoordinate;
	private boolean occupied;
	private boolean hit;
	private boolean miss;
	private boolean sunk;
	
	
	//******************************CONSTRUCTORS******************************
	
	/**
	 * Overloaded constructor that takes x and y coordinates as parameters.
	 * Sets all boolean values to false.
	 * 
	 * @param x : int that represents X coordinate
	 * @param y : int that represents Y coordinate
	 */
	GridCell(int x, int y)
	{
		super();
		xCoordinate = x;
		yCoordinate = y;
		occupied = false;
		hit = false;
		sunk = false;
		miss = false;
	}
	
	
	//**************************ACCESSOR METHODS**********************************
	
	/**
	 * Returns X coordinate.
	 * 
	 * @return xCoordinate : int that represents X coordinate 
	 */
	public int getXCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * Returns Y coordinate.
	 * 
	 * @return yCoordinate : int that represents Y coordinate
	 */
	public int getYCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * Returns the status of the grid cell - is it occupied by a ship or not.
	 * 
	 * @return occupied : boolean that represents the status of the grid cell - is it occupied by a ship or not.
	 */
	public boolean isOccupied()
	{
		return occupied;
	}
	
	/**
	 * Returns the status of the grid cell - was it hit (cell is part of the ship) or not.
	 * 
	 * @return hit : boolean that represents the status of the grid cell - was it hit (cell is part of the ship) or not.
	 */
	public boolean isHit()
	{
		return hit;
	}
	
	/**
	 * Returns the status of the grid cell - is it sunken (cell is part of the sunken ship) or not.
	 * 
	 * @return sunk : boolean that represents the status of the grid cell - is it sunken (cell is part of the sunken ship) or not.
	 */
	public boolean isSunk()
	{
		return sunk;
	}
	
	/**
	 * Returns the status of the grid cell - was it hit (cell is not part of the ship) or not.
	 * 
	 * @return miss : boolean that represents the status of the grid cell - was it hit (cell is not part of the ship) or not.
	 */
	public boolean isMiss()
	{
		return miss;
	}
	
	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Occupies the grid cell be a ship. Changes status of occupied to true. Changes the color of the grid
	 * if the ship doesn't belong to computer.
	 * 
	 * @param ai : boolean that represents if this ship belongs to computer
	 */
	public void occupy(boolean ai)
	{
		occupied = true;
		if(!ai) //if belongs to player
		{
			setFill(Color.LIGHTGREEN);
		}		
	}
	
	/**
	 * Changes status of hit to true. Changes the color of the grid
	 * to hit color.
	 */
	public void hitCell()
	{
		hit = true;
		setFill(Color.PALEVIOLETRED);
	}
	
	/**
	 * Changes status of sunk to true. Changes the color of the grid
	 * to sunk color.
	 */
	public void sunkCell()
	{
		sunk = true;
		setFill(Color.RED);
	}
	
	/**
	 * Changes status of miss to true. Changes the color of the grid
	 * to miss color.
	 */
	public void missCell()
	{
		miss = true;
		setFill(Color.BLUE);
	}

	
}
