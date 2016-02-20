package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Slot extends BaseSlot {

	private int offsetVis = height / 5;
	private int offsetHid = height / 16;

	public Slot(int x, int y) {
		super(x, y);
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
		if (!cardPile.isEmpty()) {
			if(lastCard().isVisible()){
				activeCard.moveTo(x, lastCard().getY() + offsetVis);				
			}
			else{
				activeCard.moveTo(x, lastCard().getY() + offsetHid);	
			}
		}
		else{
			activeCard.moveTo(getPos());
		}
		cardPile.add(activeCard);
	}

	public void addToPileHid(Card card) {
		cardPile.add(card);
		card.moveTo(x, y + (cardPile.size() - 1) * offsetHid);
	}

	public void removeCard(Card activeCard, ArrayList<Card> cardsUnder) {
		for (int i = cardPile.lastIndexOf(activeCard) + 1; i < cardPile.size(); i++) {
			cardsUnder.add(cardPile.get(i));
			cardPile.remove(i);
			i--;
		}
		cardPile.remove(activeCard);
	}

	public int getOffset() {
		return (cardPile.size() - 1) * offsetVis;
	}

	public void printCards() {
		System.out.println("" + cardPile.size());

	}

	public Card lastCard() {
		return cardPile.get(cardPile.size() - 1);
	}
}
