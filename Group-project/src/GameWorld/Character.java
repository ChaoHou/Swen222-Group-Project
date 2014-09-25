package GameWorld;

import UI.Board;

public class Character {
	
	public static final int NORTH=0;
	public static final int EAST=1;
	public static final int SOUTH=2;
	public static final int WEST=3;
	public static final int NO_DIRECTION=4;
	
	public static final int DEAD=5;
	public static final int ALIVE=6;
	public static final int PARALYSED=7;
	
	
	private Room inRoom;
	private int status=ALIVE;
	private int facing;
	
	
	
	public void rotateTo(int dir){
		if(dir==Character.NORTH || dir==Character.EAST || dir==Character.SOUTH || dir==Character.WEST){
			facing=dir;
		}else{
			System.out.println("invalid direction to face.");
		}
	}
	
	public void enterRoom(){
		Room roomToEnter=inRoom.getNorthRoom();
		
		if(roomToEnter==null){
			System.out.println("no room ahead");
		}else{
			System.out.println("entering room");
			inRoom=roomToEnter;
			System.out.println("entered room");
		}
	}
	
	
}
