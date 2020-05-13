package ships;

/**
 * Class that extends Ship abstract class and represents a specific type of a ship in battleship game:
 * Submarine (grid size = 3). Implements abstract method getName().
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */
public class Submarine extends Ship{

	//******************************CONSTRUCTORS******************************
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9362167289065164L;


	/**
	 * Default constructor.
	 * 
	 * Uses superclass constructor and passes the size of the submarine as a parameter.
	 */		
	public Submarine()
	{		
		super(3);			
	}
	
	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Returns name of the Submarine
	 * 
	 * @return name : String that represents a name of the Submarine
	 */
	public String getName()
	{
		return "Submarine";
	}
}
