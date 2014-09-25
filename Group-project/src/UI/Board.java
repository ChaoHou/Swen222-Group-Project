package UI;

import java.util.ArrayList;
import java.util.List;

import GameWorld.Character;
import GameWorld.Room;

public class Board {
	
	 private List<Character> characters=new ArrayList<Character>();
	 private Room[][] rooms=new Room[4][4];
	
	public Board(){
		initialieRooms();
		initialisePlayer();
	}
	
	private void initialisePlayer(){
		
	}
	
	
	private void initialieRooms() {
		//read rooms from file "roomConnection.txt"
		
	}


	
	public Character getCharacter(int uid){
		return new Character();
	}

}
