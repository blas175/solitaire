package objects;

import java.awt.Graphics2D;
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
	
	@Override
	public void draw(Graphics2D g) {
		for (int i = 0; i < cardPile.size(); i++) {
			cardPile.get(i).draw(g);
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < cardPile.size(); i++) {
			cardPile.get(i).update();
		}
	}

}
