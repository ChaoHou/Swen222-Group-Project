package ui;

import gameworld.Room;

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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This screen opens up a map that draws information on the location of the player,
 * and the werewolf as well.
 * 
 * @author Raul John Immanuel De Guzman-
 * ID: 300269955
 *
 */


public class MapScreen extends PopUpScreen {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mapDescription;
	private mapPicture map;
	//Rooms to render
	private Room[][] rooms;

	public MapScreen(String name, GameFrame game){	
		super(name, game);
		setMap(new mapPicture());		
		rooms = this.getGame().getBoard().getRooms();	
		 
		//Setting up the mapDescription
		JLabel label1 = new JLabel(
				"<html> Legend: <br> "
				+ " <br> You = Red "
				+ " <br> Werewolf = Gray"
				+ " </html>", JLabel.CENTER);
		label1.setForeground(Color.white);
		
		mapDescription = new JPanel();	
		mapDescription.add(label1);
		mapDescription.setPreferredSize(new Dimension(200,200));
		mapDescription.setBackground(Color.black);
		mapDescription.add(getBackToGame());

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
				img = ImageIO.read(new File("resources/images/UI/map.png"));	
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
						//If a room has a vampire, render him
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


