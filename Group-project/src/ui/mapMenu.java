package ui;

import gameworld.Room;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class mapMenu extends JPanel {	
	private JButton backMap;
	private JPanel mapDescription;
	private GameFrame game;
	private mapPicture map;
	//Rooms to draw
	private Room[][] rooms;

	public mapMenu(GameFrame game){		
		setMap(new mapPicture());		
		this.game = game;	
		rooms = this.game.getBoard().getRooms();	
		//Buttons
		backMap = new JButton("Go Back");
		//Action Listeners for buttons		
		ButtonListener b = new ButtonListener();
		backMap.addActionListener(b);	
		//Setting up the mapDescription
		JLabel label1 = new JLabel(
				"<html>   Legend:"
				+ " <br> Vampires = Red "
				+ " <br> Werewolf = Gray"
				+ " </html>", JLabel.CENTER);
		
		mapDescription = new JPanel();	
		mapDescription.add(label1);
		mapDescription.setPreferredSize(new Dimension(200,200));
		mapDescription.setBackground(Color.gray);
		mapDescription.add(backMap);

		//Setting up the outmost Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setBackground(Color.black);

		JLabel title = new JLabel("HURRY UP, THE WEREWOLF'S GONNA GET YOU", JLabel.CENTER);
		title.setPreferredSize(new Dimension(1000, 40));
		title.setForeground(Color.white);
		this.add(title);
		
		this.add(getMap());
	    this.add(mapDescription);	
		this.repaint();
	    
	}	

	public mapPicture getMap() {
		return map;
	}





	public void setMap(mapPicture map) {
		this.map = map;
	}

	//Actions Listener for the buttons		
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == backMap){
				//Remove the map screen
				game.showGame();
				game.repaint();
				game.setVisible(true);
				
			}

		}
	}	

	

	
	
	/**
	 * This class represents the map itself
	 * It's separate from the mapMenu,
	 * so that drawing onto the map will be easier
	 * @author John De Guzman
	 *
	 */
	
	public class mapPicture extends JLabel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public mapPicture(){
			super();		
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("src/map.png"));	
			} catch (IOException e) {
			}	
			this.setIcon(new ImageIcon(img));
	        this.setLayout(new FlowLayout());		
		}
		
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			redraw(g);
		}    

		public void redraw(Graphics g) {		
			//Draw the player and werewolf on the map
			for(int i =1; i< 5; i++){
				for(int j=1; j<5; j++){
					if(rooms[j-1][i-1] !=null){
						//If a room has a vampire, draw him
						if(!rooms[j-1][i-1].getVamps().isEmpty()){		
							g.setColor(Color.red);
							g.fillOval(i*100-40, j*100-50, 20, 20);
						}
						if(rooms[j-1][i-1].getWerewolf() != null){
							g.setColor(Color.gray);
							g.fillOval(i*100-80, j*100-50, 20, 20);
						}
					}				

				}
			}
		}
	}
}


