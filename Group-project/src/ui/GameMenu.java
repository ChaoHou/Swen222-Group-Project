package ui;

import gameworld.Collectable;
import gameworld.HealthPack;
import gameworld.Orb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * This class is responsible for the controls and information of the player.
 * It is split into four specific JPanels that show:
 * 1.) A Map
 * - This is a small map, you need to click it to "open it up" and see where you are
 * - Clicking the Map opens up another JPanel, MapScreen 
 * 2.) Navigation
 * - This Panel is responsible for turning your character and entering other rooms.
 * 3.) Player's Statistics 
 * - This Panel is responsible for presenting a player's information, his health and inventory of items.
 * 4.) Message window
 * - This Panel is responsible for printing out messages to the player, such as where he's facing,
 *   If he got mugged by another player, etc.
 * 
 * Created by: Raul John Immanuel De Guzman
 * ID: 300269955
 * 
 */

public class GameMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private GameFrame currentGame;	
	private ActionListener player; 
	
	//List of panels that see constant change 
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();
	//List of all the buttons in GameMenu
	private Map<JButton, String> buttons = new HashMap<JButton, String>();
	
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
		//These stuff change constantly, so it's important to keep track of them
		StatsPanel x = new StatsPanel();
		textPanel y = new textPanel();
		this.getPanels().put("stats", x);
		this.getPanels().put("messages", y);
		this.add(new MapPanel());
		this.add(new NavigationPanel());
		//this.add(new CombatPanel());
		this.add(x);	
		this.add(y);	
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
	 * 
	 */
	
	public class MapPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton bigMap;
		
		public MapPanel(){
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("resources/images/UI/mapSmall.png"));
			} catch (IOException e) {
			}
			//Button
		    JLabel mapName = new JLabel("Map", JLabel.CENTER);
		    bigMap = new JButton();
			bigMap.setIcon(new ImageIcon(img));			
			//Action Listeners for buttons
			bigMap.addActionListener(currentGame.getPlayer());
			//Keep track of every button
			currentGame.getScreenButtons().put(bigMap, "bigMap");
			
			this.add(mapName);
			this.add(bigMap);
			this.setBackground(Color.LIGHT_GRAY);
			this.setPreferredSize(new Dimension(200,200));
 			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));	
			getButtons().put(bigMap, "bigMap");
		}
		
	
	}

	
	
	/**
	 * This class is for the Navigation buttons for the game screen.
	 * There are three buttons: 
	 * 1.) Move left
	 * 2.) Move right
	 * 3.) Change Room
	 * 
	 * 
	 */

	public class NavigationPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton left, right, changeRoom;
		private JPanel directionPanel;
		private JPanel roomPanel;
		
		public NavigationPanel(){			
			//Buttons...	
			
		    JLabel label1 = new JLabel("Turn Around", JLabel.CENTER);
		    label1.setPreferredSize(new Dimension(180, 20));
		    JLabel label2 = new JLabel("Change Room", JLabel.CENTER);
			//Buttons
			left = new JButton("Left");
			left.setPreferredSize(new Dimension(50, 50));
			right = new JButton("Right");
			right.setPreferredSize(new Dimension(50, 50));
			changeRoom = new JButton("Change Room");
			//Action Listeners for buttons		
			left.addActionListener(player);
			right.addActionListener(player);
			changeRoom.addActionListener(player);
			
			left.addKeyListener((KeyListener) player);
			right.addKeyListener((KeyListener) player);
			changeRoom.addKeyListener((KeyListener) player);
			
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
			
			getButtons().put(left, "left");
			getButtons().put(right, "right");
			getButtons().put(changeRoom, "changeRoom");



		}	
		//Actions Listener for the buttons		
	}
	
	
	
	/**
	 * This panel presents all the messages from the game.
	 * 
	 *
	 */

	public class textPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextArea messages = new JTextArea();
		
		public textPanel(){
		    JLabel label1 = new JLabel("Messages", JLabel.CENTER);
	     
	       	messages.setLineWrap(true);
	       	messages.setWrapStyleWord(true);
	       	messages.setEditable(false);    	
	       	
			//Setting The outmost stuff
			this.setPreferredSize(new Dimension(200,200));
			this.setBackground(Color.LIGHT_GRAY);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

			//Making messages scrollable:
			JScrollPane scrollPane = new JScrollPane(messages);
			scrollPane.setPreferredSize(new Dimension(150,150));
        	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);				
			
			this.add(label1);
			this.add(scrollPane);

			
		}

		public JTextArea getMessages() {
			return messages;
		}

		public void setMessages(JTextArea messages) {
			this.messages = messages;
		}	
		
		
		
	}
	
	
	/**
	 * This is the Inventory and Health Menu
	 * 
	 */
	
	public class StatsPanel extends JPanel{		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//The two Panels:
		private JPanel Health;
		private JPanel Inventory;
		//Health Icon:
		BufferedImage img = null;
		
		public StatsPanel(){
			//Hearth image
			try {
				img = ImageIO.read(new File("resources/images/UI/heart.png"));
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
					JButton item = new JButton();
					item.setIcon(new ItemIcon(c, false));
					item.addActionListener(player);
			        item.setBorder(null);	
					Inventory.add(item);
					getButtons().put(item, "Orb");
   				}
				//If healthpack:
				else if (c instanceof HealthPack){
					JButton item = new JButton();
					item.setIcon(new ItemIcon(c, false));
					//temp
					item.addActionListener(player);
			        item.setBorder(null);	
					Inventory.add(item);
					getButtons().put(item, "HealthPack");

				}
			}		
		}	
	}
	
	/**
	 * These methods enable and disable the buttons
	 */
	
	public void enableButtons(){
		for(JButton b : getButtons().keySet()){
			b.setEnabled(true);		
		}
		
	}
	public void disableButtons(){
		for(JButton b : getButtons().keySet()){
			b.setEnabled(false);		
		}
	}

	public Map<JButton, String> getButtons() {
		return buttons;
	}
	

	public void setButtons(Map<JButton, String> buttons) {
		this.buttons = buttons;
	}


	

	

}
