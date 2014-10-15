package rendering;

import gameworld.Wall;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;

public class Box {
	
	private static final int BYTES_PER_FLOAT = 4;
	
	private float x;
	private float y;
	private float z;

	float[] size;
	
	private FloatBuffer vertices;
    private FloatBuffer normal;
	private ByteBuffer indices;
	private FloatBuffer textureV;
	private int indexCount;
	private Texture[] textures;
	private int textureIndex;


    private static final float[] NORMALS = {
            //top nomarl
            0.0f,1.0f,0.0f,
            //bottom nomarl
            0.0f,-1.0f,0.0f,
            //north[0] nomarl
            0.0f,0.0f,-1.0f,
            //west[1] nomarl
            -1.0f,0.0f,0.0f,
            //south[2] nomarl
            0.0f,0.0f,1.0f,
            //east[3] nomarl
            1.0f,0.0f,0.0f,
    };
	
	
	
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
                xC,yC,-zC, // top starts
                -xC,yC,-zC,
                -xC,yC,zC,
                xC,yC,zC, // top ends
                xC,yC,-zC, // bottom starts
                xC,yC,zC,
                -xC,yC,zC,
                -xC,yC,-zC, // bottom ends
                xC,yC,-zC, // north starts
                xC,-yC,-zC,
                -xC,-yC,-zC,
                -xC,yC,-zC, // north ends
                -xC,yC,zC, // west starts
                -xC,yC,-zC,
                -xC,-yC,-zC,
                -xC,-yC,zC, // west ends
                xC,yC,zC, // south starts
                -xC,yC,zC,
                -xC,-yC,zC,
                xC,-yC,zC, // south ends
                xC,yC,-zC, // east starts
                xC,yC,zC,
                xC,-yC,zC,
                xC,-yC,-zC, // east ends
		};


		vertices = ByteBuffer.allocateDirect(verFloat.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertices.put(verFloat).position(0);

        normal = ByteBuffer.allocateDirect(NORMALS.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        normal.put(NORMALS).position(0);

//		byte[] indByte = {
//				15,14,13,12, 11,10,9,8, 0,1,6,7, 4,5,2,3, 7,6,5,4, 3,2,1,0
//		};
//		indexCount = indByte.length;
//		this.indices = ByteBuffer.allocateDirect(indByte.length);
//		this.indices.put(indByte).position(0);
	}
	
	
	public void init(GL gl,Texture[] textures) {
		this.textures = textures;
//		float top = textures[textureIndex].getImageTexCoords().top();
//		float bottom = textures[textureIndex].getImageTexCoords().bottom();
//		float left = textures[textureIndex].getImageTexCoords().left();
//		float right = textures[textureIndex].getImageTexCoords().right();
		//System.out.println("top:"+top);
//		System.out.println("Top:"+top+" bottom:"+bottom+" left:"+left+" right:"+right);
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
//		gl.glLoadIdentity();
            gl.glPushMatrix();
            float rquad = Wall.getDir(dir);
//		gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(x, y, z);

            textures[textureIndex].enable(gl);
            textures[textureIndex].bind(gl);

            gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
            gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

            gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertices);
        gl.glNormalPointer(GL2.GL_FLOAT,0, normal);
            gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textureV);

//            gl.glDrawElements(GL2.GL_QUADS, indexCount, GL.GL_UNSIGNED_BYTE, indices);
        gl.glDrawArrays(GL2.GL_QUADS,0,4*6);

            gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
            gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
            gl.glPopMatrix();
	}

    //Chao's work
	public boolean containsPoint(GL2 gl,GLU glu, int mouseX,int mouseY,int dir){

        gl.glPushMatrix();
//		gl.glLoadIdentity();
//		float rquad = Wall.getDir(dir);
//		gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
//		gl.glTranslatef(x, y, z);
		
		int[] viewport = new int[4];
    	double[] modelView = new double[16];
    	double[] projection = new double[16];
    	
    	gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
    	gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView, 0);
    	gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);
    	
    	double winX = mouseX;
    	double winY = viewport[3] - mouseY;
		
    	double[] start = new double[4];
    	double[] end = new double[4];
    	
    	glu.gluUnProject(winX, winY, 0,
    			modelView, 0,
    			projection, 0,
    			viewport, 0,
    			start, 0);


    	glu.gluUnProject(winX, winY, 1,
    			modelView, 0,
    			projection, 0, 
    			viewport, 0, 
    			end, 0);
		
    	double[] x1 = {start[0],start[1],start[2]};
    	double[] x2 = {end[0],end[1],end[2]};
    	double[] x0 = {x,y,z};

    	double[] x2Tox1 = {x2[0]-x1[0],x2[1]-x1[1],x2[2]-x1[2]};
    	double[] x1Tox0 = {x1[0]-x0[0],x1[1]-x0[1],x1[2]-x0[2]};

    	double[] m = cross(x2Tox1,x1Tox0);


    	double length = length(m)/length(x2Tox1);
        gl.glPopMatrix();
    	
    	//compare with the radius
//        System.out.printf("%.3f,%.3f,%.3f : %.3f,%.3f,%.3f length=%.3f\n",(float)start[0],(float)start[1],(float)start[2],(float)end[0],(float)end[1],(float)end[2],(float)length);
    	if(length < size[0]) return true;
		return false;
	}
	
	public double[] cross(double[] u,double[] v){
    	return new double[]{(u[1]*v[2]) - (u[2]*v[1]),(u[2]*v[0]) - (u[0]*v[2]),(u[0]*v[1]) - (u[1]*v[0])};
    }
    
    public double length(double[] u){
    	return  Math.abs(Math.sqrt((u[0] *u[0]) + (u[1] *u[1]) + (u[2] *u[2])));
    }
}