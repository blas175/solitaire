package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{

	// dimensions
	public static int WIDTH = 620;
	public static int HEIGHT = 620;

	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// image
	private BufferedImage image;
	private Graphics2D g;

	// game state manager
	private GameStateManager gsm;
	private JFrame frame;

	public GamePanel(JFrame frame) {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		this.frame = frame;
	}

	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		RenderingHints qualityHints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON );
		qualityHints.put(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY );
		g.setRenderingHints( qualityHints );

		running = true;

		gsm = new GameStateManager(this);
		addKeyListener(this);
		Mouse mouse = new Mouse(gsm);
		addMouseListener(mouse);	
		addMouseMotionListener(mouse);
	}

	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while(running) {
			start = System.nanoTime();

			update();
			draw();
			Graphics g2 = getGraphics();
			g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
			g2.dispose();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;

			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		gsm.update();
		Keys.update();
	}
	private void draw() {
		gsm.draw(g);
	}

	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

	
	public JFrame getFrame() {
		return frame;
	}

	public void newGame() {
		gsm.getState().newGame("");

	}

	public void optionsDialog() {
		gsm.getState().optionsDialog();
		
	}
}