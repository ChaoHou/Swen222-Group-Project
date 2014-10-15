package gameworld;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Wall {
//	public static final int NORTH_WALL = 0;
//	public static final int WEST_WALL = 1;
//	public static final int SOUTH_WALL = 2;
//	public static final int EAST_WALL = 3;

    private static final int BYTES_PER_FLOAT = 4;

    private static final float WIDTH = 10.0f;
    private static final float HEIGHT = 7.5f;
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

//    private static final float[] floor = {
//      WIDTH,-HEIGHT,-WIDTH,
//            -WIDTH,-HEIGHT,-WIDTH,
//            -WIDTH,-HEIGHT,WIDTH,
//            WIDTH,-HEIGHT,WIDTH,
//    };

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

    private FloatBuffer vertices;
    private FloatBuffer normal;
    private FloatBuffer textureV;

    private int dir;

    private Texture[] textures;
    private int textureIndex;

    public Wall(int dir, int index) {
        this.dir = dir;
        this.textureIndex = index;

        float[] wall = WALLS[dir];
        float[] normalTemp = NORMALS[dir];
        vertices = ByteBuffer.allocateDirect(wall.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(wall).position(0);
        normal = ByteBuffer.allocateDirect(normalTemp.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        normal.put(normalTemp).position(0);
    }

    public void init(GL gl,Texture[] textures) {
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

    public void draw(GL2 gl,int facingDir) {
//		gl.glLoadIdentity();
        gl.glPushMatrix();
//		gl.glRotatef(getDir(facingDir), 0.0f, 1.0f, 0.0f);

        textures[textureIndex].enable(gl);
        textures[textureIndex].bind(gl);

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertices);
        gl.glNormalPointer(GL2.GL_FLOAT, 0, normal);
        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textureV);

        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);

        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glPopMatrix();
    }

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

