package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.PlayState;
import main.Texture;

public class Card extends GameObject {

	private boolean visible = false;
	private int color;
	private int value;
	private Texture texture = PlayState.getTextureInstance();

	public Card(int x, int y, int color, int value) {
		super(x, y);
		this.color = color;
		this.value = value;
	}
	@Override
	public void draw(Graphics2D g) {
		if(visible)
			g.drawImage(texture.cards[color][value], (int) x, (int) y, null);
		else{
			g.setColor(Color.orange);
			g.fill(getBounds());
			g.setColor(Color.black);
			g.draw(getBounds());
		}

	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void update() {

	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
