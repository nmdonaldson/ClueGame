package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
		// Gets the human player object (for their cards)
		ClueCardsGUI cardGUI = new ClueCardsGUI(board.getMy_players().get(0));
		DetectiveNotesGUI notesGUI = new DetectiveNotesGUI(this);
		notesGUI.setVisible(true);
		displaySplashWindow(board.getMy_players());
		
		
		// Calls paintComponent automatically
		add(board, BorderLayout.CENTER);
		add(cardGUI, BorderLayout.EAST);
		add(gui, BorderLayout.AFTER_LAST_LINE);
	}
	
	// Displays the splash window at the start of the game
	public void displaySplashWindow(ArrayList<Player> players) {
		JFrame window = new JFrame();
		window.setPreferredSize(new Dimension(200, 500));
		JOptionPane splash = new JOptionPane();
		// Displays, "You are [PLAYERNAME], press Next Player to begin play"
		JOptionPane.showMessageDialog(window, "You are " + players.get(0).getName() + 
				", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		splash.setVisible(true);
	}

	
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setTitle("Clue");
		game.setVisible(true);
	}
} 