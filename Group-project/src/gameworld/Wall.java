package gameworld;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Wall {
	public static final int NORTH_WALL = 0;
	public static final int WEST_WALL = 1;
	public static final int SOUTH_WALL = 2;
	public static final int EAST_WALL = 3;

	private float x = 10.0f;
	private float y = 10.0f;
	private float z = 10.0f;
	//private float rquad = 0.0f;

	private int dir;
	private int textureIndex;
	private float top;
	private float bottom;
	private float left;
	private float right;

	private Texture[] textures;

	public Wall(int dir, int index) {
		this.dir = dir;
		this.textureIndex = index;
	}

	public void init(GL gl,Texture[] textures) {
		this.textures = textures;
		top = textures[textureIndex].getImageTexCoords().top();
		bottom = textures[textureIndex].getImageTexCoords().bottom();
		left = textures[textureIndex].getImageTexCoords().left();
		right = textures[textureIndex].getImageTexCoords().right();
		System.out.println("top:"+top);
	}

	public void draw(GL2 gl,int facingDir) {
		
		float rquad = getDir(facingDir);
		//System.out.println(rquad);
		gl.glLoadIdentity();
		gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		textures[textureIndex].enable(gl);
		textures[textureIndex].bind(gl);
		gl.glBegin(GL2.GL_QUADS);
		
		switch (dir) {
		case NORTH_WALL:
			//gl.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
			gl.glTexCoord2f(left, bottom);
			gl.glVertex3f(-x, -y, z); // Bottom Left Of The Quad (Front)
			gl.glTexCoord2f(right, bottom);
			gl.glVertex3f(x, -y, z); // Bottom Right Of The Quad (Front)
			gl.glTexCoord2f(right, top);
			gl.glVertex3f(x, y, z); // Top Right Of The Quad (Front)
			gl.glTexCoord2f(left, top);
			gl.glVertex3f(-x, y, z); // Top Left Of The Quad (Front)
			break;

		case SOUTH_WALL:
			gl.glNormal3f(-1.0f, 0.0f, 0.0f);
			//gl.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
			gl.glTexCoord2f(left, bottom);
			gl.glVertex3f(x, -y, -z); // Bottom Left Of The Quad (Back)
			gl.glTexCoord2f(right, bottom);
			gl.glVertex3f(-x, -y, -z); // Bottom Right Of The Quad (Back)
			gl.glTexCoord2f(right, top);
			gl.glVertex3f(-x, y, -z); // Top Right Of The Quad (Back)
			gl.glTexCoord2f(left, top);
			gl.glVertex3f(x, y, -z); // Top Left Of The Quad (Back)
			break;

		case WEST_WALL:
			gl.glNormal3f(-1.0f, 0.0f, 0.0f);
			//gl.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
			gl.glTexCoord2f(left, bottom);
			gl.glVertex3f(-x, -y, -z); // Bottom Left Of The Quad (Left)
			gl.glTexCoord2f(right, bottom);
			gl.glVertex3f(-x, -y, z); // Bottom Right Of The Quad (Left)
			gl.glTexCoord2f(right, top);
			gl.glVertex3f(-x, y, z); // Top Right Of The Quad (Left)
			gl.glTexCoord2f(left, top);
			gl.glVertex3f(-x, y, -z); // Top Left Of The Quad (Left)
			break;

		case EAST_WALL:
			gl.glNormal3f(-1.0f, 0.0f, 0.0f);
			//gl.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
			gl.glTexCoord2f(left, bottom);
			gl.glVertex3f(x, -y, z); // Bottom Left Of The Quad (Right)
			gl.glTexCoord2f(right, bottom);
			gl.glVertex3f(x, -y, -z); // Bottom Right Of The Quad (Right)
			gl.glTexCoord2f(right, top);
			gl.glVertex3f(x, y, -z); // Top Right Of The Quad (Right)
			gl.glTexCoord2f(left, top);
			gl.glVertex3f(x, y, z); // Top Left Of The Quad (Right)
			break;
		}
		
		//textures[textureIndex].disable(gl);
		//texture.destroy(gl);
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
	}

	private float getDir(int facingDir) {
		switch(facingDir){
		case GameCharacter.NORTH:
			return 180f;
		case GameCharacter.EAST:
			return 270.0f;
		case GameCharacter.SOUTH:
			return 0.0f;
		case GameCharacter.WEST:
			return 90.0f;
		default:
			return -1;
		}
	}
	
}

