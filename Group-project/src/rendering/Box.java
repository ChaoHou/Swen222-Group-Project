package rendering;

import gameworld.Wall;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

public class Box {
	
	private static final int BYTES_PER_FLOAT = 4;
	
	private float x;
	private float y;
	private float z;

	float[] size;
	
	private FloatBuffer vertices;
	private ByteBuffer indices;
	private FloatBuffer textureV;
	private int indexCount;
	private Texture[] textures;
	private int textureIndex;
	
	
	
	public Box(float[] coordinate,float[] size,int index){
		x = coordinate[0];
		y = coordinate[1];
		z = coordinate[2];
		
		this.size = size;
		textureIndex = index;
		
		float xC = size[0]/2;
		float yC = size[1]/2;
		float zC = size[2]/2;
		
		float[] verFloat = {	
				-xC,-yC,zC,
				xC,-yC,zC,
				xC,yC,zC,
				-xC,yC,zC,
				-xC,yC,-zC,
				xC,yC,-zC,
				xC,-yC,-zC,
				-xC,-yC,-zC,
				xC,-yC,zC,
				xC,-yC,-zC,
				xC,yC,-zC,
				xC,yC,zC,
				-xC,-yC,-zC,
				-xC,-yC,zC,
				-xC,yC,zC,
				-xC,yC,-zC,
		};
		vertices = ByteBuffer.allocateDirect(verFloat.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertices.put(verFloat).position(0);
		
		byte[] indByte = {
				15,14,13,12, 11,10,9,8, 0,1,6,7, 4,5,2,3, 7,6,5,4, 3,2,1,0
		};
		indexCount = indByte.length;
		this.indices = ByteBuffer.allocateDirect(indByte.length);
		this.indices.put(indByte).position(0);
	}
	
	
	public void init(GL gl,Texture[] textures) {
		this.textures = textures;
		float top = textures[textureIndex].getImageTexCoords().top();
		float bottom = textures[textureIndex].getImageTexCoords().bottom();
		float left = textures[textureIndex].getImageTexCoords().left();
		float right = textures[textureIndex].getImageTexCoords().right();
		//System.out.println("top:"+top);
		System.out.println("Top:"+top+" bottom:"+bottom+" left:"+left+" right:"+right);
//		float[] tVertices = new float[32];
//		for(int i=0;i<tVertices.length;i+=8){
//			tVertices[i] = left;
//			tVertices[i+1] = top;
//			tVertices[i+2] = right;
//			tVertices[i+3] = top;
//			tVertices[i+4] = right;
//			tVertices[i+5] = bottom;
//			tVertices[i+6] = left;
//			tVertices[i+7] = bottom;
//		}
		float[] tVertices = {
				0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f,
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f,
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f,
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f
		};
		
		textureV = ByteBuffer.allocateDirect(tVertices.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		textureV.put(tVertices).position(0);
	}
	
	public void draw(GL2 gl,int dir) {
		gl.glLoadIdentity();
		float rquad = Wall.getDir(dir);
		gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(x, y, z);
		
		textures[textureIndex].enable(gl);
		textures[textureIndex].bind(gl);

		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertices);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textureV);
		
		gl.glDrawElements(GL2.GL_QUADS, indexCount, GL.GL_UNSIGNED_BYTE, indices);
		
		gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}
}