package gameworld;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import rendering.Box;

import com.jogamp.opengl.util.texture.Texture;

public class Container {
	
	public static final int DRAWER=0;
	public static final int TREASURE_CHEST=1;
	
	//type of this containter, a drawer of a treasure chest.
	private int containerType;
	private List<Collectable> items=new ArrayList<Collectable>();
	
	//private Texture[] textures;
	//private int textureIndex;
//	private int x;
//	private int y;
//	private int z;
	//private float width = 1f;
//	private float top;
//	private float bottom;
//	private float left;
//	private float right;
	
	private Box box;
	
	public Container(int containerType,int x,int y,int z,int index){
		this.containerType=containerType;
		//this.x = x;
		//this.y = y;
		//this.z = z;
		
		box = new Box(new float[]{x,y,z},new float[]{1f,2f,3f},index);
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
	
	public void init(GL gl,Texture[] textures) {
//		this.textures = textures;
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
}
