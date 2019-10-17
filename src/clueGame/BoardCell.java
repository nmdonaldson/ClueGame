package clueGame;

import clueGame.DoorDirection;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private char door;
	
	public BoardCell(int r, int c, char i, char d){
		this.row = r;
		this.column = c;
		this.initial = i;
		this.door = d;
	}
	public BoardCell(){}
	
	// Checks if the initial of the cell is a walkway or not
	public boolean isWalkway() {
		return (initial == 'W');
	}
	
	// Checks if the initial is a room initial or not
	public boolean isRoom() {
		return (initial != 'W' && initial != 'X' && !isDoorway());
	}
	
	// Checks if cell is a doorway
	public boolean isDoorway() {
		return (door != ' ' && door == 'D' || door == 'U' || door == 'R' || door == 'L');
	}
	
	// Accessors/Mutators
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public char getInitial() {
		return this.initial;
	}
	
	public void setInitial(char i){
		this.initial = i;
	}
	
	// Returns the direction the door is facing
	public DoorDirection getDoorDirection() {
		if (door == 'D') return DoorDirection.DOWN;
		if (door == 'R') return DoorDirection.RIGHT;
		if (door == 'U') return DoorDirection.UP;
		if (door == 'L') return DoorDirection.LEFT;
		else return null;
	}
}
