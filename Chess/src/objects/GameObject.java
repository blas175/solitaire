package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.PlayState;
import main.Texture;

public abstract class GameObject {

	protected static final int MARGINX = PlayState.MARGINX, MARGINY = PlayState.MARGINY;
	protected static final int BLACK = 1;
	protected static final int WHITE = 0;

	protected double x, y;
	protected int width = PlayState.TILE_WIDTH;
	protected int height = PlayState.TILE_WIDTH;
	protected Texture texture = PlayState.getTextureInstance();


	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
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

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean contains(Point point) {
		return getBounds().contains((int)point.x, (int)point.y);

	}

	public boolean contains(Piece piece) {
		return getBounds().intersects(piece.getBounds());
	}
}