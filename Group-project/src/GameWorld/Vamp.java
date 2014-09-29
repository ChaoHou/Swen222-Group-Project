package GameWorld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import UI.Board;


public class Vamp extends GameCharacter{
	
	
	public static final int DEAD=5;
	public static final int ALIVE=6;
	public static final int PARALYSED=7;
	

	private int uid;

	
	
	private int status=ALIVE;
	private int health;
	private boolean isFighting;
	private boolean isTrading;
	//private Furniture hidingIn;
	
	
	public Vamp(int uid, Board game){
		this.uid=uid;
		this.game=game;
	}

	public void collect(Collectable collectable){
		
		
	}
	

	
	public void fight(Vamp player){
		
	}
	
	public void hideIn(Furniture furniture){
		furniture.hidePlayer(this);
		//this.hidingIn=furniture;
	}
	
	
	public boolean isFighting(){
		return this.isFighting;
	}
	
	public void setFighting(boolean bool){
		this.isFighting=bool;
	}
	
	private boolean isTrading() {
		return isTrading;
	}

	private void setTrading(boolean isTradig) {
		this.isTrading = isTradig;
	}
	
	public boolean isDead(){
		return health<=0;
	}	
	
	public void setStatus(int status){
		if(status==Vamp.DEAD || status==Vamp.ALIVE || status==Vamp.PARALYSED){
			this.status=status;
		}else{
			throw new IllegalArgumentException("invalid status to set for Vamp.");
		}
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public int getUid(){
		return this.uid;
	}
	
	public void respawn(Room room){
//		room.addGameCharacter(this);
//		this.inRoom=room;
//		System.out.println("you are respawned, in "+inRoom);
	}
	
	public boolean enterRoom(){
		Room roomFrom = game.getRoomContainsPlayer(this);
		Room roomToEnter=game.getRoomAhead(roomFrom, facing);
		
		if(roomToEnter==null){
			System.out.println("no room ahead");
			return false;
		}else{
			System.out.println("entering "+roomToEnter+" from "+roomFrom);
			roomFrom.playerLeaveRoom(this);
			roomToEnter.playerEnterRoom(this);
			System.out.println("entered "+roomToEnter);
			return true;
		}
	}
	
	public void toOutputStream(DataOutputStream dout) throws IOException {		
		dout.writeInt(uid);
		dout.writeInt(facing);
		dout.writeInt(status);
		dout.writeInt(health);
		dout.writeBoolean(isFighting);
		dout.writeBoolean(isTrading);
	}
	
	public static Vamp fromInputStream(DataInputStream din,Board game) throws IOException {
		int uid = din.readInt();
		int facing = din.readInt();
		int status = din.readInt();
		int health = din.readInt();
		boolean isFighting = din.readBoolean();
		boolean isTrading = din.readBoolean();
		Vamp temp = new Vamp(uid, game);
		temp.setDirectionFacing(facing);
		temp.setStatus(status);
		temp.setHealth(health);
		temp.setFighting(isFighting);
		temp.setTrading(isTrading);
		
		return temp;
	}
	
}
