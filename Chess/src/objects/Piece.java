package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.PlayState;

public abstract class Piece extends GameObject {

	protected int color;
	protected int value;

	public Piece(double x, double y) {
		super(x, y);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(texture.pieces[color][value], (int) x, (int) y, null);

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

	public int getGX() {
		return (int) (x - MARGINX) / width;
	}

	public int getGY() {
		return (int) (y - MARGINY) / height;
	}

	public boolean valid(Point startPos, ArrayList<Piece> pieces, int mx, int my, PlayState playState) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCheck(ArrayList<Piece> pieces, King king) {
		return false;
	}

}
