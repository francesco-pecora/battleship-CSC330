package players;

import boards.*;

public class ComputerPlayer implements Player {
	
	private String name;
	private ComputerBoard board;	
	
	
	//***************************************CONSTRUCTORS**********************************************************************
	
	public ComputerPlayer(HumanPlayer h) {
		name = "Computer";
		board = new ComputerBoard(h.getPlayerBoard());
	}
	
	@Override
	public void updateLeaderboard() {}
	
	
	//***************************************************ACCESSOR METHODS*******************************************************
	
	public String getName() {
		return name;
	}
	
	public ComputerBoard getComputerBoard() {
		return board;
	}
}

