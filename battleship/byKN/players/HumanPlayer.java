package players;

import boards.*;

public class HumanPlayer implements Player {
	
	private String name;				// name input from the player
	private int turns;					// number of turns the player took to win/lose the game
	private int seconds;				// time taken by the player to win/lose the game
	private PlayerBoard board;			// instance of the player board to retrieve data
	
	private Leaderboard leaderboard;	// instance of leaderboard processing and updating the history of games
	
	
	//***************************************CONSTRUCTORS**********************************************************************
	
	// default constructor give the player a default name
	public HumanPlayer() {
		name = "Player";
		leaderboard = new Leaderboard();
		board = new PlayerBoard();
	}
	
	// constructor setting the name given by the player
	public HumanPlayer(String n) {
		name = n;
		leaderboard = new Leaderboard();
		board = new PlayerBoard();
	}
	
	//***************************************************ACCESSOR METHODS*******************************************************
	
	@Override
	public void updateLeaderboard() {
		leaderboard.updateLeaderboard(name, turns, seconds);
	}
	
	public int getTurns() {
		return turns;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public PlayerBoard getPlayerBoard() {
		return board;
	}
	
	//***************************************************MUTATOR METHODS*******************************************************

	public void setName(String n) {
		name = n;
	}
	
	public void setTurns() {
		turns = board.getRoundCounter();
	}
	
	public void setTurns(int t) {
		turns = t;
	}
	
	public void setSeconds(int s) {
		seconds = s;
	}
}
