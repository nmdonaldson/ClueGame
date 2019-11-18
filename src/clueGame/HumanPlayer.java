package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

// Child of the player class
public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, Color color, int row, int col, ArrayList<Card> cards) {
		super.setPlayerName(name);
		super.setColor(color);
		super.setRow(row);
		super.setColumn(col);
		super.setCards(cards);
	}
	
	// Allows the player to choose where to move to as long as it's within the target list
	@Override
	public void makeMove(Set<BoardCell> targs) {
	}
	
	// Highlights the targets on the board
	@Override
	public void drawTargets(Graphics g, Set<BoardCell> targs, int DIM_X, int DIM_Y) {
		for (BoardCell target: targs) {
			g.setColor(Color.GREEN);
			g.fillRect(target.getColumn()*DIM_X, target.getRow()*DIM_Y, DIM_X, DIM_Y);
		}
	}
}
