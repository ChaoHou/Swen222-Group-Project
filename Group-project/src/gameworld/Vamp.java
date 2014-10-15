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
	public static final int RECOVERING=8;

	public static final int FULL_HEALTH=5;
	public static final long RECOVERY_TIME=4000;
	

	private int uid;
	private int status=ALIVE;
	private int health;
	private List<Collectable> inventory=new ArrayList<Collectable>();
	
	//A boolean to check if the player's hidden or not
	//- John
	private boolean isHiding =false;
	private boolean isFighting =false;
	private boolean isTrading =false;
	
	
	
	
	public Vamp(int uid, Board game){
		this.uid=uid;
		this.game=game;	
		health=FULL_HEALTH;
		inventory.add(new HealthPack());
		inventory.add(new Orb(Orb.BLUE));
	}
	
	
	
	
	
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	//------------------State Info-------------------------//
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	
	public boolean isHiding() {
		return isHiding;
	}

	public void setHiding(boolean isHiding) {
		this.isHiding = isHiding;
	}

	public boolean isFighting(){
		return this.isFighting;
	}
	
	public void setFighting(boolean bool){
		this.isFighting=bool;
	}
	
	public boolean isTrading() {
		return isTrading;
	}

	private void setTrading(boolean isTradig) {
		this.isTrading = isTradig;
	}
	
	public boolean isDead(){
		return getHealth()<=0 || status==Vamp.DEAD;
	}
	
	public boolean isRecovering(){
		return this.status==Vamp.RECOVERING;
	}
	
	public void setStatus(int status){
		if(status==Vamp.DEAD){
			System.out.println("----------Vamp"+getUid()+" is dead-------------");
			health=0;
			this.status=status;
			respawn();
		}else if(status==Vamp.ALIVE || status==Vamp.PARALYSED || status==Vamp.RECOVERING){
			this.status=status;
		}else{
			throw new IllegalArgumentException("invalid status to set for Vamp.");
		}
	}
	
	public int getUid(){
		return this.uid;
	}
	
	//-----------------------------------------------------//

	
	
	
	
	
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	//---------------------Health--------------------------//
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}

	public void deductHealth(int dx){
		this.health-=dx;
		if(this.health<=0){
			setStatus(Vamp.DEAD);
		}
	}
		
	//-----------------------------------------------------//
	
	
	
	
	
	

	//-----------------------------------------------------//
	//-----------------------------------------------------//
	//-------------------Behaviour-------------------------//
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	
	public void respawn(){
		Room currentRoom=game.getRoomContainingPlayer(this);
		currentRoom.playerLeaveRoom(this);
		Room respawnRoom=game.getRespawnRoom();
		respawnRoom.playerEnterRoom(this);
		Thread thread=new Thread(){
			public void run(){
				try{
					Thread.sleep(Vamp.RECOVERY_TIME/2);
					setStatus(Vamp.RECOVERING);
					Thread.sleep(Vamp.RECOVERY_TIME);
					setStatus(Vamp.ALIVE);
					setHealth(FULL_HEALTH/2);
					System.out.println("------------vamp"+getUid()+" has recovered---------------");
				}catch(Exception e){
					
				}
			}
		};
		thread.start();
	}
	
	public boolean enterRoom(){
		if(isRecovering()){
			System.out.println("cant move----------you'are recovering!!!");
			return false;
		}	
		
		Room roomFrom = game.getRoomContainingPlayer(this);
		Room roomToEnter=game.getRoomAhead(roomFrom, facing);
		
		if(roomToEnter==null){
			System.out.println("no room ahead");
			return false;
		}else{
			roomFrom.playerLeaveRoom(this);
			roomToEnter.playerEnterRoom(this);
			return true;
		}
	}
	
	public void rotateToFace(int dir){
		if(isRecovering()){
			System.out.println("cant move----------you'are recovering!!!");
			return;
		}
		
		if(dir==Vamp.NORTH || dir==Vamp.EAST ||
				dir==Vamp.SOUTH || dir==Vamp.WEST){
			facing=dir;
		}else{
			throw new IllegalArgumentException("invalid direction to face.");
		}
	}
	
	public void fight(Vamp player){
	}
	//-----------------------------------------------------//
	
	
	
	
	
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	//---------------------Inventory-----------------------//
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	
	public List<Collectable> getInventory(){
		return this.inventory;
	}
	

	public void collectItem(Collectable item){
		if(!isInventoryfull()){
			this.getInventory().add(item);
		}
	}
	
	/**
	 * Is the inventory full?
	 * @author - Raul John De Guzman
	 */
	public boolean isInventoryfull(){
		return this.getInventory().size() == 5;
	}
	
	/**
	 * This is to remove and return a specific item type 
	 * @author - Raul John De Guzman
	 */	
	public Collectable removeItem(Collectable c){
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
	 * @author - Raul John De Guzman
	 */
	public boolean hasAllOrbs(){
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
	
	
	//-----------------------------------------------------//
	
	
	
	
	
	//-----------------------------------------------------//
	//-----------------------------------------------------//
	//--------------------Networking-----------------------//
	//-----------------------------------------------------//
	//-----------------------------------------------------//
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

	//-----------------------------------------------------//

	
	
	
	
	
}
