package clueGame;



/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.util.Map.Entry;

public class ClueGUI extends JPanel {
	private JButton nextPlayer;
	private JButton makeAccusation;
	private Board board = Board.getInstance();
	private int playerCounter = 0;
	private int messageSuppressor = 0;
	private boolean stillPlayerTurn = false;
	private boolean playerTurnOver;
	private static ClueGUI instance = new ClueGUI();
	private JButton sub;
	private JButton cancel;
	private boolean dialogOpen = false; // prevents message refuting/affirming accusation from appearing twice
	
	public static ClueGUI getInstance() {
		return instance;
	}
	
	// Constructor. Adds each panel to the window 
	private ClueGUI() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(3,3));
		add(displayTurn());
		add(playerMove());
		add(makeAccusation());
		add(rollDie());
		add(displayGuess());
		add(displayGuessResult());
		this.sub = new JButton("Submit");
		this.cancel = new JButton("Cancel");
	}

	// Creates die roll panel
	private JPanel rollDie(){
		JPanel panel = new JPanel();
	 	// Created at row 1 column 2
		panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Roll");
		JTextField dieRoll = new JTextField();
		dieRoll.setText(Integer.toString(board.getDieRoll()));
		dieRoll.setEditable(false);
		panel.add(nameLabel);
		panel.add(dieRoll);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}
	
	// Creates player movement button and allows it to handle player actions
	private JPanel playerMove() {
		// No layout, created using default flow
		nextPlayer = new JButton("Next Player");
		nextPlayer.setPreferredSize(new Dimension(280, 75));
		ButtonListener listener = new ButtonListener();
		nextPlayer.addActionListener(listener);
		JPanel panel = new JPanel();
		panel.add(nextPlayer);
		return panel;
	}

	// Creates accusation button and allows it to handle accusations
	private JPanel makeAccusation() {
		// No layout, So it's created with the default flow
		makeAccusation = new JButton("Make an Accusation");
		makeAccusation.setPreferredSize(new Dimension(280, 75));
		ButtonListener listener = new ButtonListener();
		makeAccusation.addActionListener(listener);
		JPanel panel = new JPanel();
		panel.add(makeAccusation);
		return panel;
	}
	
	// Displays the panel specifying whose turn it is
	private JPanel displayTurn() {
		JPanel panel = new JPanel();
	 	// Created in the center and the second row
		panel.setLayout(new GridLayout(2,2));
		JLabel nameLabel = new JLabel("Whose Turn?");
		JTextField whosTurn = new JTextField();
		whosTurn.setText(board.getMy_players().get(playerCounter).getName());
		whosTurn.setEditable(false);
		panel.add(nameLabel);
		panel.add(whosTurn);
		return panel;
	}
	
	// Displays the player's guess
	private JPanel displayGuess() {
		JPanel panel = new JPanel();
	 	// Created on the second row (column doesn't matter)
		panel.setLayout(new GridLayout(2,2));
		JLabel nameLabel = new JLabel("The Guess");
		JTextField dispGuess = new JTextField();
		
		// If the guess made isn't nothing, then display it
		if (board.getCurrentGuess().person != null && board.getCurrentGuess().room != null
				&& board.getCurrentGuess().weapon != null) {
			dispGuess.setText(board.getCurrentGuess().person + " "
					+ board.getCurrentGuess().room + " " + board.getCurrentGuess().weapon);
		}
		
		dispGuess.setEditable(false);
		panel.add(nameLabel);
		panel.add(dispGuess);
		// Creates border with a title around the panel
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}
	
	// Displays a panel with the result of the guess
	private JPanel displayGuessResult() {
		JPanel panel = new JPanel();
	 	// Created on the second row (column doesn't matter)
		panel.setLayout(new GridLayout(2,2));
		JLabel nameLabel = new JLabel("Response");
		JTextField dispGuess = new JTextField();
		
		// Display the guess response (assuming there is one)
		if (board.getGuessResponse() != null) dispGuess.setText(board.getGuessResponse().getCardName());
		else dispGuess.setText("");
	
		dispGuess.setEditable(false);
		panel.add(nameLabel);
		panel.add(dispGuess);
		// Creates border with a title around the panel
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Response"));
		return panel;
	}
	
	// Removes every panel, updates them, then redraws them
	// credit: https://stackoverflow.com/questions/2501861/how-can-i-remove-a-jpanel-from-a-jframe
	public void update() {
    	removeAll();
		setLayout(new GridLayout(2,3));
		add(displayTurn());
		add(playerMove());
		add(makeAccusation());
		add(rollDie());
		add(displayGuess());
		add(displayGuessResult());
    	validate();
    	repaint();
	}
	
	// Subclass the GUI uses to get allow the nextPlayer and accusation buttons to work
	private class ButtonListener implements ActionListener {
		@Override
        public void actionPerformed(ActionEvent e) {
			// Keeps the playerCounter in range
			playerCounter %= board.getMy_players().size();
	        if (e.getSource() == nextPlayer) {
	        	// If the player's turn hasn't ended, make this button display an error window
        		playerTurnOver = board.turn(playerCounter, stillPlayerTurn);
	        	update();
	        	if (playerCounter == 0) {
	        		stillPlayerTurn = true;
		        	if (!playerTurnOver && messageSuppressor != 0) {
						JFrame window = new JFrame();
						window.setPreferredSize(new Dimension(200, 525));
						JOptionPane splash = new JOptionPane();
						JOptionPane.showMessageDialog(window, "Please finish your turn!", "Unfinished Turn", JOptionPane.INFORMATION_MESSAGE);
						splash.setVisible(true);
		        	}
		        	messageSuppressor++;
		        	// If it is, update the GUI and do the next turn (to avoid having to hit next player twice)
		        	if (playerTurnOver) {
			        	stillPlayerTurn = false;
			        	messageSuppressor = 0;
			        	playerCounter++;
			        	board.turn(playerCounter, playerTurnOver);
			        	update();
		        	}
		        	else return;
	        	}
	        	// If it's not the player's turn, simply update the playerCounter
	        	messageSuppressor = 0;
	        	playerCounter++;
	        }
	        // Creates the same window as a suggestion for the player
	        else if (e.getSource() == makeAccusation) {
	        	// If it is the player's turn, let them make an accusation as normal
	        	if (playerCounter == 0 && !board.isPlayerTurnOver()) {
	        		accWindow();
	        		board.setPlayerTurnOver(true);
	        		playerTurnOver = true;
		        	update();
	        	}
	        	// Otherwise display an error window
	        	else {
					JFrame window = new JFrame();
					window.setPreferredSize(new Dimension(200, 525));
					JOptionPane splash = new JOptionPane();
					JOptionPane.showMessageDialog(window, "It is not your turn!", "Turn Error", JOptionPane.INFORMATION_MESSAGE);
					splash.setVisible(true);
	        	}
	        }
	    }
	}
	
	// Creates a modal dialog that allows the human player to make a suggestion/accusation
	public void accWindow() {
		dialogOpen = false;
		// Creates new dialog box using the main JFrame as a base
		JDialog suggFrame = new JDialog((JFrame)this.getParent().getParent().getParent().getParent(), "Make a Guess", true);
		suggFrame.setLayout(new GridLayout(4, 2));
		suggFrame.setPreferredSize(new Dimension(250, 250));
		
		// Adds the "Your room" label and the combo box displaying room options
		suggFrame.add(new JLabel("Room"));
		String[] disRooms = new String[Board.getInstance().getLegend().size() - 2];
		int i = 0;
		for (Entry<Character, String> entry : Board.getInstance().getLegend().entrySet()) {
			if (entry.getKey() == 'W' || entry.getKey() == 'X') continue;
			disRooms[i] = entry.getValue();
			i++;
		}
		JComboBox<String> roomDisp = new JComboBox<String>(disRooms);
		suggFrame.add(roomDisp);

		// Fills in a new array of all the player names for the combo box
		String[] displayers = new String[Board.getInstance().getMy_players().size()];
		for (i = 0; i < Board.getInstance().getMy_players().size(); i++) {displayers[i] = Board.getInstance().getMy_players().get(i).getName();}
		
		// Creates a new label and combo box to choose a player for the suggestion
		suggFrame.add(new JLabel("Person"));
		JComboBox<String> playerChoice = new JComboBox<String>(displayers);
		suggFrame.add(playerChoice);
		
		// Adds a new label and combo box to choose a weapon for the suggestion
		String[] disWeapons = new String[Board.getInstance().getWeapons().size()];
		disWeapons = Board.getInstance().getWeapons().toArray(disWeapons);
		suggFrame.add(new JLabel("Weapon"));
		JComboBox<String> weaponChoice = new JComboBox<String>(disWeapons);
		suggFrame.add(weaponChoice);
		
		subButtonListener buttons = null;
		
		// Adds the ability for the buttons to work
		buttons = new subButtonListener(playerChoice, weaponChoice, roomDisp, suggFrame);

		// Adds everything to the dialog window
		sub.addActionListener(buttons);
		cancel.addActionListener(buttons);
		suggFrame.add(sub);
		suggFrame.add(cancel);
		suggFrame.pack();
		suggFrame.setVisible(true);
		suggFrame.setModal(true);
	}
	
	// Allows the buttons in the modal dialog to work
	private class subButtonListener implements ActionListener {
		private JComboBox<String> playerChoice;
		private	JComboBox<String> weaponChoice;
		private JComboBox<String> roomChoice;
		private JDialog currentWindow;
		
		// Constructor
		public subButtonListener(JComboBox<String> pChoice, JComboBox<String> wChoice,
				JComboBox<String> rChoice, JDialog currentWindow) {
			this.playerChoice = pChoice;
			this.weaponChoice = wChoice;
			this.roomChoice = rChoice;
			this.currentWindow = currentWindow;
		}

		@Override
		// Checks the buttons on the Accusation dialog
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == sub && !dialogOpen) {
				Solution guess = new Solution();
				guess.person = (String) playerChoice.getSelectedItem();
				guess.weapon = (String) weaponChoice.getSelectedItem();
				guess.room = (String) roomChoice.getSelectedItem();
				
				// If the player's accusation is correct, they win
				if (Board.getInstance().checkAccusation(guess)) {
					JFrame window = new JFrame();
					window.setPreferredSize(new Dimension(200, 525));
					JOptionPane splash = new JOptionPane();
					JOptionPane.showMessageDialog(window, "Congratulations! You guessed correctly!", 
							"Game Win", JOptionPane.INFORMATION_MESSAGE);
					splash.setVisible(true);
				}
				// Otherwise, their accusation is refuted
				else {
					JFrame window = new JFrame();
					window.setPreferredSize(new Dimension(200, 525));
					JOptionPane splash = new JOptionPane();
					JOptionPane.showMessageDialog(window, "Sorry, your accusation is wrong.", 
							"Wrong Accusation", JOptionPane.INFORMATION_MESSAGE);
					splash.setVisible(true);
				}
				// Closes the dialog
				dialogOpen = true;
				currentWindow.dispose();
			}
			// If the player hits the cancel button, close the dialog
			else if (e.getSource() == cancel) {
				currentWindow.dispose();
			}
		}
	}
	
}
