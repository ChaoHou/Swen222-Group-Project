package GameWorld;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import UI.Board;

public class Room {


	private Set<Container> containers=new HashSet<Container>();
	private Set<Vamp> players = new HashSet<Vamp>();
	private Werewolf werewolf = null;
	private String room;
	//also contains walls?
	//wall contains furniture?
	
	public Room(String room) {
		this.room=room;
	}
	
	

	public void playerEnterRoom(Vamp c){
		this.players.add(c);
	}
	
	public void playerLeaveRoom(Vamp c){
		this.players.remove(c);
	}
	
	public void npcEnterRoom(Werewolf w){
		werewolf = w;
	}
	
	public Werewolf npcLeaveRoom(Werewolf w){
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
	
