package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ui.Board;


public class Vamp extends GameCharacter{
	
	
	public static final int DEAD=5;
	public static final int ALIVE=6;
	public static final int PARALYSED=7;
	

	private int uid;

	
	
	private int status=ALIVE;
	private int health;
	private boolean isFighting;
	private boolean isTrading;
	private List<Collectable> inventory=new ArrayList<Collectable>();
	
	
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
		return getHealth()<=0;
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
		Room roomFrom = game.getRoomContainingPlayer(this);
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
	
	public void rotateTo(int dir){
		if(dir==Vamp.NORTH || dir==Vamp.EAST ||
				dir==Vamp.SOUTH || dir==Vamp.WEST){
			facing=dir;
			System.out.println("Player:"+uid+" now facing "+ intDirToString());
		}else{
			throw new IllegalArgumentException("invalid direction to face.");
		}
	}
	
	
	public List<Collectable> getInventory(){
		return this.inventory;
	}
	
	
	
	public void collectItem(Collectable item){
		if(!inventoryfull())
		this.getInventory().add(item);	
	}
	
	/**
	 * Is the inventory full?
	 * @author - Raul John De Guzman
	 */
	public boolean inventoryfull(){
		return this.getInventory().size() == 5;
	}
	
	
	public void removeItem(Collectable item){
		this.getInventory().remove(item);
	}
	
	/**
	 * This is to remove and return a specific item type 
	 * @author - Raul John De Guzman
	 */
	
	public Collectable remove(Collectable c){
		Collectable temp = null;
		//We'll need to iterate through the whole container for this...
		for(Collectable l : this.getInventory()){
			//Is it an Orb? Are they the same kind of Orb?	
			if((c instanceof Orb && l instanceof Orb) && 
					((Orb) c).getColor() == ((Orb) l).getColor()){
				temp = (Orb) l;
				this.getInventory().remove(l);
				return temp;
			}
			//Is it a HealthPack?
			else if(c instanceof HealthPack && l instanceof HealthPack){
				temp = (HealthPack) l;
				this.getInventory().remove(l);
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * If the player has all 3 of each orb, he may win the game!
	 * 
	 */
	
	public boolean canWin(){
		Orb tempBlue = new Orb(0);
		Orb tempGreen = new Orb(1);
		Orb tempRed = new Orb(2);
		Boolean b = false,g = false,r = false;
		for(Collectable l : this.getInventory()){
			//Do you have a blue orb?	
			if((l instanceof Orb) && ((Orb) tempBlue).getColor() == ((Orb) l).getColor()){
				b = true;
			}
			if((l instanceof Orb) && ((Orb) tempGreen).getColor() == ((Orb) l).getColor()){
				g = true;
			}
			if((l instanceof Orb) && ((Orb) tempRed).getColor() == ((Orb) l).getColor()){
				r = true;
			}
		}
		
		return b && g && r;
	}
	
	public void toOutputStream(DataOutputStream dout) throws IOException {		
		dout.writeInt(uid);
		dout.writeInt(facing);
		dout.writeInt(status);
		dout.writeInt(getHealth());
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

	public int getHealth() {
		return health;
	}
	
}
