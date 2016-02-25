package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Pile extends BaseSlot {

	private int nbFamilies = 5;

	public Pile(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		for (int i = 0; i < nbFamilies; i++) {
			g.setColor(Color.orange);
			g.fillRect((int) (x + i * width / 4), (int) (y), (int) width, (int) height);
			g.setColor(Color.black);
			g.drawRect((int) (x + i * width / 4), (int) (y), (int) width, (int) height);
		}
	}

	public int getNbFamilies() {
		return nbFamilies;
	}

	public void setNbFamilies(int nbFamilies) {
		this.nbFamilies = nbFamilies;
	}
	public Rectangle getBounds() {
		return new Rectangle((int) (x), (int) (y), (int) width+(nbFamilies-1)*width*1/4, (int) height);
	}
}
