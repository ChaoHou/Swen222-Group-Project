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
		enterRoom();
		kill();
	}
	
<<<<<<< HEAD
=======
	
	/*
	 * Randomly enters a room.
	 */
	public void enterRandomRoom(){
		while(true){
			int random=(int)(Math.random()*4);
//			System.out.println("random:"+random);
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
			System.out.println("entering "+roomToEnter+" from "+roomFrom);
			Werewolf temp = roomFrom.werewolfLeaveRoom(this);
			roomToEnter.werewolfEnterRoom(temp);
			//System.out.println("entered "+roomToEnter);
			return true;
		}
	}
>>>>>>> refs/remotes/origin/master

	
	/*
	 * Instant-kills any player/s in the same room.
	 */
	public void kill(){
		Room room=game.getRoomContainingWerewolf();
		Set<Vamp> vamps=room.getVamps();
		for(Vamp vamp:vamps){
			vamp.setStatus(Vamp.DEAD);
		}
		
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
		
		if(roomToEnter==null){
			return false;
		}else{
			Werewolf leavingWolf = roomFrom.werewolfLeaveRoom(this);
			roomToEnter.werewolfEnterRoom(leavingWolf);
			return true;
		}
	}
	
	@Override
	public void rotateToFace(int dir) {
		if(dir==Room.NORTH || dir==Room.EAST ||
				dir==Room.SOUTH || dir==Room.WEST){
			facing=dir;
		}else{
			throw new IllegalArgumentException("invalid direction to face.");
		}
	}

	
	//
	//
	//
	//networking
	//
	//
	//
	//

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
