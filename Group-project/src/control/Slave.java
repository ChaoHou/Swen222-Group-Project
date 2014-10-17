package control;

import gameworld.Collectable;
import gameworld.Container;
import gameworld.Furniture;
import gameworld.GameCharacter;
import gameworld.HealthPack;
import gameworld.Orb;
import gameworld.Room;
import gameworld.Vamp;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import rendering.Renderer;
import ui.Board;
import ui.ContainerScreen;
import ui.GameFrame;
import ui.GameMenu;
import ui.InstructionsScreen;
import ui.GameMenu.StatsPanel;
import ui.GameOverScreen;
import ui.HidingScreen;
import ui.MapScreen;
import ui.GameMenu.textPanel;

/**
 * Slave class: the player for the networking version
 * 
 * @author Chao
 *
 */
public class Slave extends Thread implements MouseListener,KeyListener,ActionListener,PlayerInterface {
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	
	private final int uid;
	
	private Board game;
	
	private GameFrame frame;
	private Map<JButton, String> buttons;
	private Map<JButton, String> screenButtons;
	
	private Renderer renderer;
	
	public Slave(Socket socket,Board game,Renderer renderer,int uid) throws IOException{
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		
		this.uid = uid;
		this.renderer = renderer;
		this.game = game;
		//new TestUI(this);
		//frame = new GameFrame("multi user mode", game, uid, this,renderer);
		//frame.setVisible(true);    
		//frame.repaint();
	}
	
