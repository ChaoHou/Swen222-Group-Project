package gameworld;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;


/**
 * 
 * 
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 * base of rendering part made by Chao
 * established by Kyohei
 */
public class Wall {

    private static final int BYTES_PER_FLOAT = 4;

    /**
     *indicates height and width of walls
     */
    private static final float WIDTH = 10.0f;
    private static final float HEIGHT = 7.5f;

    /**
     * vertices of the wall. It is unti-clock wise.
     */
    private static final float[][] WALLS = {
            //[0]north wall
            {
                    -WIDTH,HEIGHT,WIDTH, //top left
                    WIDTH,HEIGHT,WIDTH , //top right
                    WIDTH,-HEIGHT,WIDTH, //bottom right
                    -WIDTH,-HEIGHT,WIDTH, //bottom left
            },
            //[1]west wall
            {
                    -WIDTH,HEIGHT,-WIDTH, //top left
                    -WIDTH,HEIGHT,WIDTH, //top right
                    -WIDTH,-HEIGHT,WIDTH, //bottom right
                    -WIDTH,-HEIGHT,-WIDTH, //bottom left
            },
            //[2]south wall
            {
                    WIDTH,HEIGHT,-WIDTH, //top right
                    -WIDTH,HEIGHT,-WIDTH, //top left
                    -WIDTH,-HEIGHT,-WIDTH, //bottom left
                    WIDTH,-HEIGHT,-WIDTH , //bottom right
            },
            //[3]east wall
            {
                    WIDTH,HEIGHT,WIDTH, //top left
                    WIDTH,HEIGHT,-WIDTH, //top right
                    WIDTH,-HEIGHT,-WIDTH, //bottom right
                    WIDTH,-HEIGHT,WIDTH , //bottom left
            },
    };

    /**
     *normals for each walls
     */
    private static final float[][] NORMALS = {
            //north[0] nomarl
            {0.0f,0.0f,1.0f},
            //west[1] nomarl
            {1.0f,0.0f,0.0f},
            //south[2] nomarl
            {0.0f,0.0f,-1.0f},
            //east[3] nomarl
            {-1.0f,0.0f,0.0f},
    };

    /**
     * Buffer for vertices, normals, textures
     */
    private FloatBuffer vertices;
    private FloatBuffer normal;
    private FloatBuffer textureV;

    /**
     * stores textures. textureIndex is a state of the wall. e.g. door, brick wall, etc...
     */
    private Texture[] textures;
    private int textureIndex;

    /**
     * constructor dir is for direction 0=north,1=west,2=south,3=east
     * index is condition of the wall.
     * @param dir
     * @param index
     * By Chao.
     */
    public Wall(int dir, int index) {
        this.textureIndex = index;

        float[] wall = WALLS[dir];
        float[] normalTemp = NORMALS[dir];
        vertices = ByteBuffer.allocateDirect(wall.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(wall).position(0);
        normal = ByteBuffer.allocateDirect(normalTemp.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        normal.put(normalTemp).position(0);
    }

    /**
     * stores textures
     * @param textures
     *
     * by Chao
     */
    public void init(Texture[] textures) {
        this.textures = textures;
        float top = textures[textureIndex].getImageTexCoords().top();
        float bottom = textures[textureIndex].getImageTexCoords().bottom();
        float left = textures[textureIndex].getImageTexCoords().left();
        float right = textures[textureIndex].getImageTexCoords().right();

        float[] tVertices = {
                right,top,
                left,top,
                left,bottom,
                right,bottom,
        };

        textureV = ByteBuffer.allocateDirect(tVertices.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureV.put(tVertices).position(0);
    }

    /**
     * render the walls by given information when they initialized.
     * @param gl
     */
    public void render(GL2 gl) {

        //enable textures and bind it.
        textures[textureIndex].enable(gl);
        textures[textureIndex].bind(gl);

        //enable rendering by FloatBuffer
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY); // added normal
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

        //give the data to the OpenGL
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertices);
        gl.glNormalPointer(GL2.GL_FLOAT, 0, normal);
        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textureV);

        //render it!
        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);

        //disable rendering method.
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }

    /**
     * return a radius by direction. 0 = north, 1 = west, 2 = south, 3 = east.
     * south is the default (zero radius) direction.
     * @param facingDir
     * @return
     */
    public static float getDir(int facingDir) {
        switch(facingDir){
            case GameCharacter.NORTH:
                return 180.0f;
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

