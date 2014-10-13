package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public GameOverScreen(String name, GameFrame game){	
		
		super(name, game);		
		//Buttons
		backMainMenu = new JButton("To Main Menu");
		//Action Listeners for buttons		
		//ButtonListener b = new ButtonListener();
		backMainMenu.addActionListener(getGame().getPlayer());	
		
		
		getGame().getScreenButtons().put(backMainMenu, "backMainMenu");

		
		
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


	//Actions Listener for the buttons		
//	private class ButtonListener implements ActionListener{
//		public void actionPerformed(ActionEvent event){
//			if(event.getSource() == backMainMenu){
//
//				//You're going to need to get rid of all the panels:
//				getGame().remove(getGame().getPanels().get("game"));
//				getGame().remove(getGame().getPanels().get("gameover"));
//				
//				//Go to Main Menu
//				getGame().showMainMenu();
//				getGame().setRunningGame(true);
//				
//				getGame().repaint();
//				getGame().setVisible(true);
//				
//			}
//
//		}
//	}	

	

	
}