	/**
	 * Main loop here
	 * 		Update the board by the broadcast
	 * 		check if anything is selected
	 * 		check if the player is dead, display the game over screen
	 */
	public void run(){
		try {
			boolean exit = false;
			while (!exit) 	 {
				try {
					while (input.available() != 0) {
						
						updateBoard(game);
						
					}
					
					
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
						System.out.println("Furniture selected");
						
						Room room = game.getRoomContainingPlayer(game.getVamp(uid));
						Furniture furniture = room.getFurniture();
						
						if(furniture.getHidingPlayer() != null){
							printMessage("Sorry, but another player's hiding already!");
							//TODO
							Vamp victim = furniture.getHidingPlayer();
							victim.setFighting(true);
							
							if(!victim.getInventory().isEmpty()){
								//NOT WORKING
								System.out.println("Another player inside, you get hurt");
								printMessage("Another player inside, you get hurt");
								output.writeInt(Master.ACTION.HURT.ordinal());
								updateBoard(game);
								StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
								x.updateHealth();
//								Collectable x = victim.getInventory().get(0);
//								victim.remove(x);
//								game.getVamp(uid).collectItem(x);
//								StatsPanel yourInventory = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
//								yourInventory.updateInventory();
//								frame.getPanels().get("game").repaint();
//								frame.getPanels().get("game").updateUI(); 					
							}
							//temp.getHidingPlayer();	
							//return;
						}else{
							
							output.writeInt(Master.ACTION.HIDE_IN.ordinal());
							
							updateBoard(game);
							
							printMessage("You hid into the shadows");	
							frame.showHidingScreen();
						}
						
						
						
						renderer.setFurnitureSelected(false);
					}
					
					//What if the player died?
					if(game.getVamp(uid).isDead()){
		 				//First, check if there's currently a popup menu
		 				if(frame.getCurrentScreen() != null){
		 				   //Second, remove that popup, no matter what (The game's over anyway!)
		 					frame.showGame(frame.getCurrentScreen());
		 				}			
//		 				//Third, show the gameover screen.
		 				    GameOverScreen gameover = new GameOverScreen("gameover", frame, false);
		 				    frame.showPopUp(gameover);
		 				    frame.setVisible(true);
		 			        gameover.updateUI(); 				
//						    break;
					}
					
					if(!game.getRooms()[3][1].getVamps().isEmpty() && game.getVamp(uid).hasAllOrbs()){
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
					
					Thread.sleep(100);
				} catch (InterruptedException e) {

				}

				
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		renderer.setMouseEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		try{
			if(code==KeyEvent.VK_W){

			}else if(code==KeyEvent.VK_D){
				output.writeInt(Master.ACTION.ROTATE_L.ordinal());
			}else if(code==KeyEvent.VK_A){
				output.writeInt(Master.ACTION.ROTATE_R.ordinal());
			}
		}catch(Exception exc){
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * Perform the actions base on which button is clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		try {
			if(action.equals("Left")){
				output.writeInt(Master.ACTION.ROTATE_L.ordinal());
				printMessage("You moved left");
			}				
			else if(action.equals("Right")){
				output.writeInt(Master.ACTION.ROTATE_R.ordinal());
			}
			else if(action.equals("Change Room")){
				output.writeInt(Master.ACTION.CHANGE_ROOM.ordinal());
				printMessage("You've entered the " + game.getRoomContainingPlayer(game.getVamp(uid)));

			}else if(action.equals("Show Instructions")){
				if(frame.getCurrentScreen() != null){
					frame.showGame(frame.getCurrentScreen());
				}
				InstructionsScreen temp = new InstructionsScreen("instructions", frame);
				frame.showPopUp(temp);
				frame.setVisible(true);	
				return;
			}else if(action.equals("Commit Suicide")){
				
			}else if(action.equals("Give me all Items")){
				
			}else if(action.equals("Hide into Nothingness")){
				
			}
			
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
					//make the server do it
					output.writeInt(Master.ACTION.HURT.ordinal());
					game.getVamp(uid).setHealth(game.getVamp(uid).getHealth()-1);	
					//Updating the Statistics Information:
					StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
					x.updateHealth();
					frame.getPanels().get("game").repaint();
					frame.getPanels().get("game").updateUI();
				}			
				if(buttons.get(e.getSource()).equals("HealthPack")){
					this.printMessage("You Healed up to Max Health!");
					output.writeInt(Master.ACTION.HEAL.ordinal());
					
					StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
					x.updateHealth();
					x.updateInventory();
//					frame.getPanels().get("game").repaint();
//					frame.getPanels().get("game").updateUI();
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
					System.out.println("Place to inventory");
					
					ContainerScreen currentScreen = (ContainerScreen) frame.getCurrentScreen();
					for (JCheckBox checkBox : currentScreen.getCheckContainer()) {
						if(checkBox.isSelected()) {
							
							if(!checkBox.getLabel().equals("")){
								int orbType = Integer.parseInt(checkBox.getLabel());
								if(!currentScreen.getGame().getBoard().getVamp(currentScreen.getGame().getUid()).isInventoryfull()){
									output.writeInt(Master.ACTION.PLACE_TO_INVENTORY.ordinal());
									output.writeInt(Master.TYPE_ORB); 
									output.writeInt(orbType);
								
								}else{
									System.out.println("Full!");
								}
							} else{
								if(!currentScreen.getGame().getBoard().getVamp(currentScreen.getGame().getUid()).isInventoryfull()){
									output.writeInt(Master.ACTION.PLACE_TO_INVENTORY.ordinal());
									output.writeInt(Master.TYPE_HEALTH); //1 means healthpack
								}else{
									System.out.println("Full!");
								}
							}

						}
					}	
					
					
					//need to updated the ui
					//updateCurrentBoard();
					try {
						//waiting for update the board
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					Container container = game.getRoomContainingPlayer(game.getVamp(uid)).getContainer();
					System.out.println("Container size:"+container.getItems().size());
					
				    currentScreen.updateInventory();
				    currentScreen.updateContainer();		    
				    ((StatsPanel) ((GameMenu) currentScreen.getGame().getPanels().get("game")).getPanels().get("stats")).updateInventory();
					((GameMenu) currentScreen.getGame().getPanels().get("game")).disableButtons();
					currentScreen.getGame().getPanels().get("game").repaint();
					currentScreen.getGame().getPanels().get("game").updateUI();   
				}
				
				else if(screenButtons.get(e.getSource()).equals("placeToContainer")){
					//You'll need the containerScreen
					ContainerScreen currentScreen = (ContainerScreen) frame.getCurrentScreen();
					for (JCheckBox checkBox : currentScreen.getCheckInventory()) {
						if(checkBox.isSelected()) {
							if(!checkBox.getLabel().equals("")){
								int orbType = Integer.parseInt(checkBox.getLabel());
								output.writeInt(Master.ACTION.PLACE_TO_CONTAINER.ordinal());
								output.writeInt(Master.TYPE_ORB); 
								output.writeInt(orbType);
							} else{
								output.writeInt(Master.ACTION.PLACE_TO_CONTAINER.ordinal());
								output.writeInt(Master.TYPE_HEALTH); //1 means healthpack
							}
							//Add the selected item into the inventory

						}
					}	
					
					try {
						//waiting for update the board
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
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

					output.writeInt(Master.ACTION.GET_OUT.ordinal());
					
					frame.showGame(frame.getCurrentScreen());
					frame.repaint();
					frame.setVisible(true);	
					
				}
				
				
				
				frame.getPanels().get("game").repaint();
				frame.getPanels().get("game").updateUI();
			}		
			
			
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setFrame(GameFrame g){
		this.frame = g;
		if(((GameMenu) frame.getPanels().get("game")).getButtons() != null){
					this.buttons = ((GameMenu) frame.getPanels().get("game")).getButtons();

		}
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
	
	public DataInputStream getInputStream(){
		return input;
	}
	
	public synchronized void updateBoard(Board game) throws IOException{
		//System.out.println("Updated board");
		
		int size = input.readInt();
		byte[] bytes = new byte[size];
		input.readFully(bytes);
		game.fromByteArray(bytes);
	}
	
	
	
	public void updateCurrentBoard(){
		try {
			while(true){
				
				if(input.available() != 0){
					updateBoard(game);
					break;
				}
				Thread.sleep(100);
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
