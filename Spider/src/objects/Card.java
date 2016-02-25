package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Card extends GameObject {

	private boolean visible = false;
	private int color;
	private int value;
	private double speed = 0.0;
	private double deltaX, deltaY;
	private double angle;
	private float len;
	private int t;
	private double scaleFactor = 1;

	public Card(int x, int y, int color, int value) {
		super(x, y);
		this.color = color;
		this.value = value;
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform transform = g.getTransform();
		g.translate(x + width / 2, y + height / 2);
		g.scale(scaleFactor, 1);
		g.translate(-x - width / 2, -y - height / 2);
		if (visible && scaleFactor > 0)
			g.drawImage(texture.cards[color][value], (int) x, (int) y, null);
		else {
			g.setColor(Color.orange);
			g.fill(getBounds());
			g.setColor(Color.black);
			g.draw(getBounds());
		}
		g.setTransform(transform);

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

	public void moveTo(double tx, double ty) {
		dx = tx;
		dy = ty;

		deltaX = dx - x;
		deltaY = dy - y;

		len = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		speed = 60.0;
		t = 0;
		angle = Math.atan2(deltaY, deltaX);

	}

	public void moveTo(Point pos) {
		dx = pos.x;
		dy = pos.y;

		deltaX = dx - x;
		deltaY = dy - y;

		len = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		speed = 40.0;
		t = 0;
		angle = Math.atan2(deltaY, deltaX);
	}

	@Override
	public void update() {
		if (scaleFactor < 1)
			scaleFactor += 0.1;
		if (scaleFactor > 1)
			scaleFactor = 1;
		x += Math.cos(angle) * speed;
		y += Math.sin(angle) * speed;

		t += speed;
		if (t >= len) {
			speed = 0;
			x = dx;
			y = dy;

		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean underOK() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "" + value;
	}

	public void turn() {
		scaleFactor = -1;
		visible = true;

	}
}
