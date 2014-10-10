package gameworld;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

public class Container {
	
	public static final int DRAWER=0;
	public static final int TREASURE_CHEST=1;
	
	//type of this containter, a drawer of a treasure chest.
	private int containerType;
	private List<Collectable> items=new ArrayList<Collectable>();
	
	private Texture[] textures;
	private int textureIndex;
	private int x;
	private int y;
	private int z;
	private float width = 1f;
	private float top;
	private float bottom;
	private float left;
	private float right;
	
	public Container(int containerType,int x,int y,int z,int index){
		this.containerType=containerType;
		this.x = x;
		this.y = y;
		this.z = z;
		textureIndex = index;
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
		this.textures = textures;
		top = textures[textureIndex].getImageTexCoords().top();
		bottom = textures[textureIndex].getImageTexCoords().bottom();
		left = textures[textureIndex].getImageTexCoords().left();
		right = textures[textureIndex].getImageTexCoords().right();
		//System.out.println("top:"+top);
	}
	
	public void draw(GL2 gl,int facingDir){
		gl.glLoadIdentity();
		
		
		float rquad = Wall.getDir(facingDir);
		gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(x, y, z);
		switch(containerType){
		case DRAWER:
			drawCube(gl);
			break;
		case TREASURE_CHEST:
			drawCube(gl);
			break;
		}
	}
	
	private void drawCube(GL2 gl){
		//System.out.println("Dawn cube");
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		textures[textureIndex].enable(gl);
		textures[textureIndex].bind(gl);
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(left, top);
		gl.glVertex3f(-width, width, width); // Top Left Of The Quad (Front)
		gl.glTexCoord2f(right, top);
		gl.glVertex3f(width, width, width); // Top Right Of The Quad (Front)
		gl.glTexCoord2f(right, bottom);
		gl.glVertex3f(width, -width, width); // Bottom Right Of The Quad (Front)
		gl.glTexCoord2f(left, bottom);
		gl.glVertex3f(-width, -width, width); // Bottom Left Of The Quad (Front)
		
		gl.glTexCoord2f(left, top);
		gl.glVertex3f(width, width, -width); // Top Left Of The Quad (Back)
		gl.glTexCoord2f(right, top);
		gl.glVertex3f(-width, width, -width); // Top Right Of The Quad (Back)
		gl.glTexCoord2f(right, bottom);
		gl.glVertex3f(-width, -width, -width); // Bottom Right Of The Quad (Back)
		gl.glTexCoord2f(left, bottom);
		gl.glVertex3f(width, -width, -width); // Bottom Left Of The Quad (Back)
		
		gl.glTexCoord2f(left, top);
		gl.glVertex3f(-width, width, -width); // Top Left Of The Quad (Left)
		gl.glTexCoord2f(right, top);
		gl.glVertex3f(-width, width, width); // Top Right Of The Quad (Left)
		gl.glTexCoord2f(right, bottom);
		gl.glVertex3f(-width, -width, width); // Bottom Right Of The Quad (Left)
		gl.glTexCoord2f(left, bottom);
		gl.glVertex3f(-width, -width, -width); // Bottom Left Of The Quad (Left)
		
		gl.glTexCoord2f(left, top);
		gl.glVertex3f(width, width, width); // Top Left Of The Quad (Right)
		gl.glTexCoord2f(right, top);
		gl.glVertex3f(width, width, -width); // Top Right Of The Quad (Right)
		gl.glTexCoord2f(right, bottom);
		gl.glVertex3f(width, -width, -width); // Bottom Right Of The Quad (Right)
		gl.glTexCoord2f(left, bottom);
		gl.glVertex3f(width, -width, width); // Bottom Left Of The Quad (Right)
		
		gl.glTexCoord2f(left, top);
		gl.glVertex3f(-width, width, -width);  // Top Left Of The Quad (Top)
		gl.glTexCoord2f(right, top);
		gl.glVertex3f(width, width, -width);   // Top Right Of The Quad (Top)
		gl.glTexCoord2f(right, bottom);
		gl.glVertex3f(width, width, width);    // Bottom Right Of The Quad (Top)
		gl.glTexCoord2f(left, bottom);
        gl.glVertex3f(-width, width, width);   // Bottom Left Of The Quad (Top)
        
        gl.glTexCoord2f(left, top);
        gl.glVertex3f(-width, -width, width);  // Top Left Of The Quad (Bottom)
        gl.glTexCoord2f(right, top);
        gl.glVertex3f(width, -width, width);   // Top Right Of The Quad (Bottom)
        gl.glTexCoord2f(right, bottom);
        gl.glVertex3f(width, -width, -width);  // Bottom Right Of The Quad (Bottom)
        gl.glTexCoord2f(left, bottom);
        gl.glVertex3f(-width, -width, -width); // Bottom Left Of The Quad (Bottom)
        
        
		
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
}
