package objects;

import java.util.ArrayList;

import main.Mouse;
import main.PlayState;

public class Knight extends Piece {

	public Knight(double x, double y, int color) {
		super(x, y);
		this.color = color;
		this.value = 3;
	}

	@Override
	public boolean isCheck(ArrayList<Piece> pieces, King king) {
		int gx = getGX();
		int gy = getGY();

		int kx = king.getGX();
		int ky = king.getGY();

		if ((kx == gx + 2 && ky == gy + 1) || (kx == gx + 2 && ky == gy - 1) || (kx == gx - 2 && ky == gy + 1)
				|| (kx == gx - 2 && ky == gy - 1) || (kx == gx + 1 && ky == gy + 2) || (kx == gx + 1 && ky == gy - 2)
				|| (kx == gx - 1 && ky == gy + 2) || (kx == gx - 1 && ky == gy - 2)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean valid(Point startPos, ArrayList<Piece> pieces, int mx, int my, PlayState playState) {

		int gx = (int) (startPos.x - MARGINX) / width;
		int gy = (int) (startPos.y - MARGINY) / height;

		if ((mx == gx + 2 && my == gy + 1) || (mx == gx + 2 && my == gy - 1) || (mx == gx - 2 && my == gy + 1)
				|| (mx == gx - 2 && my == gy - 1) || (mx == gx + 1 && my == gy + 2) || (mx == gx + 1 && my == gy - 2)
				|| (mx == gx - 1 && my == gy + 2) || (mx == gx - 1 && my == gy - 2)) {
			for (int i = 0; i < pieces.size(); i++) {
				Piece piece = pieces.get(i);
				if (piece != this && piece.getGX() == mx && piece.getGY() == my) {
					pieces.remove(piece);
					return true;
				}
			}
			return true;
		}
		return false;
	}
}
