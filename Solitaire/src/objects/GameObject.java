package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import main.PlayState;

public abstract class GameObject {
	protected static final int TILE_BORDER = PlayState.TILE_BORDER;
	protected static final int MARGINX = PlayState.MARGINX, MARGINY = PlayState.MARGINY;
	protected static final int TILE_WIDTH = PlayState.TILE_WIDTH;

	protected int x, y;
	protected int width = PlayState.TILE_WIDTH;
	protected int height = PlayState.TILE_HEIGHT;
	public boolean vis;

	protected int value;
	protected boolean active = false;
	protected boolean locked = false;

	protected static final Color red = new Color(249, 64, 64, 140);
	protected static final Color orange = new Color(255, 69, 0);
	protected static final Color gray = new Color(210, 210, 210);

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		vis = true;
	}

	public abstract void update();

	public abstract void draw(Graphics2D g);

	public boolean isVisible() {
		return vis;
	}

	public void setVisible(Boolean visible) {
		vis = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) (MARGINX + x + width + TILE_BORDER),
				(int) (MARGINY + y + height + TILE_BORDER), (int) width - TILE_BORDER * 2,
				(int) height - TILE_BORDER * 2);
	}
	public Point getPos() {
		return new Point(x, y);
	}

	public void setPos(Point pos) {
		x = pos.x;
		y = pos.y;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean contains(Point point) {
		return getBounds().contains(point);

	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}