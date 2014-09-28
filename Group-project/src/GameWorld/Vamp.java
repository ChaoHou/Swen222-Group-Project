package GameWorld;

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
	private Furniture hidingIn;
	
	
	public Vamp(int uid, Board game){
		this.uid=uid;
		this.game=game;
	}

	public void collect(Collectable collectable){
		
		
	}
	
	public void setDirectionFacing(int dir){
		facing=dir;
	}
	
	public int getDirectionFacing(){
		return facing;
	}
	
	public void fight(Vamp player){
		
	}
	
	public void hideIn(Furniture furniture){
		furniture.hidePlayer(this);
		this.hidingIn=furniture;
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
	
	public int getUid(){
		return this.uid;
	}
	
	public void respawn(Room room){
		room.addGameCharacter(this);
		this.inRoom=room;
		System.out.println("you are respawned, in "+inRoom);
	}
	
	
	
	
}
