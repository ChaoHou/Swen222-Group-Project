package rendering.primitive;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;

/**
 * By Kyohei Kudo
 * ID: 300287923
 *
 * Base code of texture part is given by Chao.
 */
public class Box {

    private static final int BYTES_PER_FLOAT = 4;

    /**
     * x coordinate of the box
     */
    private float x;
    /**
     * y coordinate of the box
     */
    private float y;
    /**
     * z coordinate of the box
     */
    private float z;
    /**
     * size of the box
     */
    float[] size;

    /**
     * Buffer for the box's vertices
     */
    private FloatBuffer vertices;
    /**
     * Buffer for the box's face's normals
     */
    private FloatBuffer normal;
    /**
     * Buffer for the texture
     */
    private FloatBuffer textureV;
    /**
     * Texture file loaded when it is initialized
     */
    private Texture[] textures;
    /**
     * textureIndex corresponding to box's state
     */
    private int textureIndex;


    /**
     * normal of each cube's faces
     * Created by Kyohei.
     */
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


    /**
     * Constructor of a box. index is for the state of the box e.g. Container or furniture
     * @param coordinate
     * @param size
     * @param index
     */
    public Box(float[] coordinate,float[] size,int index){
        x = coordinate[0];
        y = coordinate[1];
        z = coordinate[2];

        this.size = size;
        textureIndex = index;

        float xC = size[0]/2;
        float yC = size[1]/2;
        float zC = size[2]/2;

        //vertex coordinates modified by Kyohei
        float[] verFloat = {
                xC,yC,-zC, // top starts
                -xC,yC,-zC,
                -xC,yC,zC,
                xC,yC,zC, // top ends
                xC,-yC,-zC, // bottom starts
                xC,-yC,zC,
                -xC,-yC,zC,
                -xC,-yC,-zC, // bottom ends
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

        //add normal to each faces. Done by Kyohei.
        normal = ByteBuffer.allocateDirect(NORMALS.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        normal.put(NORMALS).position(0);

    }

    /**
     * Initialize a texture to the box instance
     * @param textures
     */
    public void init(Texture[] textures) {
        this.textures = textures;
        float[] tVertices = {
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f,
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f,
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f,
                0.0f,0.0f, 1.0f,0.0f, 1.0f,1.0f, 0.0f,1.0f
        };

        textureV = ByteBuffer.allocateDirect(tVertices.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureV.put(tVertices).position(0);
    }

    /**
     * Render a box where it is initialized.
     * @param gl
     *
     * modified by Kyohei.
     * Added normal, got rid of rotation feature, change rendering method.
     */
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);

        //enable and bind the texture to the object drawn next.
        textures[textureIndex].enable(gl);
        textures[textureIndex].bind(gl);

        //enable the feature drawing objects by FloatBuffer
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

        //give the coordinates infromation to the openGL
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertices);
        gl.glNormalPointer(GL2.GL_FLOAT,0, normal);
        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textureV);

        //renders by the given FloatBuffer
        //use glDrawArrays instead of glDrawElements. it is simpler and easier.
        gl.glDrawArrays(GL2.GL_QUADS,0,4*6);

        //disable rendering feature. otherwise it affects next rendering process.
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glPopMatrix();
    }

    /**
     * By mouse coordinate, return a boolean tells if that coordinate is contained in where a box is or not.
     * Compare two vectors where points to "this" box and where point to clicked on the screen.
     * @param gl
     * @param glu
     * @param mouseX
     * @param mouseY
     * @return
     * modified by Kyohei. removed rotation.
     */
    public boolean containsPoint(GL2 gl, GLU glu, int mouseX, int mouseY){

        //create containers for storing current information of rendering matrix.
        int[] viewport = new int[4];
        double[] modelView = new double[16];
        double[] projection = new double[16];

        //store matrix into each container
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView, 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);

        //invert Y coordinate because JFrame Y coordinate and OpenGL Y coodinate is up side down.
        double winX = mouseX;
        double winY = viewport[3] - mouseY;

        //create containers for the vector which connects screen clicked(start) and render world(end) pointed by clicked pos
        double[] start = new double[4];
        double[] end = new double[4];

        //store points into storage.
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

        //restore it into new storage has 3 elements. we don't need 4th elements for calculating.
        double[] x1 = {start[0],start[1],start[2]};
        double[] x2 = {end[0],end[1],end[2]};
        double[] x0 = {x,y,z};

        //get two vectors from vertices
        double[] x2Tox1 = {x2[0]-x1[0],x2[1]-x1[1],x2[2]-x1[2]};
        double[] x1Tox0 = {x1[0]-x0[0],x1[1]-x0[1],x1[2]-x0[2]};

        //get cross product
        double[] m = cross(x2Tox1,x1Tox0);

        //get the length of the vector
        double length = length(m)/length(x2Tox1);

        //compare with the radius
        if(length < size[0]) return true;
        return false;
    }

    /**
     * internal method to calculate cross products of two vectors.
     * @param u
     * @param v
     * @return
     *
     * modified by Kyohei
     * changed it to private
     */
    private double[] cross(double[] u,double[] v){
        return new double[]{(u[1]*v[2]) - (u[2]*v[1]),(u[2]*v[0]) - (u[0]*v[2]),(u[0]*v[1]) - (u[1]*v[0])};
    }

    /**
     * internal method to calculate length of a vector
     * @param u
     * @return
     *
     * modified by Kyohei
     * changed it to private
     */
    private double length(double[] u){
        return  Math.abs(Math.sqrt((u[0] *u[0]) + (u[1] *u[1]) + (u[2] *u[2])));
    }
}