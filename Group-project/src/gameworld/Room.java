package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import ui.Board;

/**
 * 
 * 
 * Room holds either a container or a furniture.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class Room {

	public static final int NORTH=0;
	public static final int EAST=1;
	public static final int SOUTH=2;
	public static final int WEST=3;

	private Wall[] walls;
	private Container container;
	private Furniture furniture;
	private Set<Vamp> players = new HashSet<Vamp>();
	private Werewolf werewolf = null;
	private String room;
	
	
	
	public Room(String room, Wall[] walls) {
		this.room=room;
		this.walls = walls;
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
	

	
	public void setContainer(Container container){
		this.container = container;
	}
	
	public Container getContainer(){
		return container;
	}
	
	public void setFurniture(Furniture furniture){
		this.furniture = furniture;
	}

	public Furniture getFurniture(){
		return furniture;
	}
	
	//returns a set of vamps in this room.
	//returns an empty set if no vamps in this room.
	public Set<Vamp> getVamps(){
		return players;
	}
	

	public void hideInFurniture(Vamp player) {
		if(furniture != null && players.contains(player)){
			furniture.hidePlayer(player);
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public void getOutFromFurniture(Vamp player){
		if(furniture != null){
			players.add(furniture.getOutFromFurniture());
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String toString(){
		return this.room;
	}

    /**
     *@author Chao
     * @param gl
     * @param dir
     * Done by Chao
     */
	public void render(GL2 gl, int dir){
		for(Wall wall:walls){
			wall.render(gl);
		}
		if(container != null){
			container.render(gl, dir);
		}
		if(furniture != null){
			furniture.render(gl, dir);
		}
	}
	

	/**
	 * @author Chao
	 * @param gl
	 * @param textures
	 */
	public void init(GL gl,Texture[] textures){

		for(Wall w:walls){
			w.init(gl,textures);
		}
		if(container != null){
			container.init(gl, textures);
		}
		if(furniture != null){
			furniture.init(gl, textures);
		}
	}
	
	/**
	 * @author Chao
	 * @param dout
	 * @throws IOException
	 */
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
		if(container != null){
			dout.writeBoolean(true);
			container.toOutputStream(dout);
		}else{
			dout.writeBoolean(false);
		}
		if(furniture != null){
			dout.writeBoolean(true);
			furniture.toOutputStream(dout);
		}else{
			dout.writeBoolean(false);
		}
			
		
	}
	
	/**
	 * @author Chao
	 * @param din
	 * @param game
	 * @throws IOException
	 */
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
		}else{
			werewolf = null;
		}
		boolean hasContainer = din.readBoolean();
		if(hasContainer){
			container.fromInputStream(din, game);
		}
		boolean hasFurniture = din.readBoolean();
		if(hasFurniture){
			furniture.fromInputStream(din, game);
		}
	}



	
	
	
	}
	
