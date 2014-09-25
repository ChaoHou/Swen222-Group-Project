package GameWorld;

import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

public class Room {

	private Room[] roomsConnected=new Room[4];
	private Set<Container> containers=new HashSet<Container>();
	private Set<Player> players=new HashSet<Player>();
	
	public Room() {
	}
	

	public void removePlayer(Player player){
		this.players.remove(player);
	}
	
	public void addPlayer(Player player){
		this.players.add(player);
	}
	
	public Room getNorthRoom(){
		return roomsConnected[Character.NORTH];
	}
	
	
}