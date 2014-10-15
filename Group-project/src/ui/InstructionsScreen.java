package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
	 * This is the instructions menu for the game. It can be opened:
	 * -Any time playing the game
	 * -On the main menu 
	 * 
	 * Created by: Raul John Immanuel De Guzman
	 * ID: 300269955
	 */

public class InstructionsScreen extends PopUpScreen{
	
	
	private static final long serialVersionUID = 1L;
	private BufferedImage img;

	
	public InstructionsScreen(String name, GameFrame game){	
		super(name, game);
		 	
		try {
			img = ImageIO.read(new File("src/instructions.png"));
		} catch(IOException e) {		
		}
	    
		JLabel label1 = new JLabel("", JLabel.CENTER);
        label1.setIcon(new ImageIcon(img));
		
        this.remove(getBackToGame());

		//Setting up the outmost Panel
		this.add(label1);
		this.setPreferredSize(new Dimension(1000,420));
		this.setBackground(Color.black);

		this.add(getBackToGame());
		
		this.repaint();
		
	}	
	
}
