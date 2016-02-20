package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.PlayState;

public abstract class GameObject {
	protected static final int TILE_BORDER = PlayState.TILE_BORDER;
	protected static final int MARGINX = PlayState.MARGINX, MARGINY = PlayState.MARGINY;
	protected static final int CARD_SPACE = PlayState.CARD_SPACE;

	protected double x, y, dx, dy;
	protected int width = PlayState.TILE_WIDTH;
	protected int height = PlayState.TILE_HEIGHT;

	protected boolean locked = false;

	protected static final Color red = new Color(249, 64, 64, 140);
	protected static final Color orange = new Color(255, 69, 0);
	protected static final Color gray = new Color(210, 210, 210);

	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.dx = x;
		this.dy = y;
	}

	public abstract void update();

	public abstract void draw(Graphics2D g);

	public Rectangle getBounds() {
		return new Rectangle((int) (x), (int) (y), (int) width, (int) height);
	}

	public Point getPos() {
		return new Point(x, y);
	}

	public void setPos(Point pos) {
		x = pos.x;
		y = pos.y;
		dx = pos.x;
		dy = pos.y;

	}

	public double getX() {
		return dx;
	}

	public void setX(double x) {
		this.x = x;
		this.dx = x;
	}

	public double getY() {
		return dy;
	}

	public void setY(double y) {
		this.y = y;
		this.dy = y;
	}

	public boolean contains(Point point) {
		return getBounds().contains((int)point.x, (int)point.y);

	}

	public boolean contains(Card card) {
		return getBounds().intersects(card.getBounds());
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}