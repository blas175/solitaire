package objects;

import java.util.ArrayList;

public abstract class BaseSlot extends GameObject {

	protected ArrayList<Card> cardPile;
	
	public BaseSlot(int x, int y) {
		super(x, y);
		cardPile = new ArrayList<>();
	}

	public ArrayList<Card> getCardPile() {
		return cardPile;
	}

	public void setCardPile(ArrayList<Card> cardPile) {
		this.cardPile = cardPile;
	}

}
