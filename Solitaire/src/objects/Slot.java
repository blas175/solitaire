package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Slot extends BaseSlot {

	private int offset = height / 8;

	public Slot(int x, int y) {
		super(x, y);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.gray);
		g.fill(getBounds());
		
		for (int i = 0; i < cardPile.size(); i++) {
			cardPile.get(i).draw(g);
		}

	}

	public void addToPile(Card activeCard) {
		cardPile.add(activeCard);
		activeCard.setX(x);
		activeCard.setY(y + (cardPile.size()-1) * offset);

	}

	public void removeCard(Card activeCard, ArrayList<Card> cardsUnder) {
		for (int i = cardPile.lastIndexOf(activeCard)+1 ; i < cardPile.size(); i++) {
			cardsUnder.add(cardPile.get(i));
			cardPile.remove(i);
			i--;
		}
		cardPile.remove(activeCard);
	}

	public int getOffset() {
		return (cardPile.size()-1) * offset;
	}

	public void printCards() {
		System.out.println(""+cardPile.size());
		
	}

	public Card lastCard() {
		return cardPile.get(cardPile.size()-1);
	}
}
