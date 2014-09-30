package networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import rendering.Renderer;
import GameWorld.GameCharacter;
import UI.Board;


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
			game.getCharacter(this.uid).rotateTo(GameCharacter.NORTH);
		}else if(code==KeyEvent.VK_D){
			game.getCharacter(this.uid).rotateTo(GameCharacter.EAST);
		}else if(code==KeyEvent.VK_S){
			game.getCharacter(this.uid).rotateTo(GameCharacter.SOUTH);
		}else if(code==KeyEvent.VK_A){
			game.getCharacter(this.uid).rotateTo(GameCharacter.WEST);
		}else if(code==KeyEvent.VK_E){
			game.getCharacter(this.uid).enterRoom();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		//System.out.println(action);
		if(action.equals("Turn Left")){
			if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getCharacter(uid).rotateTo(GameCharacter.WEST);
			else if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getCharacter(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getCharacter(uid).rotateTo(GameCharacter.EAST);
			else if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getCharacter(uid).rotateTo(GameCharacter.NORTH);
			renderer.rotateL();
		}				
		//When turning right
		else if(action.equals("Turn Right")){
			if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getCharacter(uid).rotateTo(GameCharacter.EAST);
			else if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getCharacter(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getCharacter(uid).rotateTo(GameCharacter.WEST);
			else if(game.getCharacter(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getCharacter(uid).rotateTo(GameCharacter.NORTH);
			renderer.rotateR();

		}
		else if(action.equals("Change Room")){
			//Use room
			String answer = (String) JOptionPane.showInputDialog(null, "Which Room would you like to go to?", null, 
					 JOptionPane.PLAIN_MESSAGE, null, new String[]{ "d", "dd"}, null);
		
			System.out.println("You moved to Room: " + answer );
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
}
