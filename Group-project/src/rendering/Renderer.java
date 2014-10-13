package rendering;

import gameworld.Container;
import gameworld.Vamp;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import ui.Board;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Kyohei Kudo on 25/09/2014.
 */
public class Renderer implements GLEventListener, KeyListener {

    private final Board game;
    private final int uid;
    
    private String[] textureFiles = {"wall0.jpg","wall1.jpg","wall2.jpg","wall3.jpg","wall4.jpg"};
    private Texture[] textures = new Texture[textureFiles.length];
    
    private GLU glu = new GLU();
    private GL2 gl;
    private MouseEvent mouse;
    public boolean selected = false;
    
    public Renderer(Board game,int uid) {
        this.game = game;
        this.uid = uid;
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        this.gl = gl;
//        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
//        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glFrontFace(GL.GL_CW);
        gl.glCullFace(GL.GL_BACK);
        
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
        
        double[] modelView = new double[16];
		double[] projection = new double[16];
		int[] viewport = new int[4];
		
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView,0);
		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		
		System.out.println(Arrays.toString(modelView));
		System.out.println(Arrays.toString(projection));
		System.out.println(Arrays.toString(viewport));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
    	GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        Vamp player = game.getVamp(uid);
        gameworld.Room room = game.getRoomContainingPlayer(player);
        
        room.draw(gl,player.getDirectionFacing());
        
        
        if(mouse != null){
        	//perform selection
        	Container container = game.getRoomContainingPlayer(game.getVamp(uid)).getContainer();
        	
        	if(container != null){
        		if(container.containsPoint(gl, glu, mouse.getX(), mouse.getY())){
        			selected = true;
        		}
        	}
        	
        	mouse = null;
        }
        
        
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(100.0f, h, 1.0, 100.0);
//        glu.gluLookAt(0, 0, 0.1, 0, 0, 0, 0, 1, 0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        
    }

    public void setMouseEvent(MouseEvent e){
    	this.mouse = e;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
//        if (keycode == KeyEvent.VK_LEFT) {room.rotateL();}
//        if (keycode == KeyEvent.VK_RIGHT) {room.rotateR();}

    }

    public void rotateL(){
//    	room.rotateL();
    }
    
    public void rotateR(){
//    	room.rotateR();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    public GL2 getGL(){
    	return gl;
    }
    
   
    
}
