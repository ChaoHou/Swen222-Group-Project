package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import ui.Board;

public class Werewolf extends GameCharacter{	
	
	
	public Werewolf(Board game){
		this.game = game;
	}

	
	/*
	 * Enters a random room and kills vamps in it.
	 */
	public void prowl(){
		kill();
		enterRandomRoom();
		kill();
	}
	
	
	/*
	 * Randomly enters a room.
	 */
	public void enterRandomRoom(){
		while(true){
			//random number 0-3 generated, each represents a direction (matches constant in GameCharacter).
			int random=(int)(Math.random()*4);
			rotateTo(random);
			if(enterRoom()){
				break;
			}
		}	
	}
	
	
	/*
	 * Called by enterRandomRoom() to enter a room.
	 * Returns true if entered the room.
	 */
	@Override
	public boolean enterRoom() {
		Room roomFrom = game.getRoomContainingWerewolf();
		Room roomToEnter=game.getRoomAhead(roomFrom, facing);
		
		if(roomToEnter==null){
			System.out.println("no room ahead");
			return false;
		}else{
			System.out.println("entering: "+roomToEnter+"  from: "+roomFrom);
			Werewolf temp = roomFrom.werewolfLeaveRoom(this);
			roomToEnter.werewolfEnterRoom(temp);
			System.out.println("entered: "+game.getRoomContainingWerewolf());
			return true;
		}
	}

	
	/*
	 * Instant-kills any player/s in the same room.
	 */
	public void kill(){
		Room room=game.getRoomContainingWerewolf();
		Set<Vamp> vamps=room.getVamps();
		for(Vamp vamp:vamps){
			vamp.setStatus(Vamp.DEAD);
			System.out.println("vamp"+vamp.getUid()+" is dead");
		}
		
	}

	
	@Override
	public void rotateTo(int dir) {
		if(dir==GameCharacter.NORTH || dir==GameCharacter.EAST ||
				dir==GameCharacter.SOUTH || dir==GameCharacter.WEST){
			facing=dir;
			System.out.println("Werewolf now facing: "+ intDirToString());
		}else{
			throw new IllegalArgumentException("invalid direction: "+dir);
		}
	}

	
	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeInt(facing);
	}

	public static Werewolf fromInputStream(DataInputStream din,Board game) throws IOException {
		int facing = din.readInt();
		Werewolf temp = new Werewolf(game);
		temp.setDirectionFacing(facing);
		
		return temp;
	}


	
}
