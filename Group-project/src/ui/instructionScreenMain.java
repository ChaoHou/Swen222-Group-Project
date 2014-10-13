package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class instructionScreenMain extends JPanel {
	//There's a back button
	private JButton backToGame;
	//You will always to use the GameFrame
	private GameFrame game;
	private instructionScreenMain tmp = this;
	

	public instructionScreenMain(GameFrame game){	
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
		
		
		//Actions Listener for the buttons		
		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				if(event.getSource() == getBackToGame()){	    			
					//game.showGame(tmp);
					game.repaint();
					game.setVisible(true);	
				}
				
			}
		}	
	
}
