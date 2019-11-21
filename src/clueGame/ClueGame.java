package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class ClueGame extends JFrame {
	public Board board;
	public boolean dispNotes;
	DetectiveNotesGUI notesGUI = new DetectiveNotesGUI(this);

	// Constructor, initializes GUI elements and the Board
	public ClueGame() {
		setSize(867, 957);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = Board.getInstance();
		board.setConfigFiles("Board.csv", "ClueRooms.txt");
		board.initialize();
		ClueGUI gui = new ClueGUI();
		gui.add(checkBox());

		// Gets the human player object (for their cards)
		ClueCardsGUI cardGUI = new ClueCardsGUI(board.getMy_players().get(0));
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
		JOptionPane.showMessageDialog(window,
				"You are " + players.get(0).getName() + ", press Next Player to begin play", "Welcome to Clue",
				JOptionPane.INFORMATION_MESSAGE);
		splash.setVisible(true);
	}

	private JPanel checkBox() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		JCheckBox box = new JCheckBox("Display Dective Notes");
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox tempBox = (JCheckBox) e.getSource();
				if (tempBox.isSelected()) {
					notesGUI.setVisible(true);
				} else {
					setSize(867, 875);
					repaint();
					notesGUI.setVisible(false);
				}
			}
		});
		panel.add(box, BorderLayout.WEST);
		return panel;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setTitle("Clue");
		game.setVisible(true);
	}
}