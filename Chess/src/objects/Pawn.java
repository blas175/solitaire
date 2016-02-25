package objects;

import java.util.ArrayList;

import main.PlayState;

public class Pawn extends Piece {

	public Pawn(double x, double y, int color) {
		super(x, y);
		this.color = color;
		this.value = 5;
	}

	@Override
	public boolean isCheck(ArrayList<Piece> pieces, King king) {
		int gx = getGX();
		int gy = getGY();

		int kx = king.getGX();
		int ky = king.getGY();

		if (color == WHITE && ky == gy - 1 && (kx == gx - 1 || kx == gx + 1)) {
			return true;
		}
		if (color == BLACK && ky == gy + 1 && (kx == gx - 1 || kx == gx + 1)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean valid(Point startPos, ArrayList<Piece> pieces, int mx, int my, PlayState playState) {

		int gx = (int) (startPos.x - MARGINX) / width;
		int gy = (int) (startPos.y - MARGINY) / height;

		if (mx == gx + 1 || mx == gx - 1) {
			if ((my == gy + 1 && color == BLACK) || (my == gy - 1 && color == WHITE)) {
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece != this && piece.getGX() == mx && piece.getGY() == my) {
						return true;
					}
				}
				return false;
			}
		}

		if (mx == gx) {
			if ((my == gy + 1 && color == BLACK) || (my == gy - 1 && color == WHITE)) {
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece != this && piece.getGX() == mx && piece.getGY() == my) {
						return false;
					}
				}
				return true;
			}
			if ((color == BLACK && gy == 1 && my == gy + 2) || (color == WHITE && gy == 6 && my == gy - 2)) {
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece != this && piece.getGX() == gx && piece.getGY() == (my + gy) / 2) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public void checkPromotion(ArrayList<Piece> pieces) {
		if ((color == BLACK && getGY() == 7) || (color == WHITE && getGY() == 0)) {
			pieces.remove(this);
			pieces.add(new Queen(x, y, color));
		}

	}

}
