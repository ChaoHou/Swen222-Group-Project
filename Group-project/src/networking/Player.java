package networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import UI.Board;
import GameWorld.Character;


public class Player implements KeyListener {
	private final Board game;
	private final int uid;
	
	public Player(int uid, Board game) {
		this.game = game;
		this.uid = uid;
	}
	
	// The following intercept keyboard events from the user.
	
	public void keyPressed(KeyEvent e) {		
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
			game.getCharacter(uid).rotateTo(Character.EAST);
		} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
			game.getCharacter(uid).rotateTo(Character.WEST);
		} else if(code == KeyEvent.VK_UP) {
			game.getCharacter(uid).rotateTo(Character.NORTH);
		} else if(code == KeyEvent.VK_DOWN) {
			game.getCharacter(uid).rotateTo(Character.SOUTH);
		} else if(code == KeyEvent.VK_E){
			game.getCharacter(uid).enterRoom();
		}
		
	}
	
	public void keyReleased(KeyEvent e) {		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

}