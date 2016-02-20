package objects;

import java.awt.Color;
import java.awt.Graphics2D;

public class FinishSlot extends BaseSlot {

	private int color;

	public FinishSlot(int x, int y, int color) {
		super(x, y);

		this.color = color;
	}

	public int getColor() {
		return color;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fill(getBounds());
		g.setColor(Color.black);
		g.draw(getBounds());

		if(cardPile.size() > 1)
			cardPile.get(cardPile.size() - 2).draw(g);
		if (!cardPile.isEmpty())
			lastCard().draw(g);
	}

	public void addToPile(Card activeCard) {
		cardPile.add(activeCard);
		activeCard.setPos(getPos());
	}

	public void removeLastCard() {
		cardPile.remove(lastCard());
	}

	public Card lastCard() {
		return cardPile.get(cardPile.size() - 1);
	}
}
