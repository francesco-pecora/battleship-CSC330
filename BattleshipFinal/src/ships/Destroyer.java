package ships;

/**
 * Class that extends Ship abstract class and represents a specific type of a ship in battleship game:
 * Destroyer (grid size = 2). Implements abstract method getName().
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */
public class Destroyer extends Ship{

	//******************************CONSTRUCTORS******************************
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1985365984770900291L;


	/**
	 * Default constructor.
	 * 
	 * Uses superclass constructor and passes the size of the destroyer as a parameter.
	 */	
	public Destroyer()
	{		
		super(2);			
	}
	
	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Returns name of the Destroyer
	 * 
	 * @return name : String that represents a name of the Destroyer
	 */
	public String getName()
	{
		return "Destroyer";
	}
}
