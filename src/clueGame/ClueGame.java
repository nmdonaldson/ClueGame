package clueGame;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class ClueGame extends JFrame {
	public Board board;
	
	// Constructor, initializes GUI elements and the Board
	public ClueGame() {
		setSize(867, 875);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = Board.getInstance();
		board.setConfigFiles("Board.csv", "ClueRooms.txt");
		board.initialize();
		ClueGUI gui = new ClueGUI();
		ClueCardsGUI cardGUI = new ClueCardsGUI();
		
		// Calls paintComponent automatically
		add(board, BorderLayout.CENTER);
		add(cardGUI, BorderLayout.EAST);
		add(gui, BorderLayout.AFTER_LAST_LINE);
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setTitle("Clue");
		game.setVisible(true);
	}
} 