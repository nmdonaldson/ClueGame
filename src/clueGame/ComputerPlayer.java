package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import clueGame.Card.CardType;

import java.util.Random;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class ComputerPlayer extends Player {
	private BoardCell previousRoom;
	private Solution winCondition;
	
	public ComputerPlayer(String name, Color color, int row, int col, ArrayList<Card> cards) {
		super.setPlayerName(name);
		super.setColor(color);
		super.setRow(row);
		super.setColumn(col);
		super.setCards(cards);
		this.winCondition = new Solution();
		this.previousRoom = new BoardCell();
	}
	public ComputerPlayer(String name, String color, int row, int col, ArrayList<Card> cards) {
		super.setPlayerName(name);
		super.setColor(convertColor(color));
		super.setRow(row);
		super.setColumn(col);
		super.setCards(cards);
		this.winCondition = new Solution();
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
		//System.out.println("The suggestion made was " + person + " in the " + place + " with the " + wep);
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
		if(Board.getInstance().getCellAt(row, col).isRoom() 
				|| Board.getInstance().getCellAt(row, col).isDoorway()) {
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
	public Card makeMove(Set<BoardCell> targs) {
		// If the previous suggestion couldn't be disproven, make this accusation
		if (winCondition.person != null && winCondition.room != null && winCondition.weapon != null) {
			// If the accusation is true, then the player wins
			if (makeAccusation(winCondition.person, winCondition.weapon)) {
				JFrame window = new JFrame();
				window.setPreferredSize(new Dimension(200, 525));
				JOptionPane splash = new JOptionPane();
				JOptionPane.showMessageDialog(window, super.getName() + " just won! The perpetrator was "
						+ winCondition.person + " using the " 
						+ winCondition.weapon + " in the "
						+ winCondition.room + ".", 
						"Game Over", JOptionPane.INFORMATION_MESSAGE);
				splash.setVisible(false);
				// Ends the program
				System.exit(0);
			}
		}
		
		BoardCell pick = pickLocation(targs);
		super.setRow(pick.getRow());
		super.setColumn(pick.getColumn());
		
		// If the computer player moves into a room, they'll make a suggestion
		if (pick.isRoom() || pick.isDoorway()) {
			Random rand = new Random();
			int randomPlayer = rand.nextInt(Board.getInstance().getMy_players().size());
			
			// Choose a random weapon from the cards people are holding
			ArrayList<Card> temp = new ArrayList<Card>();
			while (true) {
				int randomWeapon = rand.nextInt(Board.getInstance().getMy_players().size());
				temp = Board.getInstance().getMy_players().get(randomWeapon).getCards();
				// Once they find a valid card setup, they make a suggestion which is then disproven
				for (Card card : temp) {
					if (card.type == Card.CardType.WEAPON) {
						Solution guess = createSuggestion(
								Board.getInstance().getMy_players().get(randomPlayer).getName(),
								card.getCardName());
						Board.getInstance().setCurrentGuess(guess);
						Card disproven = Board.getInstance().handleSuggestion(guess, this);
						// If the guess can't be disproven, make that accusation next turn
						if (disproven == null) winCondition = guess;
						return disproven;
					}
				}
			}
		}
		return null;
	}
}
