package clueGame;

public class Card {
	private String cardName;
	public CardType type;
	public enum CardType { PERSON, WEAPON, ROOM };
	
	public boolean equals() {
		return false;
	}

	// Getters and setters
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
}
