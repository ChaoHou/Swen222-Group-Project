package networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameWorld.GameCharacter;
import UI.Board;


public class Player implements KeyListener{

	private int uid;
	private Board game;
	
	public Player(int uid, Board game){	
		this.uid=uid;
		this.game=game;
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
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
