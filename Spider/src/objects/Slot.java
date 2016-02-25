package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Slot extends BaseSlot {

	private int offsetVis = height / 5;
	private int offsetHid = height / 16;
	private Color slotColor = Color.gray;
	private long timer;
	private int ticks = 0;

	public Slot(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(slotColor);
		g.fill(getBounds());

		for (int i = 0; i < cardPile.size(); i++) {
			cardPile.get(i).draw(g);
		}
	}

	@Override
	public void update() {
		super.update();
		if (ticks > 0 && System.currentTimeMillis() - timer > 200) {
			timer = System.currentTimeMillis();
			slotColor = (slotColor == Color.red) ? Color.gray : Color.red;
			ticks--;
		}
	}

	public void addToPile(Card activeCard) {
		if (!cardPile.isEmpty()) {
			if (lastCard().isVisible()) {
				activeCard.moveTo(x, lastCard().getY() + offsetVis);
			} else {
				activeCard.moveTo(x, lastCard().getY() + offsetHid);
			}
		} else {
			activeCard.moveTo(getPos());
		}
		cardPile.add(activeCard);
	}

	public void addToPileHid(Card card) {
		cardPile.add(card);
		card.moveTo(x, y + (cardPile.size() - 1) * offsetHid);
	}

	public ArrayList<Card> getCardsUnder(Card activeCard) {
		ArrayList<Card> cardsUnder = new ArrayList<>();
		for (int i = cardPile.lastIndexOf(activeCard) + 1; i < cardPile.size(); i++) {
			cardsUnder.add(cardPile.get(i));
		}
		return cardsUnder;
	}

	public void removeCard(Card activeCard) {
		for (int i = cardPile.lastIndexOf(activeCard) + 1; i < cardPile.size(); i++) {
			cardPile.remove(i);
			i--;
		}
		cardPile.remove(activeCard);
	}

	public void removeCard(ArrayList<Card> full) {
		for (Card card : full) {
			removeCard(card);
		}

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

	public boolean valid(Card card) {
		ArrayList<Card> cardsUnder = getCardsUnder(card);
		for (int i = 0; i < cardsUnder.size(); i++) {
			Card cardUnder = cardsUnder.get(i);
			if (cardUnder.getValue() != card.getValue() - i - 1 || cardUnder.getColor() != card.getColor()) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Card> getFull() {
		ArrayList<Card> list = new ArrayList<>();
		list.add(cardPile.get(cardPile.size() - 1));

		for (int i = cardPile.size() - 2; i >= 0; i--) {
			Card card = cardPile.get(i);
			if (card.isVisible() && card.getValue() == cardPile.get(i + 1).getValue() + 1
					&& card.getColor() == cardPile.get(i + 1).getColor())
				list.add(card);
			else
				return null;
			if (list.size() == 13) {
				return list;
			}
		}
		return null;
	}

	public void blink() {
		ticks = 4;
		timer = System.currentTimeMillis();

	}

	public void shrink() {
		offsetVis = height / 10;
		updateCardPile();
	}

	public void resetOffset() {
		offsetVis = height / 5;
		updateCardPile();
	}

	private void updateCardPile() {
		for (int i = 1; i < cardPile.size(); i++) {
			Card card = cardPile.get(i);
			if (cardPile.get(i - 1).isVisible())
				card.moveTo(x, cardPile.get(i - 1).dy + offsetVis);
			else
				card.moveTo(x, cardPile.get(i - 1).dy + offsetHid);
		}
	}

}
