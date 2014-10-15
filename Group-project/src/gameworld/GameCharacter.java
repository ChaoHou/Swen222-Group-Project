package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ui.Board;

public abstract class GameCharacter {
	public static final int NORTH=0;
	public static final int EAST=1;
	public static final int SOUTH=2;
	public static final int WEST=3;
	public static final int NO_DIRECTION=4;
	
	protected int facing;
	//protected Room inRoom; //delete to reduce coupling
	protected Board game;
	
	public abstract void rotateTo(int dir);
	
	
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
	
	public synchronized void setDirectionFacing(int dir){
		facing=dir;
	}
	
	public synchronized int getDirectionFacing(){
		return facing;
	}
	
	public abstract boolean enterRoom();
	
	public abstract void toOutputStream(DataOutputStream dout) throws IOException;
	
	
}
