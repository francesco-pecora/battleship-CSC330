package ships;

/**
 * Class that extends Ship abstract class and represents a specific type of a ship in battleship game:
 * Aircraft Carrier (grid size = 5). Implements abstract method getName().
 * 
 * 
 * @author 		Team 8
 * @version 	1.0
 */
public class AircraftCarrier extends Ship{
	
	//******************************CONSTRUCTORS******************************
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 5346387643092448332L;


	/**
	 * Default constructor.
	 * 
	 * Uses superclass constructor and passes the size of the aircraft carrier as a parameter.
	 */
	public AircraftCarrier()
	{		
		super(5);		
	}
	
	
	//******************************MUTATOR METHODS***********************************
	
	/**
	 * Returns name of the Aircraft Carrier
	 * 
	 * @return name : String that represents a name of the Aircraft Carrier
	 */
	public String getName()
	{
		return "Aircraft Carrier";
	}

}
