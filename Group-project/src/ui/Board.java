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

import control.WerewolfThread;

public class Board {
	
	
	 private Set<Vamp> vamps=new HashSet<Vamp>();
	 private WerewolfThread werewolfThread;
	 
	 /* made the rooms contain characters to reduce coupling */
	 private Room[][] rooms=new Room[4][4];
	 private Room startRoom;
	 private int uid=0;
	 
	 
	 
	
	public Board(Room[][] rooms){
		this.setRooms(rooms);
		startRoom = rooms[0][1];
	}
	
	
	/*
	 * Start game by respawning all vamps in startroom and unleashing the werewolf.
	 */
	public void startGame(){
		System.out.println("isEmpty vamps list: "+vamps.isEmpty());
		for(Vamp vamp:vamps){
			vamp.respawn(startRoom);
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
		
		int dx=0;
		int dy=0;
		
		switch (dir){
			case GameCharacter.NORTH:
				dy=-1;
				break;
			case GameCharacter.EAST:
				dx=1;
				break;
			case GameCharacter.SOUTH:
				dy=1;
				break;
			case GameCharacter.WEST:
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
	
	
	/*
	 * Determines if rooms[i][j] is actually a Room (that can be entered).
	 */
	private boolean canEnter(int i, int j){
		if(i<0 || i>=getRooms().length || j<0 || j>=getRooms()[0].length ||  getRooms()[i][j]==null){
			return false;
		}else{
			return true;
		}
	}
	
	
	public synchronized int registerVamp(){
		System.out.println("in registerVamp()");
		Vamp newVamp=new Vamp(uid, this);
		startRoom.playerEnterRoom(newVamp);
		vamps.add(newVamp);
		return uid++;
	}
	
	public synchronized void registerWerewolf(Werewolf werewolf){
		startRoom.werewolfEnterRoom(werewolf);
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
		throw new IllegalArgumentException("invalid uid passed in.");
	}
	
	
	
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


	public Room[][] getRooms() {
		return rooms;
	}

	public Set<Vamp> getVamps(){
		return this.vamps;
	}

	public void setRooms(Room[][] rooms) {
		this.rooms = rooms;
	}
}
