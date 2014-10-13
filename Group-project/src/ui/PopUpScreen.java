package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Through our game, there will be popups that obscure the player's view of the game
 * These are done in situations whereby the player must perform an action, such as looking in a map.
 * These are the characteristics of a popup menu:
 * 1.) Disable the gamemenu's buttons
 * 2.) Hide the rendering screen (The "view" of the player in the room) 
 * 
 * @author John De Guzman-
 *
 */

public abstract class PopUpScreen extends JPanel{
	//There's a back button
	private JButton backToGame;
	//You will always to use the GameFrame
	private GameFrame game;
	private String name;
	private PopUpScreen tmp = this;
	

	public PopUpScreen(String name, GameFrame game){	
			this.name = name;
			this.game = game;	
			setBackToGame(new JButton("Go Back"));
			//ButtonListener b = new ButtonListener();
			getBackToGame().addActionListener(getGame().getPlayer());		
			
			game.getScreenButtons().put(backToGame, "backToGame");

			
			
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
		
		//Actions Listener for the buttons		
//		private class ButtonListener implements ActionListener{
//			public void actionPerformed(ActionEvent event){
//				if(event.getSource() == getBackToGame()){	    			
//					game.showGame(tmp);
//					game.repaint();
//					game.setVisible(true);	
//				}
//				
//			}
//		}	
	

}
