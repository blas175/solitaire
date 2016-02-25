package objects;

import java.util.ArrayList;

import main.Mouse;
import main.PlayState;

public class King extends Piece {

	private boolean hasMoved = false;

	public King(double x, double y, int color) {
		super(x, y);
		this.color = color;
		this.value = 0;
	}

	@Override
	public boolean valid(Point startPos, ArrayList<Piece> pieces, int mx, int my, PlayState playState) {

		int gx = (int) (startPos.x - MARGINX) / width;
		int gy = (int) (startPos.y - MARGINY) / height;

		if ((mx == gx + 1 && my == gy + 1) || (mx == gx + 1 && my == gy - 1) || (mx == gx + 1 && my == gy)
				|| (mx == gx - 1 && my == gy + 1) || (mx == gx - 1 && my == gy - 1) || (mx == gx - 1 && my == gy)
				|| (mx == gx && my == gy + 1) || (mx == gx && my == gy - 1)) {
			for (int i = 0; i < pieces.size(); i++) {
				Piece piece = pieces.get(i);
				if (piece != this && piece.getGX() == mx && piece.getGY() == my) {
					pieces.remove(piece);
					return true;
				}
			}
			return true;
		}
		if (!hasMoved) {
			if (mx == gx + 2) {
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if ((piece.getGX() == gx + 1 || piece.getGX() == gx + 2) && piece.getGY() == gy) {
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
					if ((piece.getGX() == gx - 1 || piece.getGX() == gx - 2 || piece.getGX() == gx - 3)
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
}
