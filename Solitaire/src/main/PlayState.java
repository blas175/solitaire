package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import objects.BaseSlot;
import objects.Card;
import objects.FinishSlot;
import objects.Pile;
import objects.Slot;

public class PlayState extends GameState {

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
	public static final int CARD_SPACE = TILE_WIDTH / 2;
	public static final int NB_CARDS = 52;
	public static final int NB_COLORS = 4;
	public static final int NB_PILES = 7;
	private static final int NB_VALUES = 1;

	private ArrayList<Slot> slots;
	private ArrayList<FinishSlot> finishSlots;
	private ArrayList<Card> cardsUnder;
	public static Texture texture;

	private int offset = TILE_HEIGHT / 8;
	private int mouse_dx, mouse_dy;
	private Card activeCard;
	private Pile pile;
	private BaseSlot startSlot;
	private ArrayList<CardId> cardsIds;

	public PlayState(GameStateManager gsm, JPanel panel) {
		super(gsm);
		init();
	}

	public void init() {
		texture = new Texture();
		slots = new ArrayList<>();
		finishSlots = new ArrayList<>();
		cardsUnder = new ArrayList<>();
		cardsIds = new ArrayList<>();
		pile = new Pile(MARGINX, MARGINY);

		for (int i = 0; i < NB_CARDS; i++) {
			cardsIds.add(new CardId(i / 13, i % 13));
		}
		Collections.shuffle(cardsIds);

		for (int i = 0; i < NB_COLORS; i++) {
			FinishSlot finishSlot = new FinishSlot(MARGINX + (i + 3) * (TILE_WIDTH + CARD_SPACE), MARGINY, i);
			finishSlots.add(finishSlot);
		}

		for (int i = 0; i < NB_PILES; i++) {
			Slot slot = new Slot(MARGINX + i * (TILE_WIDTH + CARD_SPACE), MARGINY + TILE_WIDTH * 2);
			slots.add(slot);
			for (int j = 0; j < i + 1; j++) {
				CardId cardId = cardsIds.get(0);
				Card card = new Card(MARGINX + i * (TILE_WIDTH + CARD_SPACE), MARGINY + TILE_WIDTH * 2,
						cardId.getColor(), cardId.getValue());
				cardsIds.remove(cardId);
				if (j == i) {
					card.setVisible(true);
				}
				slot.addToPile(card);
			}
		}
		for (int i = 0; i < cardsIds.size(); i++) {
			CardId cardId = cardsIds.get(i);
			Card card = new Card(MARGINX + TILE_WIDTH + CARD_SPACE, MARGINY, cardId.getColor(), cardId.getValue());
			card.setVisible(true);
			pile.getCardPile().add(card);
		}
	}

	public void pressed() {
		Point mouse = new Point(Mouse.x, Mouse.y);

		if (pile.contains(mouse)) {
			pile.next();
			return;
		}
		Card pileCard = pile.getCurCard();
		if (pileCard != null && pileCard.contains(mouse)) {
			mouse_dx = (int) (Mouse.x - pileCard.getX());
			mouse_dy = (int) (Mouse.y - pileCard.getY());
			activeCard = pileCard;
			startSlot = pile;
			return;
		}

		for (int i = 0; i < finishSlots.size(); i++) {
			FinishSlot finishSlot = finishSlots.get(i);
			if (finishSlot.contains(mouse) && !finishSlot.getCardPile().isEmpty()) {
				mouse_dx = (int) (Mouse.x - finishSlot.getX());
				mouse_dy = (int) (Mouse.y - finishSlot.getY());
				activeCard = finishSlot.lastCard();
				startSlot = finishSlot;
				return;
			}
		}

		for (int i = 0; i < slots.size(); i++) {
			Slot slot = slots.get(i);
			for (int j = slot.getCardPile().size() - 1; j >= 0; j--) {
				Card card = slot.getCardPile().get(j);
				if (card.contains(mouse) && card.isVisible()) {
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
		Point mouse = new Point(Mouse.x, Mouse.y);

		if (activeCard != null) {

			for (int i = 0; i < finishSlots.size(); i++) {
				FinishSlot finishSlot = finishSlots.get(i);
				if (finishSlot.contains(mouse) && activeCard.getColor() == finishSlot.getColor()
						&& activeCard.getValue() == finishSlot.getCardPile().size()) {

					finishSlot.addToPile(activeCard);

					if (startSlot instanceof Slot) {
						Slot slot = (Slot) startSlot;
						if (!slot.getCardPile().isEmpty())
							slot.lastCard().setVisible(true);
					} else if (startSlot instanceof Pile) {
						pile.removeCurCard();
						pile.previous();
					}
					startSlot = null;
					activeCard = null;
					checkWon();
					return;
				}
			}

			for (int i = slots.size() - 1; i >= 0; i--) {
				Slot s = slots.get(i);
				if ((!s.getCardPile().isEmpty() && s.lastCard().contains(mouse)) || s.contains(mouse)) {
					if ((s.getCardPile().isEmpty() && activeCard.getValue() == 12)
							|| (!s.getCardPile().isEmpty() && s.lastCard().getValue() == activeCard.getValue() + 1
									&& (s.lastCard().getColor() - activeCard.getColor()) % 2 != 0)) {
						s.addToPile(activeCard);

						for (int j = 0; j < cardsUnder.size(); j++) {
							s.addToPile(cardsUnder.get(j));
						}
						if (startSlot instanceof Slot) {
							Slot slot = (Slot) startSlot;
							if (!slot.getCardPile().isEmpty())
								slot.lastCard().setVisible(true);
						} else if (startSlot instanceof Pile) {
							pile.removeCurCard();
							pile.previous();
						} else if (startSlot instanceof FinishSlot) {
							FinishSlot finishSlot = (FinishSlot) startSlot;
							finishSlot.removeLastCard();
						}
						cardsUnder.clear();
						startSlot = null;
						activeCard = null;
						return;
					}
				}
			}
			if (startSlot instanceof Slot) {
				Slot slot = (Slot) startSlot;
				slot.addToPile(activeCard);
				for (int i = 0; i < cardsUnder.size(); i++) {
					slot.addToPile(cardsUnder.get(i));
				}
				cardsUnder.clear();
				startSlot = null;
				activeCard = null;
			} else if (startSlot instanceof Pile) {
				activeCard.setPos(pile.getCardPos());
				activeCard = null;
			} else if (startSlot instanceof FinishSlot) {
				activeCard.setPos(startSlot.getPos());
				activeCard = null;
			}
		}
	}

	private void checkWon() {
		for (int i = 0; i < finishSlots.size(); i++) {
			FinishSlot finishSlot = finishSlots.get(i);
			if (finishSlot.getCardPile().size() != NB_VALUES)
				return;
		}
		System.out.println("WON");
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

		pile.draw(g);

		for (int i = 0; i < finishSlots.size(); i++) {
			finishSlots.get(i).draw(g);
		}

		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).draw(g);
		}

		if (activeCard != null) {
			activeCard.draw(g);
		}
		for (int i = 0; i < cardsUnder.size(); i++) {
			cardsUnder.get(i).draw(g);
		}
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