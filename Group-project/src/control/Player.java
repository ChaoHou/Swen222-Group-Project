package control;

import gameworld.Collectable;
import gameworld.Container;
import gameworld.Furniture;
import gameworld.GameCharacter;
import gameworld.HealthPack;
import gameworld.Orb;
import gameworld.Room;

import gameworld.Vamp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Map;

import javax.media.opengl.GL2;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import rendering.Renderer;
import ui.Board;
import ui.ContainerScreen;
import ui.GameFrame;
import ui.GameMenu;
import ui.GameMenu.StatsPanel;
import ui.GameMenu.textPanel;
import ui.GameOverScreen;
import ui.HidingScreen;
import ui.InstructionsScreen;

import ui.MapScreen;


public class Player extends Thread implements KeyListener,ActionListener,MouseListener,PlayerInterface{

	private int uid;
	private Board game;
	private Renderer renderer;
	private GameFrame frame;
	private Map<JButton, String> buttons;
	private Map<JButton, String> screenButtons;
	private Room[][] rooms;

	Room temp; // = new Furniture(1,0,0,0,1);

	
	public Player(int uid, Board game,Renderer renderer){	
		this.uid=uid;
		this.game=game;
		this.renderer = renderer;
		this.rooms = game.getRooms();
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		Vamp vamp=game.getVamp(this.uid);
		
		if(code==KeyEvent.VK_W){
			vamp.enterRoom();
		}else if(code==KeyEvent.VK_D){
			vamp.rotateToFace((vamp.getDirectionFacing()+1)%4);
		}else if(code==KeyEvent.VK_A){
			int toFace=(vamp.getDirectionFacing()-1)%4;
			if(toFace<0){
				toFace=Vamp.WEST;
			}
			vamp.rotateToFace(toFace);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		Vamp vamp=game.getVamp(this.uid);
		
		if(action.equals("Change Room")){
			vamp.enterRoom();
			String r = game.getRoomContainingPlayer(vamp).toString();
			printMessage("You entered: " + r );
			
		}else if(action.equals("Right")){
			vamp.rotateToFace((vamp.getDirectionFacing()+1)%4);
			String dir = game.getVamp(uid).intDirToString();
			printMessage("You are facing: " + dir);
			
			
			
			
		}else if(action.equals("Left")){
			int toFace=(vamp.getDirectionFacing()-1)%4;
			
			if(toFace<0){
				toFace=Vamp.WEST;
			}
			vamp.rotateToFace(toFace);
			
			
			String dir = game.getVamp(uid).intDirToString();
			printMessage("You are facing: " + dir);
			
			
			
		}

		
		
		//HELP AND CHEATS BAR
		if(action.equals("Show Instructions")){	
			if(frame.getCurrentScreen() != null){
					frame.showGame(frame.getCurrentScreen());
			}
			InstructionsScreen temp = new InstructionsScreen("instructions", frame);
			frame.showPopUp(temp);
			frame.setVisible(true);	
			return;
		}			
		else if(action.equals("Commit Suicide")){
			//Game over while doing something (ex. looking at the map)
			printMessage("You killed yourself.");	
			printMessage("..Idiot");			

			//First, check if there's currently a popup menu
			if(frame.getCurrentScreen() != null){
			   //Second, remove that popup, no matter what (The game's over anyway!)
				frame.showGame(frame.getCurrentScreen());
			}			
			//Third, show the gameover screen.
			    GameOverScreen gameover = new GameOverScreen("gameover", frame, false);
			    frame.showPopUp(gameover);
			    frame.setVisible(true);
		        gameover.updateUI();
			
			return;
		}	
		
		else if(action.equals("Give me all Items")){
			//You get all possible items!
			printMessage("Like magic, your inventory fills up");			
			this.game.getVamp(uid).getInventory().add(new HealthPack());
			this.game.getVamp(uid).getInventory().add(new Orb(0));	
			this.game.getVamp(uid).getInventory().add(new Orb(1));	
			this.game.getVamp(uid).getInventory().add(new Orb(2));	
			frame.getPanels().get("game").repaint();
			frame.getPanels().get("game").updateUI();
			((StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats")).updateInventory();
		}
		
		
		else if(action.equals("Hide into Nothingness")){
			
			if(!game.getVamp(uid).isHiding()){
				printMessage("You hid in the shadows");
				game.getVamp(uid).setHiding(true);
			}
			else{			
				printMessage("You reappeared");
				game.getVamp(uid).setHiding(false);
			}
			
		}
		
		
		
		
		//GAME MENU BUTTONS				
		if(buttons.get(e.getSource()) != null){
			if(buttons.get(e.getSource()).equals("bigMap")){
		    MapScreen map = new MapScreen("map", frame);
			frame.showPopUp(map);
			frame.setVisible(true);
		    frame.getCurrentScreen().updateUI();
		}	
			if(buttons.get(e.getSource()).equals("Orb")){		
				//Touching an orb hurts you!
				printMessage("You tried eating the orb.");
				printMessage("You damaged your teeth by accident...");
				game.getVamp(uid).deductHealth(1);	
				//Updating the Statistics Information:
				StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
				x.updateHealth();
				frame.getPanels().get("game").repaint();
				frame.getPanels().get("game").updateUI();
			}			
			if(buttons.get(e.getSource()).equals("HealthPack")){
				this.printMessage("You Healed up to Max Health!");
				game.getVamp(uid).setHealth(Vamp.FULL_HEALTH);	
				HealthPack temp = new HealthPack();
				game.getVamp(uid).removeItem(temp);
				//Updating the Statistics Information:
				StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
				x.updateHealth();
				x.updateInventory();
				frame.getPanels().get("game").repaint();
				frame.getPanels().get("game").updateUI();
			}
		}
				
		
		//SCREEN BUTTONS
		screenButtons = frame.getScreenButtons();
		if(screenButtons.get(e.getSource()) != null){			
			if(screenButtons.get(e.getSource()).equals("backToGame")){
				frame.showGame(frame.getCurrentScreen());
				frame.repaint();
				frame.setVisible(true);	
			}
			else if(screenButtons.get(e.getSource()).equals("placeToInventory")){
				//You'll need the containerScreen
				Collectable temp = null;
				ContainerScreen currentScreen = (ContainerScreen) frame.getCurrentScreen();
				for (JCheckBox checkBox : currentScreen.getCheckContainer()) {
					if(checkBox.isSelected()) {
						Integer orbType = null; 
						if(!checkBox.getLabel().equals("")){
							orbType = Integer.parseInt(checkBox.getLabel());
							temp = new Orb((int)orbType);
						} else{
							temp = new HealthPack();
						}
						//Add the selected item into the inventory
						if(!currentScreen.getGame().getBoard().getVamp(currentScreen.getGame().getUid()).isInventoryfull()){
							 currentScreen.getGame().getBoard().getVamp(currentScreen.getGame().getUid()).collectItem((currentScreen.getContainer().remove(temp)));	
							 
						}
						else{
							System.out.println("Full!");
						}
					}
				}	
			    currentScreen.updateInventory();
			    currentScreen.updateContainer();		    
			    ((StatsPanel) ((GameMenu) currentScreen.getGame().getPanels().get("game")).getPanels().get("stats")).updateInventory();
				((GameMenu) currentScreen.getGame().getPanels().get("game")).disableButtons();
				currentScreen.getGame().getPanels().get("game").repaint();
				currentScreen.getGame().getPanels().get("game").updateUI();   
			}
			
			else if(screenButtons.get(e.getSource()).equals("placeToContainer")){
				//You'll need the containerScreen
				
				Collectable temp = null;
				ContainerScreen currentScreen = (ContainerScreen) frame.getCurrentScreen();
				for (JCheckBox checkBox : currentScreen.getCheckInventory()) {
					if(checkBox.isSelected()) {
						Integer orbType = null; 
						if(!checkBox.getLabel().equals("")){
							orbType = Integer.parseInt(checkBox.getLabel());
							temp = new Orb((int)orbType);
						} else{
							temp = new HealthPack();
						}
						//Add the selected item into the inventory
						
						Vamp vv = currentScreen.getGame().getBoard().getVamp(uid);
				
						currentScreen.getContainer().addItem(vv.removeItem(temp));		

					}
				}	
			    currentScreen.updateInventory();
			    currentScreen.updateContainer();		    
			    ((StatsPanel) ((GameMenu) currentScreen.getGame().getPanels().get("game")).getPanels().get("stats")).updateInventory();
				((GameMenu) currentScreen.getGame().getPanels().get("game")).disableButtons();
				currentScreen.getGame().getPanels().get("game").repaint();
				currentScreen.getGame().getPanels().get("game").updateUI();   
			}
			
			else if(screenButtons.get(e.getSource()).equals("backMainMenu")){
				//You're going to need to get rid of all the panels:
				
				frame.remove(frame.getPanels().get("game"));
				frame.remove(frame.getPanels().get("gameover"));
				
				//Go to Main Menu
				frame.showMainMenu();
				frame.setRunningGame(true);
				
				frame.repaint();
				frame.setVisible(true);
				
			}
			//Remove the player from the furniture and set the player visible.
			else if(screenButtons.get(e.getSource()).equals("getOut")){
				//TODO
				//Remove the player from the furniture.
				
				HidingScreen x = (HidingScreen) frame.getCurrentScreen();
		        Room room = game.getRoomContainingPlayer(game.getVamp(uid));
		        
		        room.getOutFromFurniture(game.getVamp(uid));
		        
				frame.showGame(frame.getCurrentScreen());
				frame.repaint();
				frame.setVisible(true);	
				
			}
			
			
			
			frame.getPanels().get("game").repaint();
			frame.getPanels().get("game").updateUI();
		}		
	}
	
	public void setFrame(GameFrame g){
		this.frame = g;
		if(((GameMenu) frame.getPanels().get("game")).getButtons() != null){
					this.buttons = ((GameMenu) frame.getPanels().get("game")).getButtons();

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		//int x = e.getX();
		//int y = e.getY();
		//System.out.println("x:"+x+" y:"+y);
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
	}


	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("Mouse pressed");
		
		renderer.setMouseEvent(e);
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	/**
	 * This method prints out a message in the message panel
	 * Note: Players do not share the same message panel
	 * 
	 * -Raul John De guzman
	 */
	
	public void printMessage(String message){
		textPanel x = (textPanel) ((GameMenu) this.frame.getPanels().get("game")).getPanels().get("messages");
	    x.getMessages().append(message + "\n");	
	}
	
	public void run(){
		try {
			while(true){
				
			
				Thread.sleep(100);
				//update map
				
				this.frame.getMapPanel().getMap().repaint();
				//this.frame.getMapPanel().getMap().updateUI();
				frame.setVisible(true);
				frame.repaint();
				
				//pick up containers
				if(renderer.isContainerSelected()){
					Room room = game.getRoomContainingPlayer(game.getVamp(uid));
					Container container = room.getContainer();
					if(container != null){
						frame.showTrade(container);
						frame.setVisible(true);
						frame.repaint();
					}
					renderer.setContainerSelected(false);
				}else if(renderer.isFurnitureSelected()){

					Room room = game.getRoomContainingPlayer(game.getVamp(uid));
					Furniture furniture = room.getFurniture();
					
					temp = room;
					
					if(furniture.getHidingPlayer() != null){
						printMessage("You found another player!");
						//TODO
						Vamp victim = furniture.getHidingPlayer();
						victim.setFighting(true);
						
						if(!victim.getInventory().isEmpty()){
							Collectable x = victim.getInventory().get(0);
							victim.removeItem(x);
							game.getVamp(uid).collectItem(x);
							StatsPanel yourInventory = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
							yourInventory.updateInventory();
							frame.getPanels().get("game").repaint();
							frame.getPanels().get("game").updateUI(); 	
							room.getOutFromFurniture(victim);
							victim.setHiding(false);
										
						}
											
						return;
					}else{
						room.hideInFurniture(game.getVamp(uid));			
						printMessage("You hid into the furniture");	
						frame.showHidingScreen();
						game.getVamp(uid).setHiding(true);
					}
					renderer.setFurnitureSelected(false);
				}
			
				//What if the player died?
				if(game.getVamp(uid).getHealth()<=0){
	 				//First, check if there's currently a popup menu
	 				if(frame.getCurrentScreen() != null){
	 				   //Second, remove that popup, no matter what (The game's over anyway!)
	 					frame.showGame(frame.getCurrentScreen());
	 				}			
	 				//Third, show the gameover screen.
//	 				    GameOverScreen gameover = new GameOverScreen("gameover", frame, false);
//	 				    frame.showPopUp(gameover);
//	 				    frame.setVisible(true);
//	 			        gameover.updateUI(); 				
//					    break;
				}
				

				//What if the player died?
				if(game.getVamp(uid).isDead()){
	 				//First, check if there's currently a popup menu				
	 				if(frame.getCurrentScreen() != null){
	 				   //Second, remove that popup, no matter what (The game's over anyway!)
	 					frame.showGame(frame.getCurrentScreen());
	 				}			
//	 				//Third, show the gameover screen.
//	 				    GameOverScreen gameover = new GameOverScreen("gameover", frame, false);
//	 				    frame.showPopUp(gameover);
//	 				    frame.setVisible(true);
//	 			        gameover.updateUI(); 				
//					    break;
				}
				
				//What if the player got ambushed?
				if(game.getVamp(uid).isFighting()){
	 				printMessage("Another player ambushed you!");
	 				printMessage("He attacked you without mercy!");				
	 				//First, remove the hiding screen
	 				if(frame.getCurrentScreen() != null){
	 				   //Second, remove that popup, no matter what.
	 					frame.showGame(frame.getCurrentScreen());
	 				}			
	 				//Third, lower that player's health and update information
	 				game.getVamp(uid).deductHealth(1);	
	 				game.getVamp(uid).setFighting(false);
	 				game.getVamp(uid).setHiding(false);
					//Updating the Statistics Information:
					StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
					x.updateHealth();
					frame.getPanels().get("game").repaint();
					frame.getPanels().get("game").updateUI(); 	
					
					
	 				
				}
				
				//Automatic Victory
				if(!rooms[3][1].getVamps().isEmpty() && game.getVamp(uid).hasAllOrbs()){
	 				printMessage("You Won a Glorious Victory.");
	 				//First, check if there's currently a popup menu
	 				if(frame.getCurrentScreen() != null){
	 				   //Second, remove that popup, no matter what (The game's over anyway!)
	 					frame.showGame(frame.getCurrentScreen());
	 				}			
	 				//Third, show the gameover screen.
	 				    GameOverScreen gameover = new GameOverScreen("gameover", frame, true);
	 				    frame.showPopUp(gameover);
	 				    frame.setVisible(true);
	 			        gameover.updateUI(); 		 			        
					    break;
				}
			
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
