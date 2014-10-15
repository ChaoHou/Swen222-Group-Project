package ui;

import gameworld.GameCharacter;
import gameworld.Room;
import gameworld.Vamp;
import gameworld.Werewolf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.media.opengl.GL;

import com.jogamp.opengl.util.texture.Texture;

import control.WerewolfThread;

public class Board {
	
	
	 private Set<Vamp> vamps=new HashSet<Vamp>();
	 private WerewolfThread werewolfThread;
	 
	 /* made the rooms contain characters to reduce coupling */
	 private Room[][] rooms=new Room[4][4];
	 private Room startRoom;
	 private Room exit;
	 private Room respawnRoom;
	 private int uid=0;
	 private boolean gameOver;
	
	 
	 
	
	public Board(Room[][] rooms){
		this.setRooms(rooms);
		startRoom = rooms[0][1];
		exit=rooms[3][1];
		respawnRoom=startRoom;
	}
	

	
	/*
	 * Start game by respawning all vamps in startroom and unleashing the werewolf.
	 */
	public void startGame(){
		for(Vamp vamp:vamps){
			vamp.respawn();
		}
		Werewolf werewolf=new Werewolf(this);
		registerWerewolf(werewolf);
		this.werewolfThread=new WerewolfThread(werewolf);
		this.werewolfThread.start();
		
		
	}
	
	/*
	 * Determines if there is a room ahead of this room (facing this particular direction). 
	 */
	public synchronized Room getRoomAhead(Room currentRoom, int dir){
		if(!checkValidDirection(dir) || currentRoom==null){
			throw new IllegalArgumentException();
		}
		
		Room room=checkForSecretPathway(currentRoom, dir);
		if(room!=null){
			return room;
		}
		
		
		int dx=0;
		int dy=0;
		
		switch (dir){
			case Room.NORTH:
				dy=-1;
				break;
			case Room.EAST:
				dx=1;
				break;
			case Room.SOUTH:
				dy=1;
				break;
			case Room.WEST:
				dx=-1;
				break;
			default:
				break;
		}
		
		for(int i=0;i<getRooms().length;i++){
			for(int j=0;j<getRooms()[0].length;j++){
				if(getRooms()[i][j]==currentRoom){
					if(canEnter(i+dy,j+dx)){
						return getRooms()[i+dy][j+dx];
					}else{
						return null;
					}
				}
			}
		
		}
		
		throw new IllegalArgumentException("no matching room");
		
	}
	
	private boolean canEnter(int i, int j){
		if(i<0 || i>=getRooms().length || j<0 || j>=getRooms()[0].length ||  getRooms()[i][j]==null){
			return false;
		}else{
			return true;
		}
	}
	
	private Room checkForSecretPathway(Room room, int dir){
		String roomName=room.toString();
		String roomToEnter=null;
		
		if(roomName.equals("bathroom")){
			if(dir==Room.SOUTH){
				roomToEnter="exit";
			}
		}else if(roomName.equals("exit")){
			if(dir==Room.WEST){
				roomToEnter="bathroom";
			}
		}else if(roomName.equals("dungeon")){
			if(dir==Room.SOUTH){
				roomToEnter="diningarea";
			}
		}else if(roomName.equals("diningarea")){
			if(dir==Room.EAST){
				roomToEnter="dungeon";
			}
		}else{
			return null;
		}
		
		
		if(roomToEnter==null){
			return null;
		}else{
			return getRoom(roomToEnter);
		}
		
		
		
	}
	
	
	public Room getRoom(String room){
		if(room==null){
			throw new IllegalArgumentException();
		}
		
		for(int i=0;i<rooms.length;i++){
			for(int j=0;j<rooms[0].length;j++){
				Room currentRoom=rooms[i][j];
				if(currentRoom!=null && currentRoom.toString().equals(room)){
					return currentRoom;
				}
			}
		}
		
		
		throw new IllegalArgumentException();
	}
	
	
	private boolean checkValidDirection(int dir){
		if(dir!=Room.NORTH && dir!=Room.EAST && 
				dir!=Room.SOUTH && dir!=Room.WEST){
			return false;
		}
		return true;
	}
	
	
	
	
	
	//
	//
	//
	// Game characters.
	//
	//
	//
	//
	public synchronized int registerVamp(){
		Vamp newVamp=new Vamp(uid, this);
		startRoom.playerEnterRoom(newVamp);
		vamps.add(newVamp);
		return uid++;
	}
	
	public synchronized void registerWerewolf(Werewolf werewolf){
		exit.werewolfEnterRoom(werewolf);
	}
	
	public synchronized Vamp getVamp(int uid){
		//loop through rooms to find the character
		for(Vamp vamp:getVamps()){
			if(vamp.getUid()==uid){
				return vamp;
			}
		}
		
		
		throw new IllegalArgumentException("invalid uid passed in.");
	}

	public Set<Vamp> getVamps(){
		return this.vamps;
	}

	public synchronized Room getRoomContainingPlayer(Vamp player){
		for(Room[] row:getRooms()){
			for(Room r:row){
				if(r == null){
					continue;
				}
				if(r.getVamps().contains(player)){
					return r;
				}
			}
		}
		throw new IllegalArgumentException("invalid uid passed in.");
	}
	
	
	
	//
	//
	//
	// Rooms.s
	//
	//
	//
	//
	public Room getRoomContainingWerewolf(){
		for(Room[] row:getRooms()){
			for(Room r:row){
				if(r == null){
					continue;
				}
				if(r.getWerewolf() != null){
					return r;
				}
			}
		}
		throw new IllegalArgumentException("no werewolf in the game");
	}
	
	public Room getRespawnRoom(){
		return this.respawnRoom;
	}
	
	public Room[][] getRooms() {
		return rooms;
	}

	public void setRooms(Room[][] rooms) {
		this.rooms = rooms;
	}
	
	public void initRooms(GL gl,Texture[] textures){
		for(Room[] row:rooms){
			for(Room r:row){
				if(r != null){
					r.init(gl,textures);
				}
			}
		}
	}
	
	
	public boolean isGameOver(){
		return false;
	}
	
	//
	//
	//
	// Networking.
	//
	//
	//
	//
	public synchronized byte[] toByteArray() throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		
		for(Room[] row:getRooms()){
			for(Room room:row){
				if(room != null){
					room.toOutputStream(dout);
				}
			}
		}
		
		dout.flush();

		return bout.toByteArray();
	}
	
	public synchronized void fromByteArray(byte[] bytes) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		
		for(Room[] row:getRooms()){
			for(Room room:row){
				if(room != null){
					room.fromInputStream(din, this);
				}
			}
		}
	}


	
	
	
	
	
}
