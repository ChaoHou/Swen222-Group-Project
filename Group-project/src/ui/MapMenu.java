package ui;

import gameworld.Room;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MapMenu extends JPanel {	
	private JButton backMap;
	private JPanel mapPanel;
	private GameFrame game;
	//Rooms to draw
	private Room[][] rooms;
	
	public MapMenu(GameFrame game){	
		this.game = game;	
		rooms = this.game.getBoard().getRooms();
	    JLabel label1 = new JLabel("Map Screen", JLabel.CENTER);
		//Buttons
		backMap = new JButton("Go Back");
		//Action Listeners for buttons		
		ButtonListener b = new ButtonListener();
		backMap.addActionListener(b);
		//Setting up the mapPanel
		mapPanel = new JPanel();
		mapPanel.add(label1);
		mapPanel.setPreferredSize(new Dimension(300,200));
		mapPanel.setBackground(Color.gray);
		mapPanel.add(backMap);
	
		//Setting up the outmost Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setPreferredSize(new Dimension(500,500));
		this.setBackground(Color.LIGHT_GRAY);
		this.add(mapPanel);	
		this.repaint();
	}	
	



	//Actions Listener for the buttons		
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == backMap){
				//Remove the map screen
				game.getPanels().remove("map");
				//Check if you're in a current game or not
				if(game.isRunningGame()){
					game.showGame();
					game.repaint();
					game.setVisible(true);
				}
				else{	
					game.setMainMenu();
					game.repaint();
					game.setVisible(true);
				}
			}

		}
	}	

	
	
	public void redraw(Graphics g) {		
		//Draw each room
		//If the room is valid, color green
		//If a player is there, draw a dot
		//If the werewolf's there, draw an x (for now)
		
		for(int i =0; i< 5; i++){
			for(int j=0; j<5; j++){
				//Color non-valid rooms black
				g.setColor(Color.black);
				g.fillRect(i*20, j*20, 20, 20);
				//Color rooms green
				if((i==0 && j==0) && (i==2 && j==0)){
					g.setColor(Color.green);
					g.fillRect(i*20, j*20, 20, 20);
				}
				//Represent the vampires
				if(!rooms[i][j].getVamps().isEmpty()){
					rooms[i][j].getVamps().contains(game.getUid());
					g.setColor(Color.red);
					g.fillOval(i*20, j*20, 20, 20);
				}
				//Draw the werewolf
				if(rooms[i][j].getWerewolf() !=null)
				g.setColor(Color.black);
				g.fillOval(i*20, j*20, 20, 20);
			}
			
		
		}

		}
	

}
