import java.util.ArrayList;
import javafx.scene.paint.Color;

public abstract class Ship {
	
	private int numOfGrids;
	boolean sunk;
	boolean placed;
	String name;
	//TODO
	//Coordinated (Grid Cells)
	ArrayList<GridCell> shipCells;
	
	public Ship()
	{
		numOfGrids = 0;
		sunk = false;
		placed = false;
		name = "Ship";		
		//TODO
		//Coordinated (Grid Cells)
		//GridCell[] coordinates = new GridCell[ng];		
	}
	
	public Ship(int ng, String n)
	{
		numOfGrids = ng;
		sunk = false;
		placed = false;
		name = n;		
		//TODO
		//Coordinated (Grid Cells)
		//GridCell[] coordinates = new GridCell[ng];		
	}
	
	public boolean isSunk()
	{
		if(sunk == true)
		{
			return sunk;
		}
		else
		{
			int sunkenIndex = 0;
			for(GridCell cell : shipCells)
			{
				//check if the cell was hit
				if(cell.isHit()) {
					sunkenIndex++;
				}
			}
			if(sunkenIndex == numOfGrids) //ship is sunken
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
	
	public void placeShip(ArrayList<GridCell> shipGrid)
	{
		//TODO 
		//Receive data from main game GUI on where the ship was placed
		//store pointers for each grid cell into the GridCell[] coordinates
		//it could be done by passing an existing GridCell array 
		//that was created in the main game class into GridCell[] coordinates		
		shipCells = shipGrid;
		placed = true;
	}
	
	public boolean isPlaced()
	{
		return placed;
	}
	
	public int getSize()
	{
		return numOfGrids;
	}
	
	public String getName()
	{
		return name;
	}
	
	
}
