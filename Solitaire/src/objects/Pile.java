package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Pile extends BaseSlot {

	private int curCard = -1;

	public Pile(int x, int y) {
		super(x, y);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		if (curCard != cardPile.size() - 1) {
			g.setColor(Color.orange);
			g.fill(getBounds());
		}
		g.setColor(Color.black);
		g.draw(getBounds());

		if (curCard >= 0) {
			if (curCard >= 1)
				cardPile.get(curCard - 1).draw(g);
			cardPile.get(curCard).draw(g);
		}

	}

	public Card getCurCard() {
		if (curCard >= 0)
			return cardPile.get(curCard);
		else
			return null;
	}

	public Point getCardPos() {
		return new Point(MARGINX + width + CARD_SPACE, MARGINY);
	}

	public void removeCurCard() {
		cardPile.remove(curCard);

	}

	public void next() {
		if (curCard < cardPile.size() - 1) {
			curCard++;
		} else {
			curCard = -1;
		}

	}

	public void previous() {
		if (curCard > -1) {
			curCard--;
		} else {
			curCard = cardPile.size() - 1;
		}
	}
}
