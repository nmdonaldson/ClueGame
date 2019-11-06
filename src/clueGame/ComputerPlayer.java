package clueGame;

import java.awt.Color;
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
	
	public ComputerPlayer(String name, Color color, int row, int col) {
		super.setPlayerName(name);
		super.setColor(color);
		super.setRow(row);
		super.setColumn(col);
		super.setCards(new ArrayList<Card>());
		this.previousRoom = new BoardCell();
	}
	public ComputerPlayer(String name, String color, int row, int col) {
		super.setPlayerName(name);
		super.setColor(convertColor(color));
		super.setRow(row);
		super.setColumn(col);
		super.setCards(new ArrayList<Card>());
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
	
	public boolean makeAccusation(String person, String wep) {
		int row = getRow();
		int col = getColumn();
		String place = "Error";
		if(Board.getInstance().getCellAt(row, col).isRoom()) {
			char room = Board.getInstance().getCellAt(row, col).getInitial();
			place = Board.getInstance().getLegend().get(room); 
		}
		System.out.println("The suggestion made was " + person + " in the " + place + " with the " + wep);
		String real_person = Board.getInstance().getSolution().person;
		String real_place = Board.getInstance().getSolution().room;
		String real_weapon = Board.getInstance().getSolution().weapon;
		if(real_person != person || real_weapon != wep || real_place.equals(place) == false) {
			return false;
		}
		return true;
	}
	
	public Solution createSuggestion(String person_1, String wep_1) {
		Solution suggestion = new Solution();
		int row = getRow();
		int col = getColumn();
		String place = "Error";
		if(Board.getInstance().getCellAt(row, col).isRoom()) {
			char room = Board.getInstance().getCellAt(row, col).getInitial();
			place = Board.getInstance().getLegend().get(room); 
		}
		suggestion.person = person_1;
		suggestion.weapon = wep_1;
		suggestion.room = place;
		return suggestion;
	}
}
