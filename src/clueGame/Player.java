package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> cards;
	
	public Player() {
	}
	
	public Player(String name, String color, int row, int col) {
		this.playerName = name;
		this.color = convertColor(color);
		this.row = row;
		this.column = col;
		this.cards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
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
	

	// Mutators
	public void addCard(Card card) {
		cards.add(card);
	}

	// Accessors
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
}
