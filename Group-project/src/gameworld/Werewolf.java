package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import ui.Board;

/**
 * 
 * 
 * This class represents the enemy AI in the game.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class Werewolf extends GameCharacter{	
	
	public Werewolf(Board game){
		this.game = game;
	}

	
	/*
	 * Enters a random room and kills vamps in it.
	 */
	public void prowl(){
		kill();
		enterRoom();
		kill();
	}
	
	
	/*
	 * Enter a room, randomly.
	 * Returns true if entered the room.
	 */
	@Override
	public boolean enterRoom() {
		while(true){
			int randomDir=(int)(Math.random()*4);
			rotateToFace(randomDir);
			if(canEnterRoomAhead()){
				break;
			}
		}
		return true;
	}

	private boolean canEnterRoomAhead(){
		Room roomFrom = game.getRoomContainingWerewolf();
		Room roomToEnter=game.getRoomAhead(roomFrom, facing);
		Room startRoom=game.getRoom("startroom");
		if(roomToEnter==null || startRoom.equals(roomToEnter)){
			return false;
		}else{
			Werewolf leavingWolf = roomFrom.werewolfLeaveRoom(this);
			roomToEnter.werewolfEnterRoom(leavingWolf);
			return true;
		}
	}	
	
	
	
	@Override
	public void rotateToFace(int dir) {
		if(dir==Vamp.NORTH || dir==Vamp.EAST ||
				dir==Vamp.SOUTH || dir==Vamp.WEST){
			facing=dir;
			System.out.println("Werewolf now facing "+ intDirToString());
		}else{
			throw new IllegalArgumentException("invalid direction to face.");
		}
	}


	
	/*
	 * Instant-kills any player/s in the same room.
	 */
	public void kill(){
		Room room=game.getRoomContainingWerewolf();
		Set<Vamp> vamps=room.getVamps();
		for(Vamp vamp:vamps){
			if(!vamp.isHiding())
			vamp.setStatus(Vamp.DEAD,true);
		}
		
	}
	
	
	/**
	 * @author Chao
	 */
	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeInt(facing);
	}

	/**
	 * @author Chao
	 * @param din
	 * @param game
	 * @return
	 * @throws IOException
	 */
	public static Werewolf fromInputStream(DataInputStream din,Board game) throws IOException {
		int facing = din.readInt();
		Werewolf temp = new Werewolf(game);
		temp.setDirectionFacing(facing);
		
		return temp;
	}


	
}
