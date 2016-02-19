package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JPanel;

import objects.Card;
import objects.Pile;
import objects.Slot;

public class PlayState extends GameState {

	public static final int NB_PILES = 7;
	public static final int MARGINX = 10, MARGINY = 10;
	public static final int WIDTH = GamePanel.WIDTH - MARGINX * 2;
	public static final int HEIGHT = GamePanel.HEIGHT - MARGINY * 2;
	public static final int TILE_WIDTH = WIDTH / 10;
	public static final int TILE_HEIGHT = (int) ((float) TILE_WIDTH * 1.4);
	public static final int TILE_BORDER = 2;
	public static final int TILE_ROUND = 5;
	public static final Color red = new Color(179, 6, 6);
	public static final Color green = new Color(31, 150, 27);
	public static final Color blue = new Color(24, 33, 226);
	private static final int CARD_SPACE = TILE_WIDTH / 2;
	private static final int NB_CARDS = 52;

	private Rectangle boardRect = new Rectangle(MARGINX, MARGINY, WIDTH, HEIGHT);

	private ArrayList<Slot> slots;
	private ArrayList<Card> cardsUnder;
	public static Texture texture;

	private int offset = TILE_HEIGHT / 8;
	private int mouse_dx, mouse_dy;
	private Card activeCard;
	private Pile pile;
	private Slot startSlot;
	private ArrayList<CardId> cardsIds;

	public PlayState(GameStateManager gsm, JPanel panel) {
		super(gsm);
		init();
	}

	public void init() {
		texture = new Texture();
		slots = new ArrayList<>();
		cardsUnder = new ArrayList<>();
		cardsIds = new ArrayList<>();
		pile = new Pile(0, 0);

		for (int i = 0; i < NB_CARDS; i++) {
			cardsIds.add(new CardId(i / 13, i % 13));
		}
		Collections.shuffle(cardsIds);

		for (int i = 0; i < NB_PILES; i++) {
			Slot slot = new Slot(MARGINX + i * (TILE_WIDTH + CARD_SPACE), MARGINY + TILE_WIDTH * 2);
			slots.add(slot);
			for (int j = 0; j < i + 1; j++) {
				CardId cardId = cardsIds.get(0);
				Card card = new Card(MARGINX + i * (TILE_WIDTH + CARD_SPACE), MARGINY + TILE_WIDTH * 2,
						cardId.getColor(), cardId.getValue());
				cardsIds.remove(cardId);
				if(j == i){
					card.setVisible(true);
				}
				slot.addToPile(card);
			}
		}
	}

	public void pressed() {
		for (int i = 0; i < slots.size(); i++) {
			Slot slot = slots.get(i);
			for (int j = slot.getCardPile().size() - 1; j >= 0; j--) {
				Card card = slot.getCardPile().get(j);
				if (card.contains(new Point(Mouse.x, Mouse.y)) && card.isVisible()) {
					mouse_dx = (int) (Mouse.x - card.getX());
					mouse_dy = (int) (Mouse.y - card.getY());
					activeCard = card;
					slot.removeCard(activeCard, cardsUnder);
					startSlot = slot;
					return;
				}
			}
		}
	}

	public void released() {
		if (activeCard != null) {
			for (int i = slots.size() - 1; i >= 0; i--) {
				Slot s = slots.get(i);
				if (s.contains(new Point(Mouse.x, Mouse.y - s.getOffset()))) {
					if (s.getCardPile().isEmpty() || (s.lastCard().getValue() == activeCard.getValue() + 1
							&& s.lastCard().getColor() - activeCard.getColor() % 2 != 0)) {
						s.addToPile(activeCard);
						for (int j = 0; j < cardsUnder.size(); j++) {
							s.addToPile(cardsUnder.get(j));
						}
						if(!startSlot.getCardPile().isEmpty())
							startSlot.lastCard().setVisible(true);
						cardsUnder.clear();
						activeCard = null;
						return;
					}
				}
			}
			startSlot.addToPile(activeCard);
			for (int i = 0; i < cardsUnder.size(); i++) {
				startSlot.addToPile(cardsUnder.get(i));
			}
			cardsUnder.clear();
			activeCard = null;
		}

	}

	public void update() {
		checkKeys();

		if (activeCard != null && Mouse.isPressed()) {
			Point point = new Point((int) (Mouse.x - mouse_dx), (int) (Mouse.y - mouse_dy));
			activeCard.setPos(point);
			for (int i = 0; i < cardsUnder.size(); i++) {
				cardsUnder.get(i).setPos(point);
				cardsUnder.get(i).setY(activeCard.getY() + (i + 1) * offset);
			}
		}
	}

	private void checkKeys() {

	}

	public void draw(Graphics2D g) {
		// BG
		g.setColor(green);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// draw grid
		drawGrid(g);
		pile.draw(g);

		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).draw(g);
		}

		if (activeCard != null) {
			activeCard.draw(g);
		}
		for (int i = 0; i < cardsUnder.size(); i++) {
			cardsUnder.get(i).draw(g);
		}

		if (Mouse.isPressed())
			drawDrag(g);

	}

	private void drawDrag(Graphics2D g) {
		Point point = new Point((int) (Mouse.x - mouse_dx), (int) (Mouse.y - mouse_dy));
		// g.drawImage(Texture.brickButtonImage(currentId, 1), (int) point.x,
		// (int) point.y, null);
	}

	private void drawGrid(Graphics2D g) {
		// g.setColor(new Color(224, 224, 224));
		// for (int i = 0; i < NB_TILES_X; i++) {
		// for (int j = 0; j < NB_TILES_Y; j++) {
		// g.fillRoundRect(MARGINX + i * TILE_WIDTH + TILE_BORDER, MARGINY + j *
		// TILE_WIDTH + TILE_BORDER,
		// TILE_WIDTH - TILE_BORDER * 2, TILE_WIDTH - TILE_BORDER * 2,
		// TILE_ROUND, TILE_ROUND);
		// }
		// }
	}

	public void newGame() {
		init();
	}

	public static Texture getTextureInstance() {
		return texture;
	}

	public void rightClick() {
	}
}