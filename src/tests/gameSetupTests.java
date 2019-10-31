package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.Decks;
import clueGame.Player;

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
		ArrayList<Card> roomCards = deck.getRoomDeck();
		System.out.println("diarrhea");
		// 9 rooms
		assertEquals(roomCards.size(), 9);
		// The first room is the Conservatory
		assertEquals(roomCards.get(0).getCardName(), "Conservatory");
		assertEquals(roomCards.get(0).type, Card.CardType.ROOM);
		// 4th card, Foyer
		assertEquals(roomCards.get(3).getCardName(), "Foyer");
		assertEquals(roomCards.get(3).type, Card.CardType.ROOM);
		// 5th card, Bathroom
		assertEquals(roomCards.get(4).getCardName(), "Bathroom");
		assertEquals(roomCards.get(4).type, Card.CardType.ROOM);
		// Last card, Kitchen
		assertEquals(roomCards.get(8).getCardName(), "Kitchen");
		assertEquals(roomCards.get(8).type, Card.CardType.ROOM);
	}
	
	@Test
	// Tests that the cards are being dealt fairly 
	public void AtestDealing() {
		deck.dealCards();
		System.out.println("poop");
		ArrayList<Card> roomCards = deck.getRoomDeck();
		ArrayList<Card> weaponCards = deck.getWeaponDeck();
		ArrayList<Card> playerDeck = deck.getPlayerDeck();
		ArrayList<Player> players = deck.getPlayers();
		
		// Checks that the cards in the deck have all been dealt
		assertEquals(roomCards.size(), 0);
		assertEquals(weaponCards.size(), 0);
		assertEquals(playerDeck.size(), 0);
		
		// Checks that the players have roughly equal amount of cards
		ArrayList<Card> cards = players.get(0).getCards();
		ArrayList<Card> cards2 = players.get(3).getCards();
		
		// Using 2 as margin of error
		assert(cards.size() - cards2.size() >= 2 || cards.size() - cards2.size() >= -2);
		
		// Tests to ensure cards only appear once
		for (int i = 0; i < cards2.size(); i++) {
			assertFalse(cards.contains(cards2.get(i)));
		}
	}
}
