package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ui.Board;

/**
 * 
 * 
 * Player plays a Vamp.
 * Player respawns when they meet the werewolf in the same room.
 * Player collects 3 orbs of different colours and makes their way to exit to win the game.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
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
		collectItem(new HealthPack());
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

	public boolean isAlive(){
		return this.status==Vamp.ALIVE;
	}
	
	public boolean isRecovering(){
		return this.status==Vamp.RECOVERING;
	}
	
	public void setStatus(int status,boolean isSinglePlayer){
		if(status==Vamp.DEAD){
			health=0;
			this.status=status;
			if(isSinglePlayer){
				respawn();
			}
			
		}else if(status==Vamp.ALIVE || status==Vamp.PARALYSED){
			this.status=status;
		}else if(status==Vamp.RECOVERING){
			this.health=FULL_HEALTH/2;
		}else{
		
			throw new IllegalArgumentException("invalid status to set for Vamp.");
		}
	}
	
	public int getStatus(){
		return this.status;
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
			setStatus(Vamp.DEAD,true);
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
					setStatus(Vamp.RECOVERING,true);
					Thread.sleep(Vamp.RECOVERY_TIME);
					setStatus(Vamp.ALIVE,true);
					setHealth(FULL_HEALTH/2);
					System.out.println("------------vamp"+getUid()+" has recovered---------------");
				}catch(Exception e){
					
				}
			}
		};
		thread.start();
	}
	
	public boolean enterRoom(){
		if(isRecovering() || isDead()){
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
		if(isRecovering() || isDead()){
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
	
	
	public Collectable removeItem(Collectable c){
		for(Collectable item:this.getInventory()){
			if(c==item){
				this.inventory.remove(item);
				return c; 
			}
		}
		throw new IllegalArgumentException("item does not exist in the game.");
		
	}
	
	
	/**
	 * If the player has all 3 of each orb, he may win the game!
	 * 
	 * @author - Raul John De Guzman
	 */
	public boolean hasAllOrbs(){
		boolean hasBlue = false, hasGreen = false, hasRed = false;
		
		for(Collectable item : this.getInventory()){
			if(item instanceof Orb){
				int colour=((Orb)item).getColour();
				if(colour==Orb.BLUE){
					hasBlue = true;
				}else if(colour==Orb.GREEN){
					hasGreen = true;
				}else if(colour==Orb.RED){
					hasRed = true;
				}
			}
		}
		System.out.println("size of inventory:"+this.inventory.size());
		return hasBlue && hasGreen && hasRed;
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
		dout.writeInt(inventory.size());
		for(Collectable c:inventory){
			if(c instanceof Orb){
				dout.writeInt(Container.ITEM_TYPE_ORB);
				c.toOutputStream(dout);
			}else if(c instanceof HealthPack){
				dout.writeInt(Container.ITEM_TYPE_HEALTHPACK);
			}
		}
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
		temp.setStatus(status,false);
		temp.setHealth(health);
		temp.setFighting(isFighting);
		temp.setTrading(isTrading);
		temp.getInventory().clear();
		int size = din.readInt();
		for(int i=0;i<size;i++){
			int type = din.readInt();
			if(type == Container.ITEM_TYPE_ORB){
				temp.collectItem(Orb.fromInputStream(din, game));
			}else if(type == Container.ITEM_TYPE_HEALTHPACK){
				temp.collectItem(new HealthPack());
			}
		}
		return temp;
	}

	//-----------------------------------------------------//

	
	
	
	
	
}
