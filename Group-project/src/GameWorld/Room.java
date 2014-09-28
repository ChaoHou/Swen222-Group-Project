package GameWorld;

import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

public class Room {


	private Set<Container> containers=new HashSet<Container>();
	private Set<GameCharacter> gameCharacters=new HashSet<GameCharacter>();
	private String room;
	
	public Room(String room) {
		this.room=room;
	}
	
	

	public void removePlayer(GameCharacter c){
		this.gameCharacters.remove(c);
	}
	
	public void addGameCharacter(GameCharacter c){
		this.gameCharacters.add(c);
	}
	
	
	
	
	public void addContainer(Container container){
		this.containers.add(container);
	}
	
	
	//returns a set of vamps in this room.
	//returns an empty set if no vamps in this room.
	public Set<Vamp> getVamps(){
		Set<Vamp> vamps=new HashSet<Vamp>();
		for(GameCharacter c:gameCharacters){
			if(c instanceof Vamp){
				vamps.add((Vamp)c);
			}
		}
		return vamps;
	}
	
	@Override
	public String toString(){
		return this.room;
	}
	}
	
