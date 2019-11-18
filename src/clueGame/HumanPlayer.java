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
	private final static int DIM_X = 30;
	private final static int DIM_Y = 30;
	
	public HumanPlayer(String name, Color color, int row, int col, ArrayList<Card> cards) {
		super.setPlayerName(name);
		super.setColor(color);
		super.setRow(row);
		super.setColumn(col);
		super.setCards(cards);
	}
	
	// Allows the player to choose where to move to
	@Override
	public void makeMove(Set<BoardCell> targs) {
		
	}
	
	// Highlights the targets on the board
	@Override
	public void drawTargets(Graphics g, Set<BoardCell> targs) {
		for (BoardCell target: targs) {
			g.setColor(Color.GREEN);
			g.fillRect(target.getColumn()*DIM_X, target.getRow()*DIM_Y, DIM_X, DIM_Y);
		}
	}
}
