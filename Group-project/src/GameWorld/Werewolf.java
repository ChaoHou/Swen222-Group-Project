package GameWorld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import UI.Board;

public class Werewolf extends GameCharacter{	
	
	public Werewolf(Board game){
		this.game = game;
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
		Set<Vamp> vamps=game.getRoomContainsNPC().getVamps();
		for(Vamp vamp:vamps){
			vamp.setStatus(Vamp.DEAD);
		}
		
	}

	@Override
	public boolean enterRoom() {
		Room roomFrom = game.getRoomContainsNPC();
		Room roomToEnter=game.getRoomAhead(roomFrom, facing);
		
		if(roomToEnter==null){
			System.out.println("no room ahead");
			return false;
		}else{
			System.out.println("entering "+roomToEnter+" from "+roomFrom);
			Werewolf temp = roomFrom.npcLeaveRoom(this);
			roomToEnter.npcEnterRoom(temp);
			System.out.println("entered "+roomToEnter);
			return true;
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