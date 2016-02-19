package main;

import java.awt.Graphics2D;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);

	public void newGame() {
	}

	public abstract void released();

	public abstract void pressed();

	public void rightClick() {
		// TODO Auto-generated method stub
		
	}
	
}
