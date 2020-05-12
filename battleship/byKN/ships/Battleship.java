package ships;

/**
 * Class that extends Ship abstract class and represents a specific type of a ship in battleship game:
 * Battleship (grid size = 4). Implements abstract method getName().
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */
public class Battleship extends Ship{

	//******************************CONSTRUCTORS******************************
	
	/**
	 * Default constructor.
	 * 
	 * Uses superclass constructor and passes the size of the battleship as a parameter.
	 */
	public Battleship()
	{		
		super(4);		
	}
	
	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Returns name of the Battleship
	 * 
	 * @return name : String that represents a name of the Battleship
	 */
	public String getName()
	{
		return "Battleship";
	}
}
