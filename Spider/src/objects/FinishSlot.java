package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class FinishSlot extends BaseSlot {

	private int nbFamilies = 0;

	public FinishSlot(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		for (int i = 0; i < nbFamilies; i++) {
			g.drawImage(texture.cards[1][0], (int) (x + i * width / 4), (int) (y), null);
		}
		if (!cardPile.isEmpty() && lastCard().x != lastCard().dx && lastCard().y != lastCard().dy){
			for(Card card : cardPile){
				card.draw(g);
			}
		}
	}

	public void addToPile(Card activeCard) {
		cardPile.add(activeCard);
		activeCard.moveTo(getPos());
	}

	public void addToPile(ArrayList<Card> full) {
		nbFamilies++;
		for (Card card : full) {
			addToPile(card);
		}
	}

	public Card lastCard() {
		return cardPile.get(cardPile.size() - 1);
	}

	public int getNbFamilies() {
		return nbFamilies;
	}
	
}
