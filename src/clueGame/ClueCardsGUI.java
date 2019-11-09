package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class ClueCardsGUI extends JPanel {
	public ClueCardsGUI() {
		super();
		add(displayCards());
	}

	// Displays the panel with the people cards
	private JPanel displayCards() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(120, 660));
		panel.setLayout(new GridLayout(3,1));
		
		// Adds the people card field
		JTextField peopleCard = new JTextField();
		peopleCard.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		peopleCard.setEditable(false);
		panel.add(peopleCard);
		
		// Adds the weapon card field
		JTextField weaponCard = new JTextField();
		weaponCard.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weaponCard.setEditable(false);
		panel.add(weaponCard);
		
		// Adds the room card field
		JTextField roomCard = new JTextField();
		roomCard.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		roomCard.setEditable(false);
		panel.add(roomCard);
		
		// Adds a named border around the card fields
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		return panel;
	}
}
