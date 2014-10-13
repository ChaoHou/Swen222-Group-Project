package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
	

public class GameOverScreen extends PopUpScreen {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton backMainMenu;
	private boolean playerWon = false;


	public GameOverScreen(String name, GameFrame game, Boolean playerWon){	
		
		super(name, game);		
		//Buttons
		backMainMenu = new JButton("To Main Menu");
		//Action Listeners for buttons		
		//ButtonListener b = new ButtonListener();
		backMainMenu.addActionListener(getGame().getPlayer());		
		getGame().getScreenButtons().put(backMainMenu, "backMainMenu");
		try {
			if(playerWon == true){
				img = ImageIO.read(new File("src/victory.jpg"));
			}
			else{
				img = ImageIO.read(new File("src/gameover.jpg"));
			}	
		} 
		catch(IOException e) {		
		}
		
		//Setting up the outmost Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setBackground(Color.black);
		//Game over screen
		JLabel title = new JLabel("GAME OVER", JLabel.CENTER);
		title.setPreferredSize(new Dimension(1000, 40));
		title.setForeground(Color.white);
		this.add(title);
		this.add(backMainMenu);
		this.remove(getBackToGame());
		this.repaint();
		
	}

	public boolean isPlayerWon() {
		return playerWon;
	}

	public void setPlayerWon(boolean playerWon) {
		this.playerWon = playerWon;
	}	

	
}