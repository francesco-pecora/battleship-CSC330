package players;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class Leaderboard {
	
	private ArrayList<String[]> gamesHistory;
	
	private File file;
	private Scanner input;
	private FileWriter writer;
	
	
	/**
	 * Default constructor. Initializes the list holding the game history and the file location.
	 */
	public Leaderboard() {
		gamesHistory = new ArrayList<String[]>();
		file = new File("C:\\Users\\franc\\Desktop\\gitHubRepositories\\battleship-CSC330\\battleship\\src\\GamesHistory.txt");
	}
	
	
	/**
	 * Function that updates the text file pushing the new game information in the right position. The leaderboard
	 * is sorted first by turns, then by time.
	 * 
	 * @param name	->	String name of the player
	 * @param turns	->	int turns taken by the player to win the game
	 * @param seconds	->	int seconds taken by the player to win the game
	 */
	public void updateLeaderboard(String name, int turns, int seconds) {
		getList();
		insertNewGame(name, turns, seconds);
		uploadList();
	}
	
	
	/**
	 * Helper function that imports the list from the text file holding the leaderboard.
	 */
	private void getList() {
		String[] entry = new String[3];
		
		try {
			input = new Scanner(file);
			while(input.hasNextLine()) {
				entry = input.nextLine().split("\\s+");		// splitting the input entry on the space
				
				if (entry.length < 2) break;				// handling empty file (size = 1 is first line empty)
				
				entry[2] = timeToSeconds(entry[2]);			// converting the time format hr:mn:sec into seconds
				gamesHistory.add(entry);
			}
			input.close();
		}
		catch(Exception e) {
			System.out.println("[ERROR] Error opening the file...");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	/**
	 * Helper function that inserts the new game in the right position.
	 * 
	 * @param name	->	String name of the player
	 * @param turns	->	int turns taken by the player to win the game
	 * @param seconds	->	int seconds taken by the player to win the game
	 */
	private void insertNewGame(String name, int turns, int seconds) {
		
		String[] entry;
		int indexToInsert = 0;
		
		for(int i = 0; i < gamesHistory.size(); i++) {
			
			entry = gamesHistory.get(i);
			
			// # of turns is higher for new game, so we skip
			if(turns > Integer.parseInt(entry[1])) {
				indexToInsert++;
				continue;
			}
			// # of turns is less, we pick the index
			else if (turns < Integer.parseInt(entry[1])) {
				indexToInsert = i;
				break;
			}
			// finding the position if have the same # of turns
			else {
				for(int j = i; j < gamesHistory.size(); j++) {
					
					entry = gamesHistory.get(j);
					
					// handles game position at the end of the list (with last elements equal turns)
					// handles positioning game at end of group of games with same # of turns
					if (seconds > Integer.parseInt(entry[2]) && turns == Integer.parseInt(entry[1])) {
						indexToInsert++;
						continue;
					}
					// position new game based on least time to complete game
					else {
						indexToInsert = j;
						break;
					}
				}
				break;
			}
		}
		String[] newGame = {name, String.valueOf(turns), String.valueOf(seconds)};
		gamesHistory.add(indexToInsert, newGame);
	}
	
	
	/**
	 * Helper function that overwrites the text file with the new information.
	 */
	private void uploadList() {
		
		try {
			writer = new FileWriter(file);
			String stringToWrite;
			for(String[] list : gamesHistory) {
				
				// creating the string to be added to the file
				stringToWrite = "";
				stringToWrite += list[0];
				stringToWrite += " ";
				stringToWrite += list[1];
				stringToWrite += " ";
				stringToWrite += toTimeFormat(list[2]);
				stringToWrite += "\n";
				
				writer.write(stringToWrite);
			}
			writer.close();
		}
		catch (Exception e) {
			System.out.println("[ERROR] Error writing to the file...");
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	
	/**
	 * Helper function that converts the time from hours:minutes:seconds to one single value
	 * representing the seconds.
	 * 
	 * @param time	->	String containing the time in the original format
	 * @return String original time converted in seconds
	 */
	public String timeToSeconds(String time) {
		
		String[] hms = time.split(":");		// string list containing hours minutes and seconds (hms)
		
		int totalSeconds = 0;
		
		int hours = Integer.parseInt(hms[0]);
		int minutes = Integer.parseInt(hms[1]);
		int seconds = Integer.parseInt(hms[2]);
		
		totalSeconds += hours * 3600;
		totalSeconds += minutes * 60;
		totalSeconds += seconds;
		
		return String.valueOf(totalSeconds);
	}
	
	
	/**
	 * Helper function that converts the time in seconds to the format hours:minutes:seconds.
	 * 
	 * @param seconds	->	String the time in seconds
	 * @return String the time in the format desired
	 */
	public String toTimeFormat(String seconds) {
		
		String result = "";
		
		int number = Integer.parseInt(seconds);
		
		int hrs = number / 3600;
		int min = (number % 3600) / 60;
		int sec = number % 60;
		
		result += String.valueOf(hrs);
		result += ":";
		result += String.valueOf(min);
		result += ":";
		result += String.valueOf(sec);
		
		return result;
	}
}
