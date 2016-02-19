package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

public class Mouse implements MouseListener, MouseMotionListener {

	public static int x;
	public static int y;
	private static boolean pressed;
	private GameStateManager stateManager;

	public Mouse(GameStateManager stateManager) {
		this.stateManager = stateManager;
	}

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)){
			stateManager.gameStates[stateManager.currentState].pressed();
			pressed = true;
		}
		else{
			stateManager.gameStates[stateManager.currentState].rightClick();
		}
	}

	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)){
			stateManager.gameStates[stateManager.currentState].released();
		}
		pressed = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		Mouse.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		Mouse.y = y;
	}

	public static boolean isPressed() {
		return pressed;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
