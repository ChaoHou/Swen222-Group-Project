package control;

import gameworld.Container;
import gameworld.GameCharacter;
import gameworld.HealthPack;
import gameworld.Orb;
import ui.GameMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rendering.Renderer;
import ui.Board;
import ui.GameFrame;
import ui.GameMenu.StatsPanel;


public class Player extends Thread implements KeyListener,ActionListener {

	private int uid;
	private Board game;
	private Renderer renderer;
	private GameFrame frame;
	
	
	public Player(int uid, Board game,Renderer renderer){	
		this.uid=uid;
		this.game=game;
		this.renderer = renderer;
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
		//System.out.println(action);
		if(action.equals("Turn Left")){
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
			renderer.rotateL();
		}				
		//When turning right
		else if(action.equals("Turn Right")){
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
			renderer.rotateR();

		}
		else if(action.equals("Change Room")){
			//Use room
			game.getVamp(uid).enterRoom();
			
		}
		else if(action.equals("p1")){
			
		}
		else if(action.equals("HealthPotion")){
			System.out.println("You Healed up to Max health");
			game.getVamp(uid).setHealth(5);		

			//Updating the Health Information:
			StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
			x.updateHealth();
			
			frame.getPanels().get("game").repaint();
			frame.getPanels().get("game").updateUI();

		}
		//TESTING SOMETHING:
		else if(action.equals("Orb")){
			//make a container
			Container c = new Container(0);
			c.addItem(new Orb(0));
			c.addItem(new Orb(1));
			c.addItem(new Orb(2));
			c.addItem(new HealthPack());
			//Tell the frame to open the trade menu now:
			this.frame.showTrade(c);
			this.frame.setVisible(true);
		    this.frame.repaint();
			
		}
		

	}
	
	public void setFrame(GameFrame g){
		this.frame = g;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void run(){	
		try{
			while(true){
			Thread.sleep(1000);
			this.frame.getPanels().get("game").updateUI();
			this.frame.getMapPanel().getMap().repaint();
			}
			
		}catch(Exception e){
			System.out.println("error in mapThread");
		}
	}
	
}
