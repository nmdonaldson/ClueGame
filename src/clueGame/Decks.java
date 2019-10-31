package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;


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
			
			Player playerObj = new Player(arr[0], arr[1], 
					Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
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
	public void initializeRooms() throws FileNotFoundException {
		Scanner scanner;
		scanner = new Scanner(new File("ClueRooms.txt"));
		
		// Adds the weapon cards to the weapon card deck
		while (scanner.hasNextLine()) {
			String[] rooms = scanner.nextLine().split(", ");
			
			// Only adds the rooms as cards if they are specified 
			// as such in the file
			if (rooms[2].equals("Card")) {
				Card room = new Card();
				room.setCardName(rooms[1]);
				room.type = Card.CardType.ROOM;
				roomDeck.add(room);
			}
		}
		
		scanner.close();
	}
	
	// Shuffles a deck
	public void shuffle(ArrayList<Card> deck) {
		Collections.shuffle(deck);
	}
	
	// Deals cards to each player
	public void dealCards() {
		int j = 0;
		int i = playerDeck.size() - 1;
		// Loops through the decks of cards deals them out to each player
		while (!playerDeck.isEmpty()) {
			if (j >= players.size()) j = 0;
			players.get(j).addCard(playerDeck.get(i));
			playerDeck.remove(i);
			j++;
			i--;
		}
		
		j = 0;
		i = weaponDeck.size() - 1;
		while (!weaponDeck.isEmpty()) {
			if (j >= players.size()) j = 0;
			players.get(j).addCard(weaponDeck.get(i));
			weaponDeck.remove(i);
			j++;
			i--;
		}
		
		j = 0;
		i = roomDeck.size() - 1;
		while (!roomDeck.isEmpty()) {
			if (j >= players.size()) j = 0;
			players.get(j).addCard(roomDeck.get(i));
			roomDeck.remove(i);
			j++;
			i--;
		}
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
