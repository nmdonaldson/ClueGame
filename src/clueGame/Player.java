package clueGame;

import java.awt.Color;
import java.util.Random;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> cards;
	
	// Default constructor; doesn't do anything
	public Player() {
	}
	
	// Constructor
	public Player(String name, String color, int row, int col) {
		this.playerName = name;
		this.color = convertColor(color);
		this.row = row;
		this.column = col;
		this.cards = new ArrayList<Card>();
	}

	// Checks if the suggestion being made can be disproved
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> return_cards = new ArrayList<Card>();
		Random rand = new Random();
		// Searches the deck of the player for a matching card
		for(int i = 0; i < cards.size(); i++) {
			if(suggestion.person == cards.get(i).getCardName() || 
					suggestion.room == cards.get(i).getCardName() || 
					suggestion.weapon == cards.get(i).getCardName()) {
				return_cards.add(cards.get(i));
			}
		}
		// If more than one match exists, choose them at random
		if(return_cards.size() == 0) {return null;}
		return return_cards.get(rand.nextInt(return_cards.size()));
	}
	
	// Converts a string to a color object, from the canvas material
	public Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} 
		catch (Exception e) {
			color = null;
		}
		return color;
	}

	// Accessors
	public String getName() {
		return playerName;
	}
	public int getRow() {
		return row;
	}
	public Color getColor() {
		return color;
	}
	public int getColumn() {
		return column;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	
	// Mutators
	public void addCard(Card card) {
		cards.add(card);
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
