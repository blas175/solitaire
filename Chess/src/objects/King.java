package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.PlayState;

public class King extends Piece {

	private boolean hasMoved = false;
	private boolean check = false;

	public King(double x, double y, int color) {
		super(x, y);
		this.color = color;
		this.value = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		if (check) {
			g.setColor(Color.red);
			g.fill(getBounds());
		}
		super.draw(g);
	}

	@Override
	public boolean valid(Point startPos, ArrayList<Piece> pieces, int mx, int my, PlayState playState) {

		int gx = (int) (startPos.x - MARGINX) / width;
		int gy = (int) (startPos.y - MARGINY) / height;

		if ((mx == gx + 1 && my == gy + 1) || (mx == gx + 1 && my == gy - 1) || (mx == gx + 1 && my == gy)
				|| (mx == gx - 1 && my == gy + 1) || (mx == gx - 1 && my == gy - 1) || (mx == gx - 1 && my == gy)
				|| (mx == gx && my == gy + 1) || (mx == gx && my == gy - 1)) {
			return true;
		}
		if (!hasMoved && !check) {
			if (mx == gx + 2) {
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece != this && (piece.getGX() == gx + 1 || piece.getGX() == gx + 2) && piece.getGY() == gy) {
						return false;
					}
					if (piece instanceof Rook && piece.getColor() == color && !((Rook) piece).hasMoved()
							&& piece.getGX() == 7) {
						playState.mx = mx;
						piece.setX(MARGINX + (mx - 1) * width);
						hasMoved = true;
						((Rook) piece).setHasMoved(true);
						return true;
					}
				}
			}
			if (mx == gx - 2) {
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece != this && (piece.getGX() == gx - 1 || piece.getGX() == gx - 2 || piece.getGX() == gx - 3)
							&& piece.getGY() == gy) {
						return false;
					}
				}
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece instanceof Rook && piece.getColor() == color && !((Rook) piece).hasMoved()
							&& piece.getGX() == 0) {
						playState.mx = mx;
						piece.setX(MARGINX + (mx + 1) * width);
						hasMoved = true;
						((Rook) piece).setHasMoved(true);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public void setCheck(boolean check) {
		this.check = check;

	}

	public boolean hasCheck() {
		return this.check;

	}
}
