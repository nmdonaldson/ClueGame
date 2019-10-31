package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.Decks;

public class gameSetupTests {
	private static Decks deck;
	
	@BeforeClass
	public static void setup() {
		deck = Decks.getInstance();
		deck.initialize();
	}
	
	@Test
	// Tests the ability for the deck object to load each player card correctly
	public void testLoadPlayerCards() {
		ArrayList<Card> playerDeck = deck.getPlayerDeck();
		// The deck of player cards should have 6 cards
		assertEquals(6, playerDeck.size());
		
		// Tests first player card
		assertTrue();
	}
	
	@Test
	public void testLoadWeapons() {
		
	}
	@Test
	public void testLoadRooms() {
		
	}
}
