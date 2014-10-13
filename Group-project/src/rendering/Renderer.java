package rendering;

import javax.media.opengl.*;
import gameworld.Container;
import gameworld.Vamp;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import rendering.primitive.*;
import rendering.primitive.Box;
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
//    private final Board board;
//    private final Vamp player;

    /**
     * position of camera
     */
    private Vector3D cameraPos = new Vector3D(0.0,48.0,150.0);
    private Vector3D lookAt = new Vector3D(0.0,42.0,0.0);
    private Vector3D cameraTop = new Vector3D(0.0,1.0,0.0);

    private float[] lightPos = new float[]{10.0f,100.0f,30.0f,1.0f};
    private float[] lightStr = new float[]{1.0f,1.0f,1.0f,1.0f};
    private float[] lightAmb = new float[]{0.1f,0.1f,0.1f,1.0f};

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

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.5f, 0.5f, 1.0f, 0.5f);    // Black Background
        gl.glColor4d(1.0,1.0,1.0,1.0);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        this.gl = gl;
//        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing

//        gl.glEnable(GL2.GL_LIGHTING);
//        gl.glEnable(GL2.GL_LIGHT0);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,lightAmb,0);

        gl.glEnable(GL.GL_CULL_FACE);
        gl.glFrontFace(GL2.GL_CW);

//        enableLighting(gl); //for test

        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        float[] white = new float[]{1.0f,1.0f,1.0f,1.0f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, white, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 16.0f);
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
        update();

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
//        render();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        this.width = width;
        this.height = height;

        gl.glViewport(0, 0, width, height);
        gl.glLoadIdentity();


    }

    private void update() {
        setCamera(gl);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION, lightPos,0);
    }

    private void setCamera(GL2 gl) {
        // change to projection matrix

        gl.glMatrixMode(GL2.GL_PROJECTION);

        gl.glLoadIdentity();
        glu.gluPerspective(100.0f, (float) width / (float) height, 1.0, 300.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(cameraPos.x(), cameraPos.y(), cameraPos.z(), lookAt.x(), lookAt.y(), lookAt.z(), cameraTop.x(), cameraTop.y(), cameraTop.z());
        gl.glRotated(angle, 0, 1, 0);
    }

    public void setMouseEvent(MouseEvent e){
    	this.mouse = e;
    }



    private void render() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

//        board.getRoomContainsPlayer(player).render(gl,texture);
        Cylinder.render(gl, new Vector3D(0.0, 20.0, 0.0), 2.5, 20.0, 0);
        Sphere.render(gl, new Vector3D(0.0, 20.0, 0.0), 5);
        Cone.render(gl, new Vector3D(0.0, 30.0, 0.0), 5, 30.0, 5);
        Box.render(gl,new Vector3D(-64,0,0), new Vector3D(20,5,20));
        Tetra.render(gl, new Vector3D(-5, 10, 0), new Vector3D(20, 5, 20),0);
        gl.glFlush();

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
        angle -= 10.0;
//        System.out.println("rotate left");
    }
    
    public void rotateR(){
        angle += 90.0;
//        System.out.println("rotate right");
    }
    
    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    public GL2 getGL(){
    	return gl;
    }
    
   
    
}
