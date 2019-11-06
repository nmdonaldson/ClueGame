package clueGame;

import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

// Child of the player class
public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, Color color, int row, int col) {
		super.setPlayerName(name);
		super.setColor(color);
		super.setRow(row);
		super.setColumn(col);
		super.setCards(new ArrayList<Card>());
	}
}
