package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import rendering.Box;
import ui.Board;

import com.jogamp.opengl.util.texture.Texture;

/**
 * 
 * 
 * Holds items.
 * Can be opened by players.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class Container {
	
	public static final int DRAWER=0;
	public static final int TREASURE_CHEST=1;
	
	public static final int ITEM_TYPE_ORB = 0;
	public static final int ITEM_TYPE_HEALTHPACK = 1;
	
	//type of this containter, a drawer of a treasure chest.
	private int containerType;
	private List<Collectable> items=new ArrayList<Collectable>();
	
	private Box box;
	
	public Container(int containerType,int x,int y,int z,int index){
		this.containerType=containerType;
		
		box = new Box(new float[]{x,y,z},new float[]{1f,1f,1f},index);
	}

	public void addItem(Collectable item){
		getItems().add(item);
	}
	
	public void removeItem(Collectable item){
		getItems().remove(item);
	}
	
	public int getContainerType(){
		return this.containerType;
	}

	public List<Collectable> getItems() {
		return items;
	}

	public void setItems(List<Collectable> items) {
		this.items = items;
	}
	
	public Collectable remove(Collectable c){
		Collectable temp = null;
		//We'll need to iterate through the whole container for this...
		for(Collectable l : this.getItems()){
			//Is it an Orb? Are they the same kind of Orb?	
			if((c instanceof Orb && l instanceof Orb) && 
				((Orb) c).getColour() == ((Orb) l).getColour()){
				temp = (Orb) l;
				this.getItems().remove(l);
				return temp;
			}
			//Is it a HealthPack?
			else if(c instanceof HealthPack && l instanceof HealthPack){
				temp = (HealthPack) l;
				this.getItems().remove(l);
				return temp;
			}
		}
		return null;
	}
	
	public boolean containsPoint(GL2 gl,GLU glu, int x,int y,int dir){
		return box.containsPoint(gl, glu, x, y,dir);
	}
	
	public void init(GL gl,Texture[] textures) {
		box.init(gl, textures);
	}
	
	public void draw(GL2 gl,int facingDir){
		
		switch(containerType){
		case DRAWER:
			//drawCube(gl);
			break;
		case TREASURE_CHEST:
			//drawCube(gl);
			break;
		}
		
		
		box.draw(gl,facingDir);
	}
	
	public void toOutputStream(DataOutputStream dout) throws IOException {	
		dout.writeInt(items.size());
		for(int i=0;i<items.size();i++){
			Collectable item = items.get(i);
			if(item instanceof Orb){
				dout.writeInt(ITEM_TYPE_ORB);
			}else if(item instanceof HealthPack){
				dout.writeInt(ITEM_TYPE_HEALTHPACK);
			}
			item.toOutputStream(dout);
		}
	}
	
	public void fromInputStream(DataInputStream din,Board game) throws IOException {
		int size = din.readInt();
		for(int i=0;i<size;i++){
			int type = din.readInt();
			Collectable item = null;
			if(type == ITEM_TYPE_ORB){
				item = Orb.fromInputStream(din, game);
			}else if(type == ITEM_TYPE_HEALTHPACK){
				item = HealthPack.fromInputStream(din, game);
			}
			items.add(item);
		}
	}
}
