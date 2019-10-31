package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class Decks {
	public ArrayList<Card> weaponDeck;
	public ArrayList<Card> playerDeck;
	public ArrayList<Card> roomDeck;
	public ArrayList<Player> players;
	private static Decks instance = new Decks();
	
	
	// Constructor
	private Decks() {
		this.playerDeck = new ArrayList<Card>();
		this.weaponDeck = new ArrayList<Card>();
		this.roomDeck = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
	}
	
	// Loads all of the information needed to load the decks
	public void initialize() {
		try {
		    initializePlayers();
		    initializeWeapons();
		    initializeRooms();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Reads player information from a text file
	public void initializePlayers() throws FileNotFoundException {
		Scanner scanner;
		scanner = new Scanner(new File("CluePlayers.txt"));

		// Adds the player information to the deck of player cards and the arrayList
		while (scanner.hasNextLine()) {
			String arr[] = scanner.nextLine().split(", ");
			
			Card playerCard = new Card();
			playerCard.type = Card.CardType.PERSON;
			playerCard.setCardName(arr[0]);
			playerDeck.add(playerCard);
			
			Player playerObj = new Player(arr[0], arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
			players.add(playerObj);
		}
		
		scanner.close();
	}
	
	// Reads weapon information from a text file
	public void initializeWeapons() throws FileNotFoundException {
		Scanner scanner;
		scanner = new Scanner(new File("ClueWeapons.txt"));
		
		// Adds the weapon cards to the weapon card deck
		while (scanner.hasNextLine()) {
			String name = scanner.nextLine();
			
			Card weapon = new Card();
			weapon.setCardName(name);
			weapon.type = Card.CardType.WEAPON;
			weaponDeck.add(weapon);
		}
		
		scanner.close();
	}
	
	// Reads room information from the legend file
	public void initializeRooms() {
		
	}
	
	// Shuffles a deck
	public void shuffle() {
		
	}
	
	// Accessors
	public ArrayList<Card> getWeaponDeck() {
		return weaponDeck;
	}

	public ArrayList<Card> getPlayerDeck() {
		return playerDeck;
	}

	public ArrayList<Card> getRoomDeck() {
		return roomDeck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	// Ensures that there is only one deck at a time along with global access
	public static Decks getInstance() { return instance; }
}