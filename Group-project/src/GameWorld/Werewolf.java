package GameWorld;

import java.util.Set;

import UI.Board;

public class Werewolf extends GameCharacter{

	private Board game;
	
	
	public Werewolf(Room inRoom){
		this.inRoom=inRoom;
	}
	
	public void enterRandomRoom(){
		
		while(true){
			int random=(int)(Math.random()*5);
			rotateTo(random);
			if(enterRoom()){
				break;
			}
		}
		
	}
	
	
	//instant-kills any player/s in the same room.
	public void kill(){
		Set<Vamp> vamps=inRoom.getVamps();
		for(Vamp vamp:vamps){
			vamp.setStatus(Vamp.DEAD);
		}
		
	}

	
}
