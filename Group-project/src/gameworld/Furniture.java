package gameworld;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;

import rendering.Box;

public class Furniture {

	public static final int BED=0;
	public static final int CLOSET=1;
	public static final int CURTAIN=2;
	
	private int furnitureType;
	private Vamp hidingPlayer;
	
	private Box box;
	
	public Furniture(int furnitureType,int x,int y,int z,int index) {
		this.furnitureType=furnitureType;
		
		box = new Box(new float[]{x,y,z},new float[]{2f,2f,2f},index);
	}
	
	public void hidePlayer(Vamp player){
		hidingPlayer=player;
	}
	
	public Vamp getHidingPlayer(){
		return this.hidingPlayer;
	}
	
	public void removePlayer(Vamp player){
		hidingPlayer=null;
	}
	
	public int getFurnitureType(){
		return this.furnitureType;
	}
	
	public void draw(GL2 gl,int facingDir){
		
		switch(furnitureType){
		case CLOSET:
			//drawCube(gl);
			break;
		case CURTAIN:
			//drawCube(gl);
			break;
		}
		
		
		box.draw(gl,facingDir);
	}
	 
	public void init(GL gl,Texture[] textures) {
		box.init(gl, textures);
	}
	
	public boolean containsPoint(GL2 gl,GLU glu, int x,int y,int dir){
		System.out.println("selecting furniture");
//		System.out.println(box.containsPoint(gl, glu, x,y));
		return box.containsPoint(gl, glu, x, y,dir);
	}

}
