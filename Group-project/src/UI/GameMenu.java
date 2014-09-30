package UI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import networking.Player;
import GameWorld.GameCharacter;

/**
 * Game UI Features:
 * 1.) A Map
 * 2.) Navigation
 * 3.) Combat
 * 4.) Player's Statistics and Inventory
 */

//The "Board" of the game (and all of it's fields)
//The list of players in the game. 
//The current player using this particular frame

public class GameMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private GameFrame currentGame;
	
//	private Board board;	
//	private int uid;
//	private ActionListener player; //changed to ActionListener, add this to all of your buttons
//									//you don't need to make your own button listener, we will pass in one
//	private Board board;	
//	private int uid;
	private ActionListener player; //changed to ActionListener, add this to all of your buttons
//									//you don't need to make your own button listener, we will pass in one
	/**
	 * This is the constructor for the JFrame
	 * @param board 
	 * @param player 
	 * @param uid 
	 */
	
	
	
	public GameMenu(GameFrame game, ActionListener player){  
		
		//this.board = board;
		//this.uid = uid;
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
		this.add(new MapPanel());
		this.add(new NavigationPanel());
		this.add(new CombatPanel());
		this.add(new StatsPanel());	
		this.setAlignmentX(BOTTOM_ALIGNMENT);		
	}
	
	/**
	 * This class is for the Map for the player
	 * @author adreledeguzman-
	 */
	
	public class MapPanel extends JPanel{
		private JPanel Map;
		private JLabel name;
		
		public MapPanel(){
		    name = new JLabel("Player Something", JLabel.CENTER);
		    JLabel mapName = new JLabel("Map", JLabel.CENTER);
		    
			Map = new JPanel();
			Map.setPreferredSize(new Dimension(150,150));
			Map.setBackground(Color.black);			
			
			this.add(mapName);
			this.add(Map);
			this.setBackground(Color.LIGHT_GRAY);
			this.setPreferredSize(new Dimension(200,200));
			this.add(name);		
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

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
			//ButtonListener b = new ButtonListener();
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
					System.out.println("Attack Player 4");
					currentGame.showInstructions();
					currentGame.setVisible(true);
					currentGame.repaint();
				}	
			}
		}	
	}
	

	
	
	
	/**
	 * This is the Inventory and Health Menu
	 * 
	 */
	
	public class StatsPanel extends JPanel{		
		//Actions Listener for the buttons		
		private JPanel Health;
		private JPanel Inventory;
		
		public StatsPanel(){
		    JLabel label1 = new JLabel("Health", JLabel.CENTER);
			JLabel label2 = new JLabel("Inventory", JLabel.CENTER);
			
			Health = new JPanel();
			Health.setPreferredSize(new Dimension(350,90));
			Health.setBackground(Color.gray);
			Health.add(label1);
		   
			Inventory = new JPanel();
			Inventory.setPreferredSize(new Dimension(350,90));
			Inventory.setBackground(Color.DARK_GRAY);
			Inventory.add(label2);
		
			this.setPreferredSize(new Dimension(370,200));
			this.setBackground(Color.LIGHT_GRAY);
			this.add(Health);
			this.add(Inventory);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

			
		}
	}

	

}
