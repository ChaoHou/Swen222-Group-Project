package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import rendering.primitive.Box;

import com.jogamp.opengl.util.texture.Texture;


import ui.Board;


/**
 * 
 * 
 * Vamps are able to hide in this class.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class Furniture {

	public static final int BED=0;
	public static final int CLOSET=1;
	public static final int CURTAIN=2;
	
	private int furnitureType;
	private Vamp hidingPlayer;
	
	private Box box;
	
	public Furniture(int furnitureType,int x,int y,int z,int index) {
		this.furnitureType=furnitureType;
		
		box = new Box(new float[]{x,y,z},new float[]{3f,3f,3f},index);
	}
	
	public void hidePlayer(Vamp player){
		hidingPlayer=player;
	}
	
	public Vamp getHidingPlayer(){
		return this.hidingPlayer;
	}
	
	public Vamp getOutFromFurniture(){
		Vamp vamp = hidingPlayer;
		hidingPlayer=null;
		return vamp;
	}
	
	public int getFurnitureType(){
		return this.furnitureType;
	}

    /**
     *@author Chao
     * @param gl
     * @param facingDir
     * done by Chao
     */
	public void render(GL2 gl, int facingDir){
		
		switch(furnitureType){
		case CLOSET:
			//drawCube(gl);
			break;
		case CURTAIN:
			//drawCube(gl);
			break;
		}
		
		
		box.render(gl);
	}
	 /**
	  * @author Chao
	  * @param gl
	  * @param textures
	  */
	public void init(GL gl,Texture[] textures) {
		box.init(textures);
	}
	
	/**
	 * @author Chao
	 * 
	 * @param gl
	 * @param glu
	 * @param x
	 * @param y
	 * @param dir
	 * @return
	 */
	public boolean containsPoint(GL2 gl,GLU glu, int x,int y,int dir){
		//System.out.println("selecting furniture");
//		System.out.println(box.containsPoint(gl, glu, x,y));
		return box.containsPoint(gl, glu, x, y);
	}
	/**
	 * @author Chao
	 * @param dout
	 * @throws IOException
	 */
	public void toOutputStream(DataOutputStream dout) throws IOException{
		if(hidingPlayer != null){
			dout.writeBoolean(true);
			hidingPlayer.toOutputStream(dout);
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
		boolean hasPlayer = din.readBoolean();
		if(hasPlayer){
			hidingPlayer = Vamp.fromInputStream(din, game);
		}else{
			hidingPlayer = null;
		}
	}
	
}
