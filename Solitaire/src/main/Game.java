package main;

import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;

public class Game {
	
	public static void main(String[] args) {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		new Frame("Solitaire");
	}

}
