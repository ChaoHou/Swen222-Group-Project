package control;

import gameworld.GameCharacter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import rendering.Renderer;
import ui.Board;
import ui.GameFrame;


public class Player implements KeyListener,ActionListener{

	private int uid;
	private Board game;
	private Renderer renderer;
	
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
			//renderer.rotateL();
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
			//renderer.rotateR();

		}
		else if(action.equals("Change Room")){
			//Use room
			game.getVamp(uid).enterRoom();
			
		}
		else if(action.equals("p1")){
			
		}
		
//		else if(action.equals("back")){
//			//Hide the instructions
//			game.getPanels().get("instructions").setVisible(false);		
//			//Check if you're in a current game or not
//			if(game.isRunningGame()){
//				game.showGame();
//				game.repaint();
//				game.setVisible(true);
//			}
//			else{	
//				game.setMainMenu();
//				game.repaint();
//				game.setVisible(true);
//			}	
//		}
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
