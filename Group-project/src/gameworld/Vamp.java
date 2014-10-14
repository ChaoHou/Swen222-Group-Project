package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ui.Board;


public class Vamp extends GameCharacter{
	
	
	public static final int DEAD=5;
	public static final int ALIVE=6;
	public static final int PARALYSED=7;
	public static final int RECOVERING=8;

	public static final int FULL_HEALTH=4;
	
	public static final long recoveryTime=3000;

	private int uid;

	
	
	private int status=ALIVE;
	private int health=4;
	private boolean isFighting;
	private boolean isTrading;
	private Set<Collectable> inventory=new HashSet<Collectable>();
	
	
	public Vamp(int uid, Board game){
		this.uid=uid;
		this.game=game;
	}


	
	public boolean hasAllOrbs(){
		boolean hasBlueOrb=false;
		boolean hasGreenOrb=false;
		boolean hasRedOrb=false;
		
		for(Collectable item:inventory){
			if(item instanceof Orb){
				int orbColour=((Orb)item).getColor();
				if(orbColour==Orb.BLUE){
					hasBlueOrb=true;
				}else if(orbColour==Orb.GREEN){
					hasGreenOrb=true;
				}else if(orbColour==Orb.RED){
					hasRedOrb=true;
				}
			}
		}
		return (hasBlueOrb&&hasGreenOrb&&hasRedOrb);
	}

	public void fight(Vamp player){
		
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

	private void setTrading(boolean	 isTradig) {
		this.isTrading = isTradig;
	}
	
	public boolean isHiding(){
		return false;
	}
	
	public boolean isDead(){
		return getHealth()<=0 || this.status==Vamp.DEAD;
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
		}else if(status==Vamp.ALIVE || status==Vamp.PARALYSED){
			this.status=status;
		}else{
			throw new IllegalArgumentException("invalid status to set for Vamp.");
		}
	}
		
	public int getUid(){
		return this.uid;
	}
	
	
	
	
	//
	//
	//
	//
	// Moving.
	//
	//
	//
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
			System.out.println("entering "+roomToEnter+" from "+roomFrom);
			roomFrom.playerLeaveRoom(this);
			roomToEnter.playerEnterRoom(this);
			System.out.println("entered "+roomToEnter);
			return true;
		}
	}
	
	public void rotateToFace(int dir){
		System.out.println(status);
		if(isRecovering()){
			System.out.println("cant move----------you'are recovering!!!");
			return;
		}
		
		if(dir==Room.NORTH || dir==Room.EAST ||
				dir==Room.SOUTH || dir==Room.WEST){
			facing=dir;
			System.out.println("Player:"+uid+" now facing "+ getDirectionFacingInString());
		}else{
			throw new IllegalArgumentException("invalid direction to face.");
		}
	}
	
	public void respawn(){
		Room currentRoom=game.getRoomContainingPlayer(this);
		currentRoom.playerLeaveRoom(this);
		Room respawnRoom=game.getRespawnRoom();
		respawnRoom.playerEnterRoom(this);
		Thread thread=new Thread(){
			public void run(){
				try{
					Thread.sleep(recoveryTime/2);
					status=Vamp.RECOVERING;
					Thread.sleep(recoveryTime);
					status=Vamp.ALIVE;
					health=FULL_HEALTH/2;
					System.out.println("------------vamp"+getUid()+" has recovered---------------");
				}catch(Exception e){
					
				}
			}
		};
		thread.start();
		
	}
	

	
	
	
	
	//
	//
	//
	//inventory
	//
	//
	//
	//
	public Set<Collectable> getInventory(){
		return this.inventory;
	}
	
	public void collectItem(Collectable item){
		this.getInventory().add(item);
	}
	
	public void removeItem(Collectable item){
		this.getInventory().remove(item);
	}
	
	
	
	//
	//
	//
	//
	//
	//
	//
	//
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health){
		this.health=health;
	}
	
	public void deductHealth(int dx){
		this.health-=dx;
		if(this.health<=0){
			setStatus(Vamp.DEAD);
		}
		
	}
	
	
	
	//
	//
	//
	//
	//networking
	//
	//
	//
	
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


}
