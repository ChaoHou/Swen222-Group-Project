package gameworld;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ui.Board;

public class Room {

	public static final int NORTH=0;
	public static final int EAST=1;
	public static final int SOUTH=2;
	public static final int WEST=3;

	private List<Wall> walls=new ArrayList<Wall>();
	private Set<Furniture> furniture=new HashSet<Furniture>();
	private Set<Container> containers=new HashSet<Container>();
	private Set<Vamp> players = new HashSet<Vamp>();
	private Werewolf werewolf = null;
	private String room;
	
	
	
	public Room(String room) {
		this.room=room;
	}
	
	

	public void playerLeaveRoom(Vamp c){
		this.players.remove(c);
	}
	
	public void playerEnterRoom(Vamp c){
		this.players.add(c);
	}
	
	public void werewolfEnterRoom(Werewolf w){
		werewolf = w;
	}
	
	public Werewolf werewolfLeaveRoom(Werewolf w){
		Werewolf temp = werewolf;
		werewolf = null;
		return temp;
	}
	
	
	public Werewolf getWerewolf(){
		return werewolf;
	}
	
	public void addContainer(Container container){
		this.containers.add(container);
	}
	
	public void addFurniture(Furniture furniture){
		this.furniture.add(furniture);
	}
	
	//returns a set of vamps in this room.
	//returns an empty set if no vamps in this room.
	public Set<Vamp> getVamps(){
		return players;
	}
	
	@Override
	public String toString(){
		return this.room;
	}
	
	
	public void toOutputStream(DataOutputStream dout) throws IOException {		
		dout.writeInt(players.size());
		for(Vamp p:players){
			p.toOutputStream(dout);
		}
		if(werewolf != null){
			dout.writeBoolean(true);
			werewolf.toOutputStream(dout);
		}else{
			dout.writeBoolean(false);
		}
	}
	
	public void fromInputStream(DataInputStream din,Board game) throws IOException {
		int size = din.readInt();
		players.clear();
		for(int i=0;i<size;i++){
			Vamp temp = Vamp.fromInputStream(din, game);
			players.add(temp);
		}
		boolean hasNPC = din.readBoolean();
		if(hasNPC){
			werewolf = Werewolf.fromInputStream(din, game);
		}
	}
	
	
	
	}
	
