package ui;

import gameworld.Collectable;
import gameworld.HealthPack;
import gameworld.Orb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import networking.Player;

/**
 * Game UI Features:
 * 1.) A Map
 * 2.) Navigation
 * 3.) Combat
 * 4.) Player's Statistics and Inventory
 */


public class GameMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private GameFrame currentGame;	
	private ActionListener player; 
	
	
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();

	
	public GameMenu(GameFrame game, ActionListener player){  	
		this.currentGame = game;
		this.player = player;
		
        //FlowLayout 
		FlowLayout fullMenu = new FlowLayout();
		fullMenu.setAlignment(100);				
		fullMenu.setHgap(0);		
		fullMenu.setAlignment(FlowLayout.CENTER);
		
		//Everything is placed inside here
		this.setLayout(fullMenu);	
		this.setPreferredSize(new Dimension(1000, 200));	
		
		StatsPanel x = new StatsPanel();
		this.getPanels().put("stats", x);
		this.add(new MapPanel());
		this.add(new NavigationPanel());
		this.add(new CombatPanel());
		this.add(x);	
		
		
		this.setAlignmentX(BOTTOM_ALIGNMENT);		
	}
	

	public Map<String, JPanel> getPanels() {
		return panels;
	}





	public void setPanels(Map<String, JPanel> panels) {
		this.panels = panels;
	}


	/**
	 * This class is for the Map for the player
	 * @author adreledeguzman-
	 */
	
	public class MapPanel extends JPanel{
		private JButton bigMap;
		
		public MapPanel(){
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("src/mapSmall.png"));
			} catch (IOException e) {
			}
			//Button
		    JLabel mapName = new JLabel("Map", JLabel.CENTER);
		    bigMap = new JButton();
			bigMap.setIcon(new ImageIcon(img));			
			ButtonListener b = new ButtonListener();
			//Action Listeners for buttons
			bigMap.addActionListener(b);
			this.add(mapName);
			this.add(bigMap);
			this.setBackground(Color.LIGHT_GRAY);
			this.setPreferredSize(new Dimension(200,200));
 			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
		
		//Actions Listener for the buttons		
				private class ButtonListener implements ActionListener{
					public void actionPerformed(ActionEvent event){
						if(event.getSource() == bigMap){
							currentGame.showMap();
							currentGame.setVisible(true);
						    updateUI();
						}						
					}
				}	
	
	}

	
	
	/**
	 * This class is for the Navigation buttons for the game screen.
	 * There are three buttons: 
	 * 1.) Move left
	 * 2.) Move right
	 * 3.) Change Room
	 * 
	 * @author Raul John De Guzman
	 */

	public class NavigationPanel extends JPanel{
		private JButton left, right, changeRoom;
		private JPanel directionPanel;
		private JPanel roomPanel;
		private NavigationPanel y = this;
		
		public NavigationPanel(){			
		    JLabel label1 = new JLabel("Turn Around", JLabel.CENTER);
		    JLabel label2 = new JLabel("Change Room", JLabel.CENTER);
			//Buttons
			left = new JButton("Turn Left");
			right = new JButton("Turn Right");
			changeRoom = new JButton("Change Room");
			//Action Listeners for buttons		
			left.addActionListener(player);
			right.addActionListener(player);
			changeRoom.addActionListener(player);			
			//Setting up the directionalPanel
			directionPanel = new JPanel();
			directionPanel.add(label1);
			directionPanel.setPreferredSize(new Dimension(180,90));
			directionPanel.setBackground(Color.gray);
			directionPanel.add(left);
			directionPanel.add(right);
			//Setting up the roomPanel
			roomPanel = new JPanel();
			roomPanel.add(label2);
			roomPanel.setPreferredSize(new Dimension(180,90));
			roomPanel.setBackground(Color.DARK_GRAY);
			roomPanel.add(changeRoom);		
			//Setting up the outmost Panel
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			this.setPreferredSize(new Dimension(200,200));
			this.setBackground(Color.LIGHT_GRAY);
			this.add(directionPanel);
			this.add(roomPanel);
		}	
		//Actions Listener for the buttons		
	}
	
	

	/**
	 * This is the Combat panel
	 * 
	 */
	
	public class CombatPanel extends JPanel{
		private JButton p1, p2, p3, p4;
		private JPanel buttonPanel;
		private JPanel normalCombat;
		private JPanel surpriseCombat;

		public CombatPanel(){
		    JLabel label1 = new JLabel("Combat", JLabel.CENTER);
			//Buttons
			p1 = new JButton("P1");			
			p2 = new JButton("P2");
			p3 = new JButton("P3");
			p4 = new JButton("P4");
			ButtonListener b = new ButtonListener();
			//Action Listeners for buttons
			p1.addActionListener(b);
			p2.addActionListener(b);
			p3.addActionListener(b);
			p4.addActionListener(b);
			//Setting up the Panel
			buttonPanel = new JPanel();
			buttonPanel.setPreferredSize(new Dimension(150,150));
			buttonPanel.setBackground(Color.black);
			buttonPanel.add(p1);
			buttonPanel.add(p2);
			buttonPanel.add(p3);
			buttonPanel.add(p4);
			//Setting The outmost stuff
			this.setPreferredSize(new Dimension(200,200));
			this.setBackground(Color.LIGHT_GRAY);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			this.add(label1);
			this.add(buttonPanel);
		}	
		//Actions Listener for the buttons		
		private class ButtonListener implements ActionListener{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent event){
				if(event.getSource() == p1){
					String answer = (String) JOptionPane.showInputDialog(null, "You're now facing Player 1", null, 
							 JOptionPane.PLAIN_MESSAGE, null, new String[]{ "Fight", "Trade"}, null);
					System.out.println("Attack Player 1?");

				}
				else if(event.getSource() == p2){
					System.out.println("Attack Player 2");
					p1.setBackground(Color.white);
					p1.setLabel("P1 is Occupied");

				}
				else if(event.getSource() == p3){
					//Use room
					System.out.println("Attack Player 3");
					p1.setLabel("P1");
				}	
				else if(event.getSource() == p4){
					//Use room
					currentGame.showInstructions();
					currentGame.setVisible(true);					
				    updateUI();
				}	
			}
		}	
	}
	

	
	
	
	/**
	 * This is the Inventory and Health Menu
	 * 
	 */
	
	public class StatsPanel extends JPanel{		
		//The two Panels:
		private JPanel Health;
		private JPanel Inventory;
		
		//Health:
		BufferedImage img = null;
		
		//Inventory:
				
		public StatsPanel(){
			//Hearth image
			try {
				img = ImageIO.read(new File("src/heart.png"));
			} catch (IOException e) {
			}		
			//Draw the player's health
			Health = new JPanel();
			updateHealth();
			//Draw the player's inventory: 
			Inventory = new JPanel();
			updateInventory();
			//Set up the whole Panel now:
			this.setPreferredSize(new Dimension(370,200));
			this.setBackground(Color.LIGHT_GRAY);
			this.add(Health);
			this.add(Inventory);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
		
		public void updateHealth(){	
			//This sets up the Hearts
			this.Health.removeAll();
			JLabel label1 = new JLabel("<html>Health <br/> </html>", JLabel.CENTER);
		    label1.setPreferredSize(new Dimension(350,20));
			Health.setPreferredSize(new Dimension(350,90));
			Health.setBackground(Color.gray);
			Health.add(label1);
			int health = currentGame.getBoard().getVamp(currentGame.getUid()).getHealth();
			System.out.println("Current health: " + health);
			for(int i = 0; i<health; i++){
				JLabel heart = new JLabel();
			    heart.setIcon(new ImageIcon(img));
				Health.add(heart);
			}
		}
		
		public void updateInventory(){	
			//This sets up the inventory buttons
			Inventory.removeAll();
			JLabel label2 = new JLabel("Inventory", JLabel.CENTER);
		    label2.setPreferredSize(new Dimension(350,20));	
		    Inventory.setPreferredSize(new Dimension(350,90));
			Inventory.setBackground(Color.DARK_GRAY);
			Inventory.add(label2);
			for(Collectable c : currentGame.getBoard().getVamp(currentGame.getUid()).getInventory()){
				//If Orb:
				if(c instanceof Orb){
					JButton item = new JButton("Orb");
					item.setIcon(new itemIcon(c));
					item.addActionListener(player);
			        item.setBorder(null);	
					Inventory.add(item);
				}
				//If healthpack:
				else if (c instanceof HealthPack){
					JButton item = new JButton("healthpack");
					item.setIcon(new itemIcon(c));
					//temp
					item.addActionListener(player);
			        item.setBorder(null);	
					Inventory.add(item);
				}
			}		
		}
		
		
		
		
	}
	
	
	public void enableButtons(){
		
	}
	public void disableButtons(){
		
	}


	

}
