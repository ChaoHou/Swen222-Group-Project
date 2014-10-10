package ui;

import gameworld.Collectable;
import gameworld.Container;
import gameworld.HealthPotion;
import gameworld.Orb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import ui.GameMenu.StatsPanel;

/**
 * This class represents the "Trading screen"
 * Basically this happens in two scenarios:
 * 1.) Two players asked to trade each other
 * 2.) A player clicked a container and wants get some stuff.
 * 
 */


public class containerMenu extends JPanel {
	private JButton backTrade, placeToInventory, placeToContainer;
	private JPanel tradingScreen, containerPanel, inventoryPanel;
	private GameFrame game;
	private Container container;
	private List<JCheckBox> checkContainer = new ArrayList<JCheckBox>();
	private List<JCheckBox> checkInventory = new ArrayList<JCheckBox>();


	public containerMenu(GameFrame game, Container c){
		this.game = game;
		this.container = c;	
		//Buttons
		backTrade = new JButton("Go Back");		
		placeToInventory = new JButton("Put selected items into Inventory");
		placeToContainer = new JButton("Put selected items into cointainer");
		ButtonListener b = new ButtonListener();
		backTrade.addActionListener(b);
		placeToInventory.addActionListener(b);
		placeToContainer.addActionListener(b);
		
		//Draw the container's info into a panel:
		containerPanel = new JPanel();
		updateContainer();
		//Draw the player's inventory:
		inventoryPanel = new JPanel();
		updateInventory();
		
		//Filler
		tradingScreen = new JPanel();
		tradingScreen.setPreferredSize(new Dimension(200, 40));
		tradingScreen.add(backTrade);
		
		//All into the panel itself
		this.setPreferredSize(new Dimension(1000, 300));
		this.setBackground(Color.GRAY);
		this.add(containerPanel);
		this.add(tradingScreen);
		this.add(inventoryPanel);
	}


	public void updateContainer(){
		checkContainer.clear();
		containerPanel.removeAll();	
		containerPanel.setPreferredSize(new Dimension(300,500));
		containerPanel.setBackground(Color.white);
		containerPanel.add(placeToContainer);
		
		JCheckBox obj = null;
		for(Collectable c : container.getItems()){
			//If Orb:
			if(c instanceof Orb){
				obj = new JCheckBox(Integer.toString(((Orb) c).getColor()));
				obj.setIcon(new itemIcon(c));		
				obj.setSelectedIcon(new itemIcon(new HealthPotion()));						
				obj.setBorder(null);	
				checkContainer.add(obj);
				containerPanel.add(obj);				
			}
			//If HealthPotion:
			else if (c instanceof HealthPotion){
				obj = new JCheckBox();
				obj.setIcon(new itemIcon(c));
				obj.setSelectedIcon(new itemIcon(new Orb(2)));		
				obj.setBorder(null);
				checkContainer.add(obj);
				containerPanel.add(obj);
			}
		}		
		containerPanel.repaint();
		this.repaint();
	    updateUI();

	}

	public void updateInventory(){
		checkInventory.clear();
		inventoryPanel.removeAll();
		inventoryPanel.setPreferredSize(new Dimension(300,500));
		inventoryPanel.setBackground(Color.white);
		inventoryPanel.add(placeToInventory);	
		JCheckBox obj = null;
		for(Collectable c : game.getBoard().getVamp(game.getUid()).getInventory()){
			//If Orb:
			if(c instanceof Orb){
				obj = new JCheckBox(Integer.toString(((Orb) c).getColor()));
				obj.setIcon(new itemIcon(c));		
				obj.setSelectedIcon(new itemIcon(new HealthPotion()));						
				obj.setBorder(null);	
				checkInventory.add(obj);
				inventoryPanel.add(obj);
			}
			//If HealthPotion:
			else if (c instanceof HealthPotion){
				obj = new JCheckBox();
				obj.setIcon(new itemIcon(c));
				obj.setSelectedIcon(new itemIcon(new Orb(2)));		
				obj.setBorder(null);
				checkInventory.add(obj);
				inventoryPanel.add(obj);
			}
		}	
		this.repaint();
		inventoryPanel.repaint();
	    updateUI();

	}

	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == backTrade){
				//Remove the trade screen				
				game.showGame();
				game.repaint();
				game.setVisible(true);					
			}
			if(event.getSource() == placeToContainer){	
				for (JCheckBox checkBox : checkContainer) {
					if(checkBox.isSelected()) {
						Integer orbType = null; 
						if(!checkBox.getLabel().equals("")){
							orbType = Integer.parseInt(checkBox.getLabel());
						}
						//Add the selected item into the inventory
						for(Collectable l : container.getItems()){
							if(l instanceof Orb && orbType !=null){	
								game.getBoard().getVamp(game.getUid()).getInventory().add(new Orb(orbType));
								container.getItems().remove(l);
								break;
							}
							else{				
								game.getBoard().getVamp(game.getUid()).getInventory().add(new HealthPotion());
								container.getItems().remove(l);
								break;
							}
						}
					}
					
				}				
			    updateInventory();
			    updateContainer();	
			    StatsPanel x = (StatsPanel) ((GameMenu) game.getPanels().get("game")).getPanels().get("stats");
				x.updateInventory();;
				game.getPanels().get("game").repaint();
				game.getPanels().get("game").updateUI();			
			}
			
			if(event.getSource() == placeToInventory){	
				for (JCheckBox checkBox : checkInventory) {
					if(checkBox.isSelected()) {
						Integer orbType = null; 
						if(!checkBox.getLabel().equals("")){
							orbType = Integer.parseInt(checkBox.getLabel());
						}
						//Add the selected item into the inventory
						for(Collectable l : game.getBoard().getVamp(game.getUid()).getInventory()){
							if(l instanceof Orb && orbType !=null){	
								container.getItems().add(new Orb(orbType));
								game.getBoard().getVamp(game.getUid()).getInventory().remove(l);
								break;
							}
							else{				
								container.getItems().add(new HealthPotion());
								game.getBoard().getVamp(game.getUid()).getInventory().remove(l);
								break;
							}
						}
					}
					
				}	
				
			    updateInventory();
			    updateContainer();		    
			    StatsPanel x = (StatsPanel) ((GameMenu) game.getPanels().get("game")).getPanels().get("stats");
				x.updateInventory();;
				game.getPanels().get("game").repaint();
				game.getPanels().get("game").updateUI();
			    
			}
			
			
		}
	}
}		




	
