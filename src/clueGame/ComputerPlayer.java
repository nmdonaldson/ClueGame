package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class ComputerPlayer extends Player {
	private BoardCell previousRoom;
	
	public ComputerPlayer(String name, Color color, int row, int col, ArrayList<Card> cards) {
		super.setPlayerName(name);
		super.setColor(color);
		super.setRow(row);
		super.setColumn(col);
		super.setCards(cards);
		this.previousRoom = new BoardCell();
	}
	public ComputerPlayer(String name, String color, int row, int col, ArrayList<Card> cards) {
		super.setPlayerName(name);
		super.setColor(convertColor(color));
		super.setRow(row);
		super.setColumn(col);
		super.setCards(cards);
		this.previousRoom = new BoardCell();
	}
	
	// Chooses location to move to within targets from the board
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// Checks the targets array for rooms
		for (BoardCell target: targets) {
			// If there is a room in the targets list and it isn't the previously visited room, choose it
			if (target.isDoorway() && target != previousRoom) {
				previousRoom = target;
				return target;
			}
		}
		
		// Otherwise, choose the target randomly (regardless of its tile type)
		Random rand = new Random();
		int random = rand.nextInt(targets.size());
		int i = 0;
		for (BoardCell target: targets) {
			if (i == random) return target;
			i++;
		}
		return null;
	}
	
	// Makes an accusation. Is a game winning/losing move
	public boolean makeAccusation(String person, String wep) {
		int row = getRow();
		int col = getColumn();
		String place = "Error";
		// Gets an instance of the board
		if(Board.getInstance().getCellAt(row, col).isRoom()) {
			char room = Board.getInstance().getCellAt(row, col).getInitial();
			place = Board.getInstance().getLegend().get(room); 
		}
		// Checks the accusation being made vs. the solution
		System.out.println("The suggestion made was " + person + " in the " + place + " with the " + wep);
		String real_person = Board.getInstance().getSolution().person;
		String real_place = Board.getInstance().getSolution().room;
		String real_weapon = Board.getInstance().getSolution().weapon;
		// Returns true if the accusation == solution, false otherwise
		return (real_person != person || real_weapon != wep || real_place.equals(place));
	}
	
	// Makes a suggestion. Rules out possibilities
	public Solution createSuggestion(String person_1, String wep_1) {
		Solution suggestion = new Solution();
		int row = getRow();
		int col = getColumn();
		String place = "Error";
		// Gets an instance of the board
		if(Board.getInstance().getCellAt(row, col).isRoom()) {
			char room = Board.getInstance().getCellAt(row, col).getInitial();
			place = Board.getInstance().getLegend().get(room); 
		}
		// Suggestion takes place in the room the player is currently in
		suggestion.person = person_1;
		suggestion.weapon = wep_1;
		suggestion.room = place;
		return suggestion;
	}
	
	// Picks a target to move to and moves to it, also handles suggestions and accusations
	@Override
	public void makeMove(Set<BoardCell> targs) {
		BoardCell pick = pickLocation(targs);
		super.setRow(pick.getRow());
		super.setColumn(pick.getColumn());
		// TODO: For next lab, handle suggestions and accusations here
	}
}
