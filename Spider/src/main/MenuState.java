package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class MenuState extends GameState {

	private static final int WIDTH = GamePanel.WIDTH;
	private static final int HEIGHT = GamePanel.HEIGHT;

	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"Quit"
	};

	private Color titleColor;
	private Font titleFont;

	private Font font;

	public MenuState(GameStateManager gsm) {

		super(gsm);

		// titles and fonts
		titleColor = Color.WHITE;
		titleFont = new Font("Times New Roman", Font.PLAIN, 40);
		font = new Font("Arial", Font.PLAIN, 30);

	}

	public void init() {}

	public void update() {

		// check keys
		handleInput();

	}

	public void draw(Graphics2D g) {

		// draw bg
		g.setColor(new Color(234, 153, 30));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Plants vs Zombies", WIDTH/2-140, 200);

		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Start", WIDTH/2-40, 300);
		g.drawString("Quit", WIDTH/2-40, 400);
	}

	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.PLAYSTATE);
		}
		else if(currentChoice == 1) {
			System.exit(0);
		}
	}

	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				currentChoice++;
			}
		}
	}

	@Override
	public void released() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressed() {
		// TODO Auto-generated method stub
		
	}

}




