package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.util.Random;

public class DetectiveNotesGUI  extends JDialog{
	
	public DetectiveNotesGUI(JFrame A) {
		// Create a layout with 2 rows
		
		super(A);
		setLayout(new GridLayout(3,2));
		setSize(550,500);
		setTitle("Dective Notes");
		JPanel panel = new JPanel();
		panel = dispPeople();
		add(panel);
		panel = dispPersonGuess();
		add(panel);
		panel = dispRooms();
		add(panel);
		panel = dispRoomGuess();
		add(panel);
		panel = dispWeapons();
		add(panel);
		panel = dispWeaponGuess();
		add(panel);
	}
	
	private JPanel dispPeople(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(3,2));
		JCheckBox box1 = new JCheckBox("Mrs. White");
		JCheckBox box2 = new JCheckBox("Mrs. Peacock");
		JCheckBox box3 = new JCheckBox("Miss Scarlet");
		JCheckBox box4 = new JCheckBox("Colonel Mustard");
		JCheckBox box5 = new JCheckBox("Mr. Green");
		JCheckBox box6 = new JCheckBox("Professor Plum");
		panel.add(box1);
		panel.add(box2);
		panel.add(box3);
		panel.add(box4);
		panel.add(box5);
		panel.add(box6);
		return panel;
	}
	private JPanel dispRooms(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(5, 2));
		JCheckBox box1 = new JCheckBox("Conservatory");
		JCheckBox box2 = new JCheckBox("Office");
		JCheckBox box3 = new JCheckBox("Pool");
		JCheckBox box4 = new JCheckBox("Foyer");
		JCheckBox box5 = new JCheckBox("Bathroom");
		JCheckBox box6 = new JCheckBox("Billiard Room");
		JCheckBox box7 = new JCheckBox("Study");
		JCheckBox box8 = new JCheckBox("Armory");
		JCheckBox box9 = new JCheckBox("Kitchen");

		panel.add(box1);
		panel.add(box2);
		panel.add(box3);
		panel.add(box4);
		panel.add(box5);
		panel.add(box6);
		panel.add(box7);
		panel.add(box8);
		panel.add(box9);
		return panel;
	}
	private JPanel dispWeapons(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(3,2));
		JCheckBox box1 = new JCheckBox("Knife");
		JCheckBox box2 = new JCheckBox("Pistol");
		JCheckBox box3 = new JCheckBox("Pool Cue");
		JCheckBox box4 = new JCheckBox("Keyboard");
		JCheckBox box5 = new JCheckBox("Chair Leg");
		JCheckBox box6 = new JCheckBox("Candle Stick");
		panel.add(box1);
		panel.add(box2);
		panel.add(box3);
		panel.add(box4);
		panel.add(box5);
		panel.add(box6);
		return panel;
	}
	private JPanel dispPersonGuess(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return panel;
	}
	private JPanel dispRoomGuess(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return panel;
	}
	private JPanel dispWeaponGuess(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel;
	}
}
