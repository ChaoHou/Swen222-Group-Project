package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Through our game, there will be popups that obscure the player's view of the game
 * These are done in situations whereby the player must perform an action, such as looking in a map.
 * These are the characteristics of a popup menu:
 * 1.) Disable the gamemenu's buttons
 * 2.) Hide the rendering screen (The "view" of the player in the room) 
 * 
 * @author Raul John Immanuel De Guzman-
 * ID: 300269955
 *
 */

public abstract class PopUpScreen extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//There's a back button
	private JButton backToGame;
	//You will always to use the GameFrame
	private GameFrame game;
	private String name;

	protected BufferedImage img;	 
	public PopUpScreen(String name, GameFrame game){	
			this.name = name;
			this.game = game;	
			setBackToGame(new JButton("Go Back"));
			getBackToGame().addActionListener(getGame().getPlayer());				
			game.getScreenButtons().put(backToGame, "backToGame");
			//For making the panel look nice..
			try {
				img = ImageIO.read(new File("src/blur.png"));
			} catch(IOException e) {		
			}
			
			this.setPreferredSize(new Dimension(1000, 500));
			this.setBackground(Color.white);
			this.add(getBackToGame());
			this.repaint();
			
		}	

		public JButton getBackToGame() {
			return backToGame;
		}

		public void setBackToGame(JButton backToGame) {
			this.backToGame = backToGame;
		}

		public GameFrame getGame() {
			return game;
		}

		public void setGame(GameFrame game) {
			this.game = game;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}

}
