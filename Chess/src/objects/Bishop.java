package objects;

import java.util.ArrayList;

import main.Mouse;
import main.PlayState;

public class Bishop extends Piece {

	public Bishop(double x, double y, int color) {
		super(x, y);
		this.color = color;
		this.value = 2;
	}

	@Override
	public boolean isCheck(ArrayList<Piece> pieces, King king) {
		int gx = getGX();
		int gy = getGY();
		int kx = king.getGX();
		int ky = king.getGY();
		
		int dx = kx - gx;
		int dy = ky - gy;

		if (dx == dy) {
			if (gx < kx) {
				for (int cx = gx + 1; cx <= kx; cx++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == kx && piece != this && piece.getGY() == ky && piece.getGX() == kx) {
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy + cx - gx) {
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
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy + cx - gx) {
							return false;
						}
					}
				}
				return true;
			}
		}

		if (dx == -dy) {
			if (gx < kx) {
				for (int cx = gx + 1; cx <= kx; cx++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == kx && piece != this && piece.getGY() == ky && piece.getGX() == kx) {
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy - cx + gx) {
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
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy - cx + gx) {
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

		int dx = mx - gx;
		int dy = my - gy;

		if (dx == dy) {
			if (gx < mx) {
				for (int cx = gx + 1; cx <= mx; cx++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == mx && piece != this && piece.getGY() == my && piece.getGX() == mx) {
							pieces.remove(piece);
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy + cx - gx) {
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
							pieces.remove(piece);
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy + cx - gx) {
							return false;
						}
					}
				}
				return true;
			}
		}

		if (dx == -dy) {
			if (gx < mx) {
				for (int cx = gx + 1; cx <= mx; cx++) {
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						if (cx == mx && piece != this && piece.getGY() == my && piece.getGX() == mx) {
							pieces.remove(piece);
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy - cx + gx) {
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
							pieces.remove(piece);
							return true;
						}
						if (piece != this && piece.getGX() == cx && piece.getGY() == gy - cx + gx) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
}
