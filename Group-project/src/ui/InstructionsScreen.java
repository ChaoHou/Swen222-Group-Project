package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InstructionsScreen extends PopUpScreen{
	/**
	 * This is the instructions menu for the game. It can be opened:
	 * -Any time playing the game
	 * -On the main menu 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel instructionsPanel;
	
	public InstructionsScreen(String name, GameFrame game){	
		super(name, game);
		 	
	    JLabel label1 = new JLabel("There are instructions here, I swear", JLabel.CENTER);
		//Setting up the directionalPanel
		instructionsPanel = new JPanel();
		instructionsPanel.add(label1);
		instructionsPanel.setPreferredSize(new Dimension(300,200));
		instructionsPanel.setBackground(Color.gray);
		instructionsPanel.add(getBackToGame());
		
		
		//Setting up the outmost Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setPreferredSize(new Dimension(500,500));
		this.setBackground(Color.LIGHT_GRAY);
		this.add(instructionsPanel);	
	}	
	
}
