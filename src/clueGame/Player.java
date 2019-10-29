package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	public Player() {
	}
	public Player(String name, String color) {
		this.playerName = name;
		this.color = convertColor(color);
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
}
