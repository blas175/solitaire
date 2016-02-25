package objects;

import java.util.ArrayList;

import main.Mouse;
import main.PlayState;

public class Rook extends Piece {

	private boolean hasMoved = false;

	public Rook(double x, double y, int color) {
		super(x, y);
		this.color = color;
		this.value = 4;
	}

	@Override
	public boolean isCheck(ArrayList<Piece> pieces, King king) {
		int gx = getGX();
		int gy = getGY();
		int kx = king.getGX();
		int ky = king.getGY();

		if (kx == gx) {
			if (gy < ky) {
				for (int cy = gy + 1; cy <= ky; cy++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cy == ky && piece != this && piece.getGX() == kx && piece.getGY() == ky) {
							return true;
						}
						if (piece != this && piece.getGY() == cy && piece.getGX() == kx) {
							return false;
						}
					}
				}
				return true;
			}
			if (gy > ky) {
				for (int cy = gy - 1; cy >= ky; cy--) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cy == ky && piece != this && piece.getGX() == kx && piece.getGY() == ky) {
							return true;
						}
						if (piece != this && piece.getGY() == cy && piece.getGX() == kx) {
							return false;
						}
					}
				}
				return true;
			}
		}
		if (ky == gy) {
			if (gx < kx) {
				for (int cx = gx + 1; cx <= kx; cx++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == kx && piece != this && piece.getGY() == ky && piece.getGX() == kx) {
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == ky) {
							return false;
						}
					}
				}
				return true;
			}
			if (gx > kx) {
				for (int cx = gx - 1; cx >= kx; cx--) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == kx && piece != this && piece.getGY() == ky && piece.getGX() == kx) {
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == ky) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean valid(Point startPos, ArrayList<Piece> pieces, int mx, int my, PlayState playState) {

		int gx = (int) (startPos.x - MARGINX) / width;
		int gy = (int) (startPos.y - MARGINY) / height;

		if (mx == gx) {
			if (gy < my) {
				for (int cy = gy + 1; cy <= my; cy++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cy == my && piece != this && piece.getGX() == mx && piece.getGY() == my) {
							return true;
						}
						if (piece != this && piece.getGY() == cy && piece.getGX() == mx) {
							return false;
						}
					}
				}
				return true;
			}
			if (gy > my) {
				for (int cy = gy - 1; cy >= my; cy--) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cy == my && piece != this && piece.getGX() == mx && piece.getGY() == my) {
							return true;
						}
						if (piece != this && piece.getGY() == cy && piece.getGX() == mx) {
							return false;
						}
					}
				}
				return true;
			}
		}
		if (my == gy) {
			if (gx < mx) {
				for (int cx = gx + 1; cx <= mx; cx++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == mx && piece != this && piece.getGY() == my && piece.getGX() == mx) {
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == my) {
							return false;
						}
					}
				}
				return true;
			}
			if (gx > mx) {
				for (int cx = gx - 1; cx >= mx; cx--) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == mx && piece != this && piece.getGY() == my && piece.getGX() == mx) {
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == my) {
							return false;
						}
					}
				}
				return true;
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
