package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ui.Board;

public abstract class GameCharacter {
	
	protected int facing;
	//protected Room inRoom; //delete to reduce coupling
	protected Board game;
	
	public abstract boolean enterRoom();
	public abstract void rotateToFace(int dir);
	
	public GameCharacter(){
		facing=Room.SOUTH;
	}
	
	public String getDirectionFacingInString(){
		switch(facing){
		case Room.NORTH:
			return "north";
		case Room.EAST:
			return "east";
		case Room.SOUTH:
			return "south";
		case Room.WEST:
			return "west";
		default:
			return "never returning this";
		}
			
	}
	
	public void setDirectionFacing(int dir){
		facing=dir;
	}
	
	public int getDirectionFacing(){
		return facing;
	}
	
	
	
	public abstract void toOutputStream(DataOutputStream dout) throws IOException;
	
	
}
