package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Decks;
import clueGame.Player;
import clueGame.Solution;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class gameActionTests {
	private static Board board;
	private static Decks deck;
	private static ComputerPlayer compPlayer;
	

	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("Board.csv", "ClueRooms.txt");
		board.initialize();
		deck = board.getDecks();
		compPlayer = new ComputerPlayer("abc", "BLUE", 1, 1);	
	}
	
	@Test
	//testing the solution to verify it is unique
	public void solutionTest() {
		String theGuilty = board.getSolution().person;
		String thePlace = board.getSolution().room;
		String theMethod = board.getSolution().weapon;
		boolean result1, result2, result3;
		result1 = false;
		result2 = false;
		result3 = false;
		//makes sure they are each in the respective deck
		for(int i = 0; i < deck.getPlayerDeck().size(); i++ ) {
			if(deck.getPlayerDeck().get(i).getCardName() == theGuilty) {
				result1 = true;
			}
			if(deck.getRoomDeck().get(i).getCardName() == thePlace) {
				result2 = true;
			}
			if(deck.getWeaponDeck().get(i).getCardName() == theMethod) {
				result3 = true;
			}
		}	
		//if all of the fields are within the deck it passes
		assertTrue(result1 && result2 && result3);
	}
	
	@Test
	public void createSuggestionTest() {
		//makes a new solution and gives it specific values 
		//passing it into the create suggestion to verify it is working properly
		Solution tester = new Solution();
		tester.person = "test";
		tester.weapon = "pipe";
		tester.room = "Conservatory";
		Solution my_solution = compPlayer.createSuggestion("test", "pipe");
		
		//checks each field if it is correct value
		assertTrue(my_solution.room.equals(tester.room));
		assertTrue(my_solution.person.equals(tester.person));
		assertTrue(my_solution.weapon.equals(tester.weapon));
	}
	
	@Test
	public void testAccusation() {
		//checks a given accusation as long as the room is the conservatory
		//System.out.println("Room is " + Board.getInstance().getSolution().room);
		if(Board.getInstance().getSolution().room.equals("Conservatory")) {
			String person = Board.getInstance().getSolution().person;
			String weapon = Board.getInstance().getSolution().weapon;
			assertTrue(compPlayer.makeAccusation(person, weapon));	
		}
	}
	
	@Test
	// Tests computer player behavior
	public void testTargets() {
		// Calculates the targets at 14, 17, and 3
		board.calcTargets(14, 17, 2);
		compPlayer.setColumn(17);
		compPlayer.setRow(14);
		
		// If there are no rooms in the list, select randomly
		assertTrue(board.getTargets().contains(compPlayer.pickLocation(board.getTargets())));
		
		// If there is a room within range, select that room target
		board.calcTargets(14, 17, 3);
		assertEquals(compPlayer.pickLocation(board.getTargets()), board.getCellAt(15, 17));
		
		// If there is a room within range, but was there the previous turn, choose a random cell
		assertTrue(board.getTargets().contains(compPlayer.pickLocation(board.getTargets())));
		
		// Resets computer player back to original spot
		compPlayer.setColumn(1);
		compPlayer.setRow(1);
	}
	
	@Test
	// Checks suggestion disproving
	public void disproveSuggestion() {
		// Creates new solution
		Solution suggestion = new Solution();
		suggestion.person = "Mr. Plum";
		suggestion.weapon = "Pipe";
		suggestion.room = "Conservatory";
		

		Card card1 = new Card();
		card1.setCardName("Armory");
		compPlayer.addCard(card1);
		Card temp = compPlayer.disproveSuggestion(suggestion);
		
		// Tests that there are no matching cards
		assertEquals(null, temp);
		
		Card card = new Card();
		card.setCardName("Mr. Plum");
		compPlayer.addCard(card);
		temp = compPlayer.disproveSuggestion(suggestion);
		// Tests that the card chosen matches
		assertTrue(temp.getCardName() == "Mr. Plum" || temp.getCardName() == "Pipe" 
				|| temp.getCardName() == "Conservatory");
		
		Card card2 = new Card();
		card2.setCardName("Pipe");
		compPlayer.addCard(card2);
		temp = compPlayer.disproveSuggestion(suggestion);
		
		// Randomly chooses between the valid cards
		if (temp.getCardName() == "Mr. Plum") {
			assertTrue(true);
		}
		else {
			assertTrue(temp.getCardName() == "Pipe");
		}
	}
	
	@Test
	// Tests handling of a suggestion across all players
	public void handleSuggestion() {
		Solution suggestion = new Solution();
		suggestion.person = "Quote";
		suggestion.room = "Egg Corridor";
		suggestion.weapon = "Polar Star";
		// Players' answer to a suggestion
		Card answer = board.handleSuggestion(suggestion);
		ArrayList<Player> players = board.getMy_players();
		
		// Answer doesn't exist, so it returns nothing
		assertEquals(null, answer);
		
		Card temp = new Card();
		temp.setCardName("H");
		players.get(1).addCard(temp);
		// Suggestion becomes disprovable to the player (not human)
		suggestion.person = players.get(1).getCards().get(0).getCardName();
		// If accusing player is the only one that can disprove
		assertEquals(null, board.handleSuggestion(suggestion));
		
		
	}
}
