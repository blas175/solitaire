package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import main.Id;
import main.PlayState;

public class Card extends GameObject {
	
	private boolean locked = false;
	private Color color = Color.white;

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Card(int x, int y) {
		super(x, y);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color );
		g.fill(getBounds());
		g.setColor(Color.black);
		g.draw(getBounds());
		
	}

}
