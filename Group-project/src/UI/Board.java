package UI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GameWorld.GameCharacter;
import GameWorld.Room;
import GameWorld.Vamp;

public class Board {
	
	 //private List<Vamp> vamps=new ArrayList<Vamp>();
	
	 /* made the rooms contain characters to reduce coupling */
	 private Room[][] rooms=new Room[4][4];
	 private Room startRoom;
	 private int uid=0;
	 
	
	public Board(Room[][] rooms){
		this.rooms=rooms;
		startRoom = rooms[0][1];
	}
	
	
	public Room getRoomAhead(Room currentRoom, int dir){
		
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
		
		for(int i=0;i<rooms.length;i++){
			for(int j=0;j<rooms[0].length;j++){
				if(rooms[i][j]==currentRoom){
					if(canEnter(i+dy,j+dx)){
						return rooms[i+dy][j+dx];
					}else{
						return null;
					}
				}
			}
		
		}
		
		throw new IllegalArgumentException("no matching room");
		
	}
	
	
	//determines if room (coordinate as argument) can be entered. 
	private boolean canEnter(int i, int j){
		if(i<0 || i>=rooms.length || j<0 || j>=rooms[0].length ||  rooms[i][j]==null){
			return false;
		}else{
			return true;
		}
	}
	
	public int registerVamp(){
		System.out.println("in registerVamp()");
		startRoom.playerEnterRoom(new Vamp(uid,this));
//		vamps.add(new Vamp(uid, this));
		return uid++;
	}
	
	
	public Vamp getCharacter(int uid){
//		for(Vamp vamp:this.vamps){
//			if(vamp.getUid()==uid){
//				return vamp;
//			}
//		}
		//loop through rooms to find the character
		for(Room[] row:rooms){
			for(Room r:row){
				if(r == null){
					continue;
				}
				for(Vamp v:r.getVamps()){
					if(v.getUid() == uid){
						return v;
					}
				}
			}
		}
		
		throw new IllegalArgumentException("invalid uid passed in.");
	}

	public Room getRoomContainsPlayer(Vamp player){
		for(Room[] row:rooms){
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
	
	public Room getRoomContainsNPC(){
		for(Room[] row:rooms){
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
	
	//method for werewolves to enter random rooms.
	public void enterRandomRoom(){
		
	}
	
	public void startGame(){
//		System.out.println("isEmpty vamps list: "+vamps.isEmpty());
//		for(Vamp vamp:vamps){
//			vamp.respawn(rooms[0][1]);
//		}
	}
	
	public synchronized byte[] toByteArray() throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		
		for(Room[] row:rooms){
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
		
		for(Room[] row:rooms){
			for(Room room:row){
				if(room != null){
					room.fromInputStream(din, this);
				}
			}
		}
	}
}
