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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGUI extends JPanel {
	private JButton nextPlayer;
	private JButton makeAccusation;
	private Board board = Board.getInstance();
	private int playerCounter = 0;
	
	// Constructor. Adds each panel to the window 
	public ClueGUI() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,3));
		add(displayTurn());
		add(playerMove());
		add(makeAccusation());
		add(rollDie());
		add(displayGuess());
		add(displayGuessResult());
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
		dispGuess.setEditable(false);
		panel.add(nameLabel);
		panel.add(dispGuess);
		// Creates border with a title around the panel
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Response"));
		return panel;
	}
	
	// Subclass the GUI uses to get allow the nextPlayer and accusation buttons to work
	private class ButtonListener implements ActionListener {
		@Override
        public void actionPerformed(ActionEvent e) {
			// Keeps the playerCounter in range
			playerCounter %= board.getMy_players().size();
	        if (e.getSource() == nextPlayer) {
	        	if (board.turn(playerCounter)) {
	        		
	        	}
	        	// Removes every panel, updates them, then redraws them
	        	// credit: https://stackoverflow.com/questions/2501861/how-can-i-remove-a-jpanel-from-a-jframe
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
	        	playerCounter++;
	        }
	        else if (e.getSource() == makeAccusation) {
	        	// Removes every panel, updates them, then redraws them
	        	// credit: https://stackoverflow.com/questions/2501861/how-can-i-remove-a-jpanel-from-a-jframe
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
	    }
	}
}
