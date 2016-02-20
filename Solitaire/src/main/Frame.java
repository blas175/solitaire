package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Frame extends JFrame {

	private static final long serialVersionUID = -3449025957650798178L;

	private JMenuBar menuBar = new JMenuBar();
	private JMenu gameMenu = new JMenu("Partie");

	private JMenuItem newGameItem = new JMenuItem("Nouvelle Partie");
	private JMenuItem quitItem = new JMenuItem("Quitter");

	private GamePanel gamePanel;
	
	public Frame(String title) {
		super(title);
		
		gamePanel = new GamePanel(this);

		gameMenu.add(newGameItem);
		gameMenu.add(quitItem);
		menuBar.add(gameMenu);
		setJMenuBar(menuBar);
		
		quitItem.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){
		        System.exit(0);
		      }
		    });
		
		newGameItem.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){
		    	  gamePanel.newGame();
		      }
		    });
		add(gamePanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
