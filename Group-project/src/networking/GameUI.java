package networking;

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

public class GameUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//This is just a temporary main method
	public static void main(String args []){
		GameUI g = new GameUI();
        g.setVisible(true);
	}
			
	/**
	 * This is the constructor for the JFrame
	 */
	
	public GameUI(){  
		
		
        //FlowLayout 
		FlowLayout fullMenu = new FlowLayout();
		fullMenu.setAlignment(100);				
		fullMenu.setHgap(0);		
		fullMenu.setAlignment(FlowLayout.CENTER);
					
		//Rendering panel
		JPanel render = new JPanel();
		render.setPreferredSize(new Dimension (1000, 500));
		render.setBackground(Color.white);
		
		//Everything is placed inside here
        JPanel outerPanel = new JPanel();	
		outerPanel.setLayout(fullMenu);	
		outerPanel.setPreferredSize(new Dimension(1000, 200));		
		outerPanel.add(new MapPanel());
		outerPanel.add(new NavigationPanel());
		outerPanel.add(new CombatPanel());
		outerPanel.add(new StatsPanel());	
		outerPanel.setAlignmentX(BOTTOM_ALIGNMENT);
		
		//Boxlayout
		//BoxLayout x = new BoxLayout(outerPanel, BoxLayout.X_AXIS);
		//outerPanel.setLayout(x);
		
	
	    //The JFrame Constructor:
		this.getContentPane().add(render);
	    this.getContentPane().add(outerPanel);
	    this.setTitle("Vampire Mansion");
		this.setSize(1000,740);
		this.setResizable(false);
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);   
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
			ButtonListener b = new ButtonListener();
			left.addActionListener(b);
			right.addActionListener(b);
			changeRoom.addActionListener(b);			
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
		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				if(event.getSource() == left){
					System.out.println("You moved Left");

				}
				else if(event.getSource() == right){
					System.out.println("You moved Right");

				}
				else if(event.getSource() == changeRoom){
					//Use room
					String answer = (String) JOptionPane.showInputDialog(null, "Which Room would you like to go to?", null, 
							 JOptionPane.PLAIN_MESSAGE, null, new String[]{ "d", "dd"}, null);
				
					System.out.println("You moved to Room: " + answer );
				}	
			}
		}	
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
			
			setTitle("Box Layout Example");
	        setSize(150, 150);
	        getContentPane().setLayout(
	        new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			this.setAlignmentX(Component.BOTTOM_ALIGNMENT);
			
			this.setPreferredSize(new Dimension(370,200));
			this.setBackground(Color.LIGHT_GRAY);
			this.add(Health);
			this.add(Inventory);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

			
		}
	}

	

}