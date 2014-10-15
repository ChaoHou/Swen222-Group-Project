package ui;

import gameworld.Furniture;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * This class represents a Jpanel whereby a player is hiding in furniture.
 * When you click the button getOut, the board's info updates.
 * -The player is no longer hidden
 * -The Furniture he was in no longer has anyone
 * 
 * 
 * @author Raul John Immanuel De Guzman-
 * ID: 300269955
 *
 */

public class HidingScreen extends PopUpScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Furniture furniture;
	private JButton getOut;

	public HidingScreen(String name, GameFrame game) {		
		super(name, game);
		this.furniture = furniture;		

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
//		
//		getGame().getBoard().getVamp(getGame().getUid()).setHiding(false);
//		
//	}
	
	

}
