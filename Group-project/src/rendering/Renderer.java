package rendering;

import com.jogamp.opengl.util.gl2.GLUT;
import gameworld.Container;
import gameworld.Furniture;
import gameworld.Vamp;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import gameworld.Wall;
import rendering.primitive.*;
import ui.Board;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Kyohei Kudo
 * ID: 300287923
 *
 * modified by Chao
 */
public class Renderer implements GLEventListener{

    /**
     * stores the game state.
     */
    private final Board game;
    /**
     * stores the user id used by multiplayer
     */
    private final int uid;

    /**
     * texture files given in resource folder
     */
    private String[] textureFiles = {"wall0.jpg","wall1.jpg","wall2.jpg","wall3.jpg","wall4.jpg"};
    /**
     * storage for textures used by Wall and Box.
     */
    private Texture[] textures = new Texture[textureFiles.length];

    /**
     * field for GL utility class.
     */
    private GLU glu = new GLU();
    /**
     * stores main GL class which controls rendering like Graphics in Java standard libraries
     */
    private GL2 gl;
    /**
     * mouse event
     */
    private MouseEvent mouse;
    /**
     * true when container or furniture is selected by user
     */
    private boolean containerSelected = false;
    private boolean furnitureSelected = false;

    /**
     * position of camera
     */
    private float fovy = 45.0f;
    private Vector3D cameraPos = new Vector3D(0.0,2.0,9.0);
    private Vector3D lookAt = new Vector3D(0.0,1.0,0.0);
    private Vector3D cameraTop = new Vector3D(0.0,1.0,0.0);

    private float[] lightPos = new float[]{0.0f,1.0f,1.0f,0.0f};
    private float[] lightStr = new float[]{1.0f,1.0f,1.0f,1.0f};
    private float[] lightColor = new float[]{0.1f,0.1f,0.1f,1.0f}; // color of light

    private double angle = 0;
    private int width;
    private int height;


    public Renderer(Board game,int uid) {
        this.game = game;
        this.uid = uid;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        this.gl = gl;

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.2f, 0.2f, 0.2f, 0.5f);    // Black Background
        gl.glColor4d(1.0,1.0,1.0,1.0);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing

        gl.glEnable(GL.GL_CULL_FACE);
//        gl.glFrontFace(GL2.GL_CW);
        gl.glCullFace(GL.GL_BACK);

        setLighting();


//        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
//        gl.glEnable(GL2.GL_COLOR_MATERIAL);

//        float[] white = new float[]{1.0f,1.0f,1.0f,1.0f};
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, white, 0);
//        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 16.0f);

        try {
			
			for(int i=0;i<textureFiles.length;i++){
				String dir = "resources/images/walls/"+textureFiles[i];
				System.out.println("dir:"+dir);
				textures[i] = TextureIO.newTexture(new File(dir),false);
				//gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
				//gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR);
				textures[i].setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST); 
				textures[i].setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			}
			
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Texture Initialized");
        
        game.initRooms(gl,textures);
      
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        setLighting();

        //render a avator
        Cone.render(gl, new Vector3D(0.0, 0.0, 0.0), 1, 7.5, 0);
        Sphere.render(gl, new Vector3D(0.0, 0.0, 0.0), 1);

        setCamera();

        Vamp player = game.getVamp(uid);
        gameworld.Room room = game.getRoomContainingPlayer(player);

        angle = Wall.getDir(player.getDirectionFacing());
        room.render(gl, player.getDirectionFacing());
        
        
        if(mouse != null){
        	//perform selection
        	Container container = game.getRoomContainingPlayer(game.getVamp(uid)).getContainer();
        	if(container != null){
        		if(container.containsPoint(gl, glu, mouse.getX(), mouse.getY(),player.getDirectionFacing())){
        			//System.out.println("Mouse point: x:"+mouse.getX()+" y:"+mouse.getY());
        			setContainerSelected(true);
        		}else{
        			setContainerSelected(false);
        			
        			//if the container is not selected, check the furniture
        			
        		}
        	}
        	
        	Furniture furniture = game.getRoomContainingPlayer(game.getVamp(uid)).getFurniture();
        	if(furniture != null){
        		if(furniture.containsPoint(gl, glu, mouse.getX(), mouse.getY(),player.getDirectionFacing())){
        			setFurnitureSelected(true);
        		}else{
        			setFurnitureSelected(false);
        		}
        	}
        	
        	mouse = null;
        }


        gl.glFlush();
//        render();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        this.width = width;
        this.height = height;

        gl.glViewport(0, 0, width, height);
        gl.glLoadIdentity();

//        setCamera(gl);
    }

    private void setCamera() {
        // change to projection matrix

        gl.glMatrixMode(GL2.GL_PROJECTION);

        gl.glLoadIdentity();
        glu.gluPerspective(fovy, (float) width / (float) height, 1.0, 300.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(cameraPos.x(), cameraPos.y(), cameraPos.z(), lookAt.x(), lookAt.y(), lookAt.z(), cameraTop.x(), cameraTop.y(), cameraTop.z());

        gl.glRotated(angle, 0, 1, 0);
    }

    private void setLighting() {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_AMBIENT, lightStr,0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION,lightPos,0);
    }

    public void setMouseEvent(MouseEvent e){
    	this.mouse = e;
    }


    public GL2 getGL(){
    	return gl;
    }

	public boolean isContainerSelected() {
		return containerSelected;
	}

	public void setContainerSelected(boolean containerSelected) {
		this.containerSelected = containerSelected;
	}

	public boolean isFurnitureSelected() {
		return furnitureSelected;
	}

	public void setFurnitureSelected(boolean furnitureSelected) {
		this.furnitureSelected = furnitureSelected;
	}
    
   
    
}
