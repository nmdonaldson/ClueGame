package clueGame;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.util.Random;

import experiment.GUI_Example;

public class ClueGUI extends JPanel {
	private JTextField name;

	// Constructor. Adds each panel to the window 
	public ClueGUI() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,3));
		JPanel panel = displayTurn();
		add(panel);
		panel = playerMove();
		add(panel);
		panel = makeAccusation();
		add(panel);
		panel = rollDie();
		add(panel);
		panel = displayGuess();
		add(panel);
		panel = displayGuessResult();
		add(panel);
	}
	
	// Creates die roll panel
	private JPanel rollDie(){
		 JPanel panel = new JPanel();
	 	// Created at row 1 column 2
		panel.setLayout(new GridLayout(1,2));
		JLabel nameLabel = new JLabel("Roll");
		JTextField dieRoll = new JTextField();
		dieRoll.setEditable(false);
		panel.add(nameLabel);
		panel.add(dieRoll);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}
	
	// Creates player movement button
	private JPanel playerMove() {
		// No layout, created using default flow
		JButton nextPlayer = new JButton("Next player");
		nextPlayer.setPreferredSize(new Dimension(255, 75));
		JPanel panel = new JPanel();
		panel.add(nextPlayer);
		return panel;
	}

	// Creates accusation button
	private JPanel makeAccusation() {
		// No layout, So it's created with the default flow
		JButton makeAccusation = new JButton("Make an accusation");
		makeAccusation.setPreferredSize(new Dimension(255, 75));
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
		JLabel nameLabel = new JLabel("Guess");
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
	
	// Creates the window and its attributes
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		// Forces the program to stop running when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(800, 200);
		ClueGUI gui = new ClueGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
