package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Pile extends GameObject {

	public Pile(int x, int y) {
		super(x, y);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.fill(getBounds());

	}

	public Rectangle getBounds() {
		return new Rectangle((int) (MARGINX + x + TILE_BORDER), (int) (MARGINY + y + TILE_BORDER),
				(int) width - TILE_BORDER * 2, (int) height - TILE_BORDER * 2);
	}
}
