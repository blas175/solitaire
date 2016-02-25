package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import objects.Card;
import objects.FinishSlot;
import objects.Pile;
import objects.Point;
import objects.Slot;

public class PlayState extends GameState {

	public static final int MARGINX = 10, MARGINY = 10;
	public static int WIDTH = GamePanel.WIDTH - MARGINX * 2;
	public static int HEIGHT = GamePanel.HEIGHT - MARGINY * 2;
	public static int TILE_WIDTH = (int) (WIDTH / 12.5);
	public static int TILE_HEIGHT = (int) ((float) TILE_WIDTH * 1.4);
	public static int CARD_SPACE = TILE_WIDTH / 4;
	public static final int TILE_BORDER = 2;
	public static final int TILE_ROUND = 5;
	public static final Color green = new Color(31, 150, 27);
	private static final int NB_VALUES = 13;
	public static final int NB_CARDS = NB_VALUES * 8;
	public static final int NB_COLORS = 4;
	public static final int NB_PILES = 10;
	private static final int PILE = 1;

	ArrayList<Slot> slots;
	FinishSlot finishSlot;
	private ArrayList<Card> cardsUnder;
	public static Texture texture;

	private int offset = TILE_HEIGHT / 8;
	private int mouse_dx, mouse_dy;
	private Card activeCard;
	private Pile pile;
	private Slot startSlot;
	ArrayList<CardId> cardsIds;
	private boolean won;
	private JFrame frame;
	private int nbCards;
	private int pressed;
	private boolean emptySlot;
	private int level = 4;
	OptionsDialog optionsDialog = new OptionsDialog(null, "Options", true, level);

	public PlayState(GameStateManager gsm, GamePanel panel) {
		super(gsm);
		frame = panel.getFrame();
		texture = new Texture();
		init();
	}

	public void init() {
		won = false;
		slots = new ArrayList<>();
		finishSlot = new FinishSlot(MARGINX, HEIGHT - MARGINY - TILE_HEIGHT);
		cardsIds = new ArrayList<>();
		cardsUnder = new ArrayList<>();
		pile = new Pile(WIDTH - MARGINX - TILE_WIDTH * 2, HEIGHT - MARGINY - TILE_HEIGHT);

		for (int i = 0; i < NB_VALUES; i++) {
			for (int j = 0; j < 8; j++) {
				cardsIds.add(new CardId(j % level, i % NB_VALUES));
			}
		}

		Collections.shuffle(cardsIds);
		Collections.shuffle(cardsIds);

		for (int i = 0; i < NB_PILES; i++) {
			Slot slot = new Slot(MARGINX + i * (TILE_WIDTH + CARD_SPACE), MARGINY);
			slots.add(slot);
			if (i < 4) {
				nbCards = 6;
			} else {
				nbCards = 5;
			}
			for (int j = 0; j < nbCards; j++) {
				CardId cardId = cardsIds.get(0);
				Card card = new Card(MARGINX + i * (TILE_WIDTH + CARD_SPACE), MARGINY + TILE_WIDTH * 2,
						cardId.getColor(), cardId.getValue());
				cardsIds.remove(cardId);
				if (j == nbCards - 1)
					card.turn();
				slot.addToPileHid(card);
			}
		}
		for (int i = 0; i < cardsIds.size(); i++) {
			CardId cardId = cardsIds.get(i);
			Card card = new Card(MARGINX + TILE_WIDTH + CARD_SPACE, MARGINY, cardId.getColor(), cardId.getValue());
			card.turn();
			pile.getCardPile().add(card);
		}
	}

	public void pressed() {
		pressed = 0;
		if (!won) {
			Point mouse = new Point((double) Mouse.x, (double) Mouse.y);
			if (pile.contains(mouse)) {
				pressed = PILE;
				return;
			}

			for (int i = 0; i < slots.size(); i++) {
				Slot slot = slots.get(i);
				for (int j = slot.getCardPile().size() - 1; j >= 0; j--) {
					Card card = slot.getCardPile().get(j);
					if (card.contains(mouse) && card.isVisible()) {
						if (slot.valid(card)) {
							mouse_dx = (int) (Mouse.x - card.getX());
							mouse_dy = (int) (Mouse.y - card.getY());
							activeCard = card;
							cardsUnder = slot.getCardsUnder(activeCard);
							slot.removeCard(activeCard);
							startSlot = slot;
							return;
						}
					}
				}
			}
		}
	}

