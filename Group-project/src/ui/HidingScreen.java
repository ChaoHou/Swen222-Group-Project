package ui;

import gameworld.Furniture;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HidingScreen extends PopUpScreen {
	//private Furniture furniture;
	private JButton getOut;

	public HidingScreen(String name, GameFrame game) {		
		super(name, game);
		//this.furniture = furniture;		

		getOut = new JButton("Get out");
 		getOut.addActionListener(getGame().getPlayer());	
		game.getScreenButtons().put(getOut, "getOut");
		
		//Game over screen
		JLabel title = new JLabel("You're hiding inside furniture...", JLabel.CENTER);
		title.setPreferredSize(new Dimension(1000, 40));
		title.setForeground(Color.white);
		this.add(title);
		this.add(getOut);
		this.remove(getBackToGame());
		this.repaint();

	}
	
//	public void removePlayer(){
//		furniture.removePlayer(getGame().getBoard().getVamp(getGame().getUid()));
//		getGame().getBoard().getVamp(getGame().getUid()).setHiding(false);
//		
//	}
	
	

}
