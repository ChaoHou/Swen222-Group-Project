package ui;

import gameworld.Container;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class represents the "Trading screen"
 * Basically this happens in two scenarios:
 * 1.) Two players asked to trade each other
 * 2.) A player clicked a container and wants get some stuff.
 * 
 */


public class containerMenu extends JPanel {
	private JButton back;
	private JPanel tradingScreen;
	private GameFrame game;
	
	public containerMenu(GameFrame game, Container c){
		//Get the game and container
		
		
		
		BufferedImage img = null;
		BufferedImage img2 = null;
		try {
			img = ImageIO.read(new File("src/mapSmall.png"));
			img = ImageIO.read(new File("src/newgame1.png"));
		} catch (IOException e) {
		}
		
		ImageIcon x = new ImageIcon(img);
		ImageIcon y = new ImageIcon(img2);
		
		
		
		
	}
	
	

}