	public void released() {
		emptySlot = false;
		if (!won) {
			Point mouse = new Point((double) Mouse.x, (double) Mouse.y);
			if (pile.contains(mouse) && pressed == PILE) {
				for (int i = 0; i < slots.size(); i++) {
					Slot slot = slots.get(i);
					if (slot.getCardPile().isEmpty()) {
						slot.blink();
						emptySlot = true;
						return;
					}
				}
				if (!emptySlot) {
					for (int i = 0; i < slots.size(); i++) {
						Slot slot = slots.get(i);
						if (cardsIds.isEmpty()) {
							return;
						}
						CardId cardId = cardsIds.get(0);
						Card card = new Card((int) pile.getX(), (int) pile.getY(), cardId.getColor(),
								cardId.getValue());
						card.turn();
						cardsIds.remove(cardId);
						slot.addToPile(card);

						if (slot.getCardPile().size() > 6) {
							slot.shrink();
						} else {
							slot.resetOffset();
						}
					}
					pile.setNbFamilies(pile.getNbFamilies() - 1);
					checkShrink();
					return;
				}
			}
			if (activeCard != null) {
				for (int i = 0; i < slots.size(); i++) {
					Slot s = slots.get(i);
					if ((!s.getCardPile().isEmpty() && s.lastCard().contains(activeCard)) || s.contains(activeCard)) {
						if ((s.getCardPile().isEmpty()) || (!s.getCardPile().isEmpty()
								&& s.lastCard().getValue() == activeCard.getValue() + 1)) {
							s.addToPile(activeCard);

							for (int j = 0; j < cardsUnder.size(); j++) {
								s.addToPile(cardsUnder.get(j));
							}
							if (!startSlot.getCardPile().isEmpty() && !startSlot.lastCard().isVisible())
								startSlot.lastCard().turn();
							cardsUnder.clear();
							startSlot = null;
							activeCard = null;

							ArrayList<Card> full = s.getFull();
							if (full != null) {
								finishSlot.addToPile(full);
								if (finishSlot.getNbFamilies() == 8)
									checkWon();
								s.removeCard(full);
								if (!s.getCardPile().isEmpty() && !s.lastCard().isVisible())
									s.lastCard().turn();
							}
							checkShrink();
							return;
						}
					}
				}
				slots.remove(startSlot);
				slots.add(startSlot);
				startSlot.addToPile(activeCard);
				for (int i = 0; i < cardsUnder.size(); i++) {
					startSlot.addToPile(cardsUnder.get(i));
				}
				cardsUnder.clear();
				startSlot = null;
				activeCard = null;
			}
		}
	}

	private void checkShrink() {
		for (int i = 0; i < slots.size(); i++) {
			Slot s = slots.get(i);
			if (s.getCardPile().size() > 15) {
				s.shrink();
			} else {
				s.resetOffset();
			}
		}
	}

	private void checkWon() {
		won = true;
		newGame("You won !\n");
	}

	public void update() {
		checkKeys();

		pile.update();

		finishSlot.update();

		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).update();
		}

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
		if(Keys.isPressed(Keys.S)){
			Save.saveGame(this);
		}
		else if(Keys.isPressed(Keys.O)){
			Save.loadGame(this);
//			init();
		}

	}

	public void draw(Graphics2D g) {

		g.setColor(green);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		pile.draw(g);
		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).draw(g);
		}

		finishSlot.draw(g);

		if (activeCard != null) {
			activeCard.draw(g);
		}
		for (int i = 0; i < cardsUnder.size(); i++) {
			cardsUnder.get(i).draw(g);
		}
	}

	public void newGame(String string) {
		Object[] options = { "Yes", "No, quit the game", "Cancel" };
		int n = JOptionPane.showOptionDialog(frame, string+"Would you like to play again ? ", "Game over",
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

	@Override
	public void optionsDialog() {
		optionsDialog.setVisible(true);
		if (level != optionsDialog.getLevel()) {
			level = optionsDialog.getLevel();
			init();
		}
	}

	public static Texture getTextureInstance() {
		return texture;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}