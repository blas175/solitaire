package main;

public class GameStateManager {

	GameState[] gameStates;
	int currentState;

	private GamePanel panel;

	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;

	public GameStateManager(GamePanel panel) {
		this.panel = panel;
		gameStates = new GameState[2];

		currentState = PLAYSTATE;
		loadState(currentState);

	}

	private void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if (state == PLAYSTATE)
			gameStates[state] = new PlayState(this, panel);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public GameState getState() {
		return gameStates[currentState];
	}

	public void update() {
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	public void draw(java.awt.Graphics2D g) {
		if (gameStates[currentState] != null)
			gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

}