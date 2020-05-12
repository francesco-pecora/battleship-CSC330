package ships;

/**
 * Class that extends Ship abstract class and represents a specific type of a ship in battleship game:
 * Cruiser (grid size = 3). Implements abstract method getName().
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */

public class Cruiser extends Ship{
	
	//******************************CONSTRUCTORS******************************
	
	/**
	 * Default constructor.
	 * 
	 * Uses superclass constructor and passes the size of the cruiser as a parameter.
	 */	
	public Cruiser()
	{
		super(3);		
	}

	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Returns name of the Cruiser
	 * 
	 * @return name : String that represents a name of the Cruiser
	 */
	public String getName()
	{
		return "Cruiser";
	}
}
