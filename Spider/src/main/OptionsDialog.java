package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionsDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 8908554507307653238L;
	private JRadioButton color1;
	private JRadioButton color2;
	private JRadioButton color4;
	private JButton bOk = new JButton("OK"), bCancel = new JButton("Cancel");
	private int level;
	private ButtonGroup bg;

	public OptionsDialog(JFrame parent, String title, boolean modal, int level) {
		super(parent, title, modal);
		this.level = level;
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.initComponents();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private void initComponents() {
		JPanel panLevel = new JPanel();
		panLevel.setLayout(new BoxLayout(panLevel, getDefaultCloseOperation()));
		panLevel.setBorder(BorderFactory.createTitledBorder("Difficulté"));
		color1 = new JRadioButton("1 couleur");
		color1.setSelected((level == 1) ? true : false);
		color2 = new JRadioButton("2 couleurs");
		color2.setSelected((level == 2) ? true : false);
		color4 = new JRadioButton("4 couleurs");
		color4.setSelected((level == 4) ? true : false);
		bg = new ButtonGroup();
		bg.add(color1);
		bg.add(color2);
		bg.add(color4);
		panLevel.add(color1);
		panLevel.add(color2);
		panLevel.add(color4);

		JPanel panButtons = new JPanel();
		panButtons.add(bOk);
		panButtons.add(bCancel);

		getContentPane().add(panLevel, BorderLayout.CENTER);
		getContentPane().add(panButtons, BorderLayout.SOUTH);

		bOk.addActionListener(this);
		bCancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bOk) {
			level = (color1.isSelected()) ? 1 : (color2.isSelected()) ? 2 : (color4.isSelected()) ? 4 : 1;
			setVisible(false);
		} else if (e.getSource() == bCancel) {
			setVisible(false);
		}

	}

	public int getLevel() {
		return level;
	}
}
