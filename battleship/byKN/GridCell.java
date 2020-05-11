import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridCell extends Rectangle{
	private int xCoordinate;
	private int yCoordinate;
	private boolean occupied;
	private boolean hit;
	private boolean miss;
	private boolean sunk;
	
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
	
	public void occupy(boolean ai)
	{
		occupied = true;
		if(!ai)
		{
			setFill(Color.LIGHTGREEN);
		}
		
	}
	
	public void hitCell()
	{
		hit = true;
		setFill(Color.PALEVIOLETRED);
	}
	
	public void sunkCell()
	{
		sunk = true;
		setFill(Color.RED);
	}
	
	public void missCell()
	{
		miss = true;
		setFill(Color.BLUE);
	}
	
	public boolean isHit()
	{
		return hit;
	}
	
	public boolean isSunk()
	{
		return sunk;
	}
	
	public boolean isMiss()
	{
		return miss;
	}
	
}
