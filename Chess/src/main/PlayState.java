package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import objects.Bishop;
import objects.King;
import objects.Knight;
import objects.Pawn;
import objects.Piece;
import objects.Point;
import objects.Queen;
import objects.Rook;

public class PlayState extends GameState {

	public static final int MARGINX = 10, MARGINY = 10;
	public static final int WIDTH = GamePanel.WIDTH - MARGINX * 2;
	public static final int HEIGHT = WIDTH;
	private static final int NB_SQUARES = 8;
	public static final int TILE_WIDTH = WIDTH / NB_SQUARES;
	private final Rectangle boardRect = new Rectangle(MARGINX, MARGINY, WIDTH, HEIGHT);
	protected static final int BLACK = 1;
	protected static final int WHITE = 0;

	private ArrayList<Piece> pieces;
	public static Texture texture;

	private int mouse_dx, mouse_dy;
	private Piece curPiece;
	private JFrame frame;
	private int pressed;
	private int turn;
	private Color dark = new Color(181, 136, 99);
	private Color light = new Color(240, 217, 181);
	private Color bg = new Color(26, 26, 26);
	private Point startPos;
	public int mx, my;
	private King whiteKing, blackKing;

	public PlayState(GameStateManager gsm, GamePanel panel) {
		super(gsm);
		frame = panel.getFrame();
		texture = new Texture();
		init();
	}

	public void init() {
		turn = WHITE;
		pieces = new ArrayList<>();

		// pieces.add(new Queen(MARGINX + 2 * TILE_WIDTH, MARGINY + 3 *
		// TILE_WIDTH, 0));
		// pieces.add(new Queen(MARGINX + 3 * TILE_WIDTH, MARGINY + 4 *
		// TILE_WIDTH, 1));
		addPieces();
		// pieces.add(new Pawn(MARGINX + 0 * TILE_WIDTH, MARGINY + 5 *
		// TILE_WIDTH, 0));
		// pieces.add(new Pawn(MARGINX + 0 * TILE_WIDTH, MARGINY + 2 *
		// TILE_WIDTH, 1));
	}

	public void pressed() {
		Point mouse = new Point((double) Mouse.x, (double) Mouse.y);
		pressed = 0;
		for (int i = 0; i < pieces.size(); i++) {
			Piece piece = pieces.get(i);
			if (piece.contains(mouse)) {
				mouse_dx = (int) (Mouse.x - piece.getX());
				mouse_dy = (int) (Mouse.y - piece.getY());
				startPos = piece.getPos();
				curPiece = piece;
			}
		}
	}

	public void released() {
		Point mouse = new Point((double) Mouse.x, (double) Mouse.y);
		if (curPiece != null) {
			if (!boardRect.contains(Mouse.x, Mouse.y) || curPiece.getColor() != turn) {
				goBack();
				return;
			}
			for (int i = 0; i < pieces.size(); i++) {
				Piece piece = pieces.get(i);
				if (piece != curPiece && piece.contains(mouse)) {
					if (piece.getColor() == curPiece.getColor()) {
						goBack();
						return;
					}
				}
			}
			mx = (Mouse.x - MARGINX) / TILE_WIDTH;
			my = (Mouse.y - MARGINY) / TILE_WIDTH;
			if (curPiece.valid(startPos, pieces, mx, my, this)) {
				curPiece.setPos(new Point(MARGINX + mx * TILE_WIDTH, MARGINY + my * TILE_WIDTH));
				if (curPiece instanceof Rook) {
					((Rook) curPiece).setHasMoved(true);
				}
				if (curPiece instanceof King) {
					((King) curPiece).setHasMoved(true);
				}
				if (curPiece instanceof Pawn) {
					((Pawn) curPiece).checkPromotion(pieces);
				}

				whiteKing.setCheck(false);
				blackKing.setCheck(false);
				for (int i = 0; i < pieces.size(); i++) {
					Piece piece = pieces.get(i);
					if (piece.isCheck(pieces, opponentKing(piece))) {
						opponentKing(piece).setCheck(true);
						break;
					}
				}
				curPiece = null;
//				 turn = (turn == 0) ? 1 : 0;
				return;
			} else {
				goBack();
				return;
			}
		}
	}

