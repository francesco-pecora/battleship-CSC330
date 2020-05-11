
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ComputerBoard extends Board{
	
	Board enemy;
	
 	
	public ComputerBoard(boolean playerBoard, Board e)
	{
		super(playerBoard);	
		enemy = e;
	}
	
	
	public void onClickPlaceShip(GridCell gc, 
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
        					attackEnemy();
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
    					attackEnemy();
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
	
 	
}
 	
