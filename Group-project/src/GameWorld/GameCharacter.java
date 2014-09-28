package GameWorld;

import UI.Board;

public class GameCharacter {
	public static final int NORTH=0;
	public static final int EAST=1;
	public static final int SOUTH=2;
	public static final int WEST=3;
	public static final int NO_DIRECTION=4;
	
	protected int facing;
	protected Room inRoom;
	protected Board game;
	
	public void rotateTo(int dir){
		if(dir==Vamp.NORTH || dir==Vamp.EAST ||
				dir==Vamp.SOUTH || dir==Vamp.WEST){
			facing=dir;
			System.out.println("now facing "+ intDirToString());
		}else{
			throw new IllegalArgumentException("invalid direction to face.");
		}
	}
	
	
	public String intDirToString(){
		switch(facing){
		case GameCharacter.NORTH:
			return "north";
		case GameCharacter.EAST:
			return "east";
		case GameCharacter.SOUTH:
			return "south";
		case GameCharacter.WEST:
			return "west";
		default:
			return "never returning this";
		}
			
	}
	public boolean enterRoom(){
		Room roomToEnter=game.getRoomAhead(inRoom, facing);
		
		if(roomToEnter==null){
			System.out.println("no room ahead");
			return false;
		}else{
			System.out.println("entering "+roomToEnter+" from "+inRoom);
			inRoom=roomToEnter;
			System.out.println("entered "+roomToEnter);
			return true;
		}
	}
	
}
