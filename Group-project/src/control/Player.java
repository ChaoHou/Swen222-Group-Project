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


public class Player extends Thread implements KeyListener,ActionListener,MouseListener{

	private int uid;
	private Board game;
	private Renderer renderer;
	private GameFrame frame;
	private Map<JButton, String> buttons;
	private Map<JButton, String> mainButtons;
	private Map<JButton, String> screenButtons;
	private Room[][] rooms;

	Furniture tempFurniture = new Furniture(1);

	
	public Player(int uid, Board game,Renderer renderer){	
		this.uid=uid;
		this.game=game;
		this.renderer = renderer;
		this.rooms = game.getRooms();
		
		//tempFurniture.hidePlayer(new Vamp(uid+1, this.game));			

	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		
		if(code==KeyEvent.VK_W){
			game.getVamp(this.uid).rotateTo(GameCharacter.NORTH);
		}else if(code==KeyEvent.VK_D){
			game.getVamp(this.uid).rotateTo(GameCharacter.EAST);
		}else if(code==KeyEvent.VK_S){
			game.getVamp(this.uid).rotateTo(GameCharacter.SOUTH);
		}else if(code==KeyEvent.VK_A){
			game.getVamp(this.uid).rotateTo(GameCharacter.WEST);
		}else if(code==KeyEvent.VK_E){
			game.getVamp(this.uid).enterRoom();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println(action.toString());
		//System.out.println(action);
		if(action.equals("Left")){
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
			//renderer.rotateL();
			printMessage("You're facing "+ game.getVamp(uid).intDirToString() );
		}				
		//When turning right
		else if(action.equals("Right")){
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
			//renderer.rotateR();
			printMessage("You're facing "+ game.getVamp(uid).intDirToString() );

		}
		else if(action.equals("Change Room")){
			//Use room
			game.getVamp(uid).enterRoom();
			printMessage("You've entered the " + game.getRoomContainingPlayer(game.getVamp(uid)));

		
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
			//You hide into furniture that's not in the game.
			frame.showHidingScreen(tempFurniture);
			printMessage("You hid into the shadows");	
	
			
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
				game.getVamp(uid).setHealth(game.getVamp(uid).getHealth()-1);	
				//Updating the Statistics Information:
				StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
				x.updateHealth();
				frame.getPanels().get("game").repaint();
				frame.getPanels().get("game").updateUI();
			}			
			if(buttons.get(e.getSource()).equals("HealthPack")){
				this.printMessage("You Healed up to Max Health!");
				game.getVamp(uid).setHealth(5);	
				HealthPack temp = new HealthPack();
				game.getVamp(uid).remove(temp);
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
						if(!currentScreen.getGame().getBoard().getVamp(currentScreen.getGame().getUid()).inventoryfull()){
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
						currentScreen.getContainer().addItem(currentScreen.getGame().getBoard().getVamp(currentScreen.getGame().getUid()).remove(temp));		

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
				//Remove the player from the furniture.
				HidingScreen x = (HidingScreen) frame.getCurrentScreen();
		        x.removePlayer();
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		//int x = e.getX();
		//int y = e.getY();
		//System.out.println("x:"+x+" y:"+y);
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed");
		
		renderer.setMouseEvent(e);
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
				if(renderer.selected){
					Room room = game.getRoomContainingPlayer(game.getVamp(uid));
					Container container = room.getContainer();
					if(container != null){
						frame.showTrade(container);
						frame.setVisible(true);
						frame.repaint();
					}
					renderer.selected = false;
				}
				
				if(game.getVamp(uid).isDead()){
	 				printMessage("You got killed");
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
					    break;
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
	 				game.getVamp(uid).setHealth(game.getVamp(uid).getHealth()-1);	
	 				game.getVamp(uid).setFighting(false);
	 				game.getVamp(uid).setHiding(false);
					//Updating the Statistics Information:
					StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
					x.updateHealth();
					frame.getPanels().get("game").repaint();
					frame.getPanels().get("game").updateUI(); 				
	 				
				}
				
				//Automatic Victory
				if(!rooms[3][1].getVamps().isEmpty() && game.getVamp(uid).canWin()){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
