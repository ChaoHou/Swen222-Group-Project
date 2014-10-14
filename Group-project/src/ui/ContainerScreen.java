package ui;

import gameworld.Collectable;
import gameworld.Container;
import gameworld.HealthPack;
import gameworld.Orb;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class represents the "Trading screen"
 * Basically this happens when a player clicked a container and wants get some stuff.
 * He may also put stuff into the container, as a choice to hide stuff from other players.
 * 
 * Created by: Raul John Immanuel De Guzman
 * ID: 300269955
 */


public class ContainerScreen extends PopUpScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton placeToInventory, placeToContainer;	
	private JLabel nameInventory, nameContainer;
	private JPanel tradingScreen, containerPanel, inventoryPanel;
	private Container container;
	private List<JCheckBox> checkContainer = new ArrayList<JCheckBox>();
	private List<JCheckBox> checkInventory = new ArrayList<JCheckBox>();


	public ContainerScreen(String name, GameFrame game, Container c){
		super(name, game);
		this.setContainer(c);	
		//Buttons
		placeToInventory = new JButton("Put selected items into Inventory");
		placeToContainer = new JButton("Put selected items into container");
 		placeToInventory.addActionListener(getGame().getPlayer());
		placeToContainer.addActionListener(getGame().getPlayer());		
		game.getScreenButtons().put(placeToInventory, "placeToInventory");
		game.getScreenButtons().put(placeToContainer, "placeToContainer");

		//Draw the container's info into a panel:
		containerPanel = new JPanel();
		nameContainer = new JLabel("The container", JLabel.CENTER);
		nameContainer.setForeground(Color.white);
		nameContainer.setPreferredSize(new Dimension(300, 50));
		containerPanel.add(nameContainer);
		updateContainer();
		
		//Draw the player's inventory:
		inventoryPanel = new JPanel();
		nameInventory = new JLabel("Your inventory", JLabel.CENTER);
		nameInventory.setForeground(Color.white);
		nameInventory.setPreferredSize(new Dimension(300, 50));
		inventoryPanel.add(nameInventory);
		updateInventory();
		
		//Filler in the middle of the screen
		tradingScreen = new JPanel();
		tradingScreen.setPreferredSize(new Dimension(200, 40));
		tradingScreen.setBackground(Color.black);
		tradingScreen.add(getBackToGame());
		
		//All into the panel itself
		this.setPreferredSize(new Dimension(1000, 300));
		this.setBackground(Color.black);
		this.add(containerPanel);
		this.add(tradingScreen);
		this.add(inventoryPanel);
	}


	public void updateContainer(){
		getCheckContainer().clear();
		containerPanel.removeAll();	
		containerPanel.setPreferredSize(new Dimension(300,500));
		containerPanel.setBackground(Color.black);
		containerPanel.add(nameContainer); 

		JCheckBox obj = null;
		for(Collectable c : getContainer().getItems()){
			//If Orb:
			if(c instanceof Orb){
				obj = new JCheckBox(Integer.toString(((Orb) c).getColor()));
				obj.setIcon(new ItemIcon(c, false));		
				obj.setSelectedIcon(new ItemIcon(c, true));						
				obj.setBorder(null);	
				getCheckContainer().add(obj);
				containerPanel.add(obj);				
			}
			//If HealthPotion:
			else if (c instanceof HealthPack){
				obj = new JCheckBox("");
				obj.setIcon(new ItemIcon(c, false));
				obj.setSelectedIcon(new ItemIcon(c, true));		
				obj.setBorder(null);
				getCheckContainer().add(obj);
				containerPanel.add(obj);
			}
		}		
		
		containerPanel.add(placeToInventory);
		containerPanel.repaint();
		this.repaint();
	    updateUI();

	}

	public void updateInventory(){
		getCheckInventory().clear();
		inventoryPanel.removeAll();
		inventoryPanel.setPreferredSize(new Dimension(300,500));
		inventoryPanel.setBackground(Color.black);
		inventoryPanel.add(nameInventory); 
		JCheckBox obj = null;
		for(Collectable c : getGame().getBoard().getVamp(getGame().getUid()).getInventory()){
			//If Orb:
			if(c instanceof Orb){
				obj = new JCheckBox(Integer.toString(((Orb) c).getColor()));
				obj.setIcon(new ItemIcon(c, false));		
				obj.setSelectedIcon(new ItemIcon(c, true));						
				obj.setBorder(null);	
				getCheckInventory().add(obj);
				inventoryPanel.add(obj);
			}
			//If HealthPotion:
			else if (c instanceof HealthPack){
				obj = new JCheckBox("");
				obj.setIcon(new ItemIcon(c, false));
				obj.setSelectedIcon(new ItemIcon(c, true));		
				obj.setBorder(null);
				getCheckInventory().add(obj);
				inventoryPanel.add(obj);
			}
		}			
		inventoryPanel.add(placeToContainer);	
		this.repaint();
		inventoryPanel.repaint();
	    updateUI();

	}

	public List<JCheckBox> getCheckContainer() {
		return checkContainer;
	}


	public void setCheckContainer(List<JCheckBox> checkContainer) {
		this.checkContainer = checkContainer;
	}

	public Container getContainer() {
		return container;
	}


	public void setContainer(Container container) {
		this.container = container;
	}

	public List<JCheckBox> getCheckInventory() {
		return checkInventory;
	}


	public void setCheckInventory(List<JCheckBox> checkInventory) {
		this.checkInventory = checkInventory;
	}


}		




	
