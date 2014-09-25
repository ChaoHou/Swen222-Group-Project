package GameWorld; 

public class Player {

	public static final int NORTH=0;
	public static final int EAST=1;
	public static final int SOUTH=2;
	public static final int WEST=3;
	
	private int facing;
	private int health;
	private Room inRoom;
	private boolean isFighting;
	private boolean isTrading;
	private Furniture hidingIn;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public void collect(Collectable collectable){
		
		
	}
	
	public void setDirectionFacing(int dir){
		facing=dir;
	}
	
	public int getDirectionFacing(){
		return facing;
	}
	
	public void fight(Player player){
		
	}
	
	public void hideIn(Furniture furniture){
		furniture.hidePlayer(this);
		this.hidingIn=furniture;
	}
	
	public void enterRoom(Room room){
		inRoom.removePlayer(this);
		room.addPlayer(this);
		this.inRoom=room;
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

	
	
}