	private King opponentKing(Piece piece) {
		if (piece.getColor() == WHITE)
			return blackKing;
		else
			return whiteKing;
	}

	private void goBack() {
		curPiece.setPos(startPos);
		curPiece = null;

	}

	public void update() {
		checkKeys();

		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).update();
		}
		if (curPiece != null && Mouse.isPressed()) {
			Point point = new Point((int) (Mouse.x - mouse_dx), (int) (Mouse.y - mouse_dy));
			curPiece.setPos(point);
		}
	}

	private void checkKeys() {
		if (Keys.isPressed(Keys.F2)) {
			init();
		}
	}

	public void draw(Graphics2D g) {

		g.setColor(bg = new Color(26, 26, 26));
		g.fillRect(0, 0, WIDTH + 2 * MARGINX, GamePanel.WIDTH);

		for (int i = 0; i < NB_SQUARES; i++) {
			for (int j = 0; j < NB_SQUARES; j++) {
				g.setColor((g.getColor() == dark) ? light : dark);
				g.fillRect(MARGINX + i * TILE_WIDTH, MARGINY + j * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
			}
			g.setColor((g.getColor() == dark) ? light : dark);
		}

		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).draw(g);
		}

		if (curPiece != null)
			curPiece.draw(g);
	}

	public void newGame(String string) {
		Object[] options = { "Yes", "No, quit the game", "Cancel" };
		int n = JOptionPane.showOptionDialog(frame, string + "Would you like to play again ? ", "Game over",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		switch (n) {
		case JOptionPane.YES_OPTION:
			init();
			break;
		case JOptionPane.NO_OPTION:
			System.exit(1);
			break;
		default:
			break;
		}
	}

	public static Texture getTextureInstance() {
		return texture;
	}

	private void addPieces() {
		for (int j = 0; j < NB_SQUARES; j++) {
			pieces.add(new Pawn(MARGINX + j * TILE_WIDTH, MARGINY + 1 * TILE_WIDTH, BLACK));
			pieces.add(new Pawn(MARGINX + j * TILE_WIDTH, MARGINY + 6 * TILE_WIDTH, WHITE));
		}
		pieces.add(new Rook(MARGINX + 0 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));
		pieces.add(new Knight(MARGINX + 1 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));
		pieces.add(new Bishop(MARGINX + 2 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));
		pieces.add(new Queen(MARGINX + 3 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));
		blackKing = new King(MARGINX + 4 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK);
		pieces.add(blackKing);
		pieces.add(new Bishop(MARGINX + 5 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));
		pieces.add(new Knight(MARGINX + 6 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));
		pieces.add(new Rook(MARGINX + 7 * TILE_WIDTH, MARGINY + 0 * TILE_WIDTH, BLACK));

		pieces.add(new Rook(MARGINX + 0 * TILE_WIDTH, MARGINY + 7 * TILE_WIDTH, WHITE));
		pieces.add(new Knight(MARGINX + 1 * TILE_WIDTH, MARGINY + 7 * TILE_WIDTH, WHITE));
		pieces.add(new Bishop(MARGINX + 2 * TILE_WIDTH, MARGINY + 7 * TILE_WIDTH, WHITE));
		pieces.add(new Queen(MARGINX + 3 * TILE_WIDTH, MARGINY + 7 * TILE_WIDTH, WHITE));
		whiteKing = new King(MARGINX + 4 * TILE_WIDTH, MARGINY + 7 * TILE_WIDTH, WHITE);
		pieces.add(whiteKing);
		 pieces.add(new Bishop(MARGINX + 5 * TILE_WIDTH, MARGINY + 7 *
		 TILE_WIDTH, WHITE));
		 pieces.add(new Knight(MARGINX + 6 * TILE_WIDTH, MARGINY + 7 *
		 TILE_WIDTH, WHITE));
		pieces.add(new Rook(MARGINX + 7 * TILE_WIDTH, MARGINY + 7 * TILE_WIDTH, WHITE));
	}

}