package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Decks;
import clueGame.Player;
import clueGame.BoardCell;

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
		// Tests first player card from the file
		assertEquals(playerDeck.get(0).getCardName(), "Mrs. White");
		assertEquals(playerDeck.get(0).type, Card.CardType.PERSON);
		// Tests 4th player card from the file
		assertEquals(playerDeck.get(3).getCardName(), "Colonel Mustard");
		assertEquals(playerDeck.get(3).type, Card.CardType.PERSON);
		// Tests last player card from the file
		assertEquals(playerDeck.get(5).getCardName(), "Professor Plum");
		assertEquals(playerDeck.get(3).type, Card.CardType.PERSON);
	}
	
	@Test
	// Tests that the colors for each player are appropriate as per the file's info
	public void testPlayerColors() {
		ArrayList<Player> players = deck.getPlayers();
		
		// There should be 6 players
		assertEquals(6, players.size());
		// Tests first player's (Mrs. White) color
		assertEquals(players.get(0).getColor(), Color.WHITE);
		// Tests 4th player's (Colonel Mustard) color
		assertEquals(players.get(3).getColor(), Color.YELLOW);
		// Tests 6th player's (Professor Plum) color
		assertEquals(players.get(5).getColor(), Color.MAGENTA);
	}
	
	@Test
	// Tests the starting locations of each player
	public void testPlayerLocations() {
		ArrayList<Player> players = deck.getPlayers();
		
		// Mrs. Peacock, card #2, should be at 16, 0
		assertEquals(players.get(1).getRow(), 16);
		assertEquals(players.get(1).getColumn(), 0);
		// Miss Scarlet, 20, 8
		assertEquals(players.get(2).getRow(), 20);
		assertEquals(players.get(2).getColumn(), 8);
		// Mr Green, 6, 17
		assertEquals(players.get(4).getRow(), 6);
		assertEquals(players.get(4).getColumn(), 17);
	}
	
	@Test
	// Tests that the weapon cards are loaded correctly
	public void testLoadWeapons() {
		ArrayList<Card> weaponCards = deck.getWeaponDeck();
		
		// There are supposed to be 6 weapon cards
		assertEquals(weaponCards.size(), 6);
		// The first card is a knife
		assertEquals(weaponCards.get(0).getCardName(), "Knife");
		assertEquals(weaponCards.get(0).type, Card.CardType.WEAPON);
		// 3rd is a Pool Cue
		assertEquals(weaponCards.get(2).getCardName(), "Pool Cue");
		assertEquals(weaponCards.get(2).type, Card.CardType.WEAPON);
		// Last is a Candlestick
		assertEquals(weaponCards.get(5).getCardName(), "Candlestick");
		assertEquals(weaponCards.get(5).type, Card.CardType.WEAPON);
	}
	
	@Test
	// Tests that the room cards are loaded correctly
	public void testLoadRooms() {
		
	}
}
