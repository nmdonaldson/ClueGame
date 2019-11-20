package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

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
	private Player humanPlayer;
	
	// Constructor
	public ClueCardsGUI(Player human) {
		super();
		this.humanPlayer = human;
		add(displayCards());
	}

	// Displays the panel with the people cards
	private JPanel displayCards() {
		// Initializes panel titled "My Cards"
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(120, 660));
		panel.setLayout(new GridLayout(3,1));
		ArrayList<Card> cardList = humanPlayer.getCards();
		int players = 0;
		int weapons = 0;
		int rooms = 0;
		
		// Gets the number of each type of card in the player's deck
		// This helps with the layout of the display 
		for (int i = 0; i < cardList.size(); i++) {
			switch(cardList.get(i).type) {
				case PERSON:
					players++;
					break;
				case WEAPON:
					weapons++;
					break;
				case ROOM:
					rooms++;
					break;
				default:
					System.out.println("Not a valid type!");
					break;
			}
		}

		// Adds the people card field
		JPanel peopleCards = new JPanel();
		peopleCards.setLayout(new GridLayout(players, 1));
		peopleCards.setBorder(new TitledBorder (new EtchedBorder(), "People"));

		// Adds the weapon card field
		JPanel weaponCards = new JPanel();
		weaponCards.setLayout(new GridLayout(weapons, 1));
		weaponCards.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));

		// Adds the room card field
		JPanel roomCards = new JPanel();
		roomCards.setLayout(new GridLayout(rooms, 1));
		roomCards.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		

		// Adds each of the cards from the player's deck; sorts them based on their type
		for (int i = 0; i < cardList.size(); i++) {
			switch(cardList.get(i).type) {
			// Adds a new text field for each card of the same type dealt to the player
				case PERSON:
					JTextField peopleCard = new JTextField();
					peopleCard.setText(cardList.get(i).getCardName());
					peopleCard.setEditable(false);
					peopleCards.add(peopleCard);
					break;
				case WEAPON:
					JTextField weaponCard = new JTextField();
					weaponCard.setText(cardList.get(i).getCardName());
					weaponCard.setEditable(false);
					weaponCards.add(weaponCard);
					break;
				case ROOM:
					JTextField roomCard = new JTextField();
					roomCard.setText(cardList.get(i).getCardName());
					roomCard.setEditable(false);
					roomCards.add(roomCard);
					break;
				default:
					System.out.println("Not a valid card type!");
					break;
			}
		}
		
		// Adds a named border around the card fields
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		panel.add(peopleCards);
		panel.add(weaponCards);
		panel.add(roomCards);
		return panel;
	}
}
