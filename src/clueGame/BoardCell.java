package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import clueGame.DoorDirection;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class BoardCell extends JPanel {
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
	
	// Each cell will be 30 x 30 pixels
	public void draw(Graphics g, Map<Character, String> legend, int DIM_X, int DIM_Y) {
		// Chooses what to draw based on the attributes of the tile
		// If walkway, draw a yellow tile with a black border
		if (this.isWalkway()) {
			g.setColor(Color.YELLOW);
			g.fillRect(DIM_X*column, DIM_Y*row, DIM_X, DIM_Y);
			g.setColor(Color.BLACK);
			g.drawRect(DIM_X*column, DIM_Y*row, DIM_X, DIM_Y);
		}
		// If this is a closet cell, draw a red tile
		else if (this.initial == 'X') {
			g.setColor(Color.RED);
			g.fillRect(DIM_X*column, DIM_Y*row, DIM_X, DIM_Y);
		}
		// If this tile is a doorway, draw the tile gray like a room tile
		// Then draw a small blue rectangle on the border of the door's entrance
		else if (this.isDoorway()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(DIM_X*column, DIM_Y*row, DIM_X, DIM_Y);
			switch (this.getDoorDirection()) {
				case UP:
					g.setColor(Color.BLUE);
					g.fillRect(DIM_X*column, DIM_Y*row, DIM_X, DIM_Y/10);
					break;
				case DOWN:
					g.setColor(Color.BLUE);
					g.fillRect(DIM_X*column, DIM_Y*row+DIM_Y-3, DIM_X, DIM_Y/10);
					break;
				case LEFT:
					g.setColor(Color.BLUE);
					g.fillRect(DIM_X*column, DIM_Y*row, DIM_X/10, DIM_Y);
					break;
				case RIGHT:
					g.setColor(Color.BLUE);
					g.fillRect(DIM_X*column+DIM_X-3, DIM_Y*row, DIM_X/10, DIM_Y);
					break;
				default:
					System.out.println("Error Displaying Door!");
					break;
			}
		}
		// If this cell is a room, draw a gray tile. 
		// If the tile wants a name to be drawn, draw the name of the room according to the legend.
		else if (this.isRoom()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(DIM_X*column, DIM_Y*row, DIM_X, DIM_Y);
			if (door == 'N') {
				g.setColor(Color.BLACK);
				g.drawString(legend.get(initial), DIM_X*column, DIM_Y*row);
			}
		}
	}
	
	// Checks if the initial of the cell is a walkway or not
	public boolean isWalkway() {
		return (this.initial == 'W');
	}
	
	// Checks if the initial is a room initial or not
	public boolean isRoom() {
		return (!isWalkway() && !isDoorway());
	}
	
	// Checks if cell is a doorway
	public boolean isDoorway() {
		return (this.door == 'D' || this.door == 'U' 
				|| this.door == 'R' || this.door == 'L');
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
