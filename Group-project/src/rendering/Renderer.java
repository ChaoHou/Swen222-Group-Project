package rendering;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;
import rendering.primitive.*;
import ui.Board;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Kyohei Kudo on 25/09/2014.
 */
public class Renderer implements GLEventListener, KeyListener {
    private int height;
    private int width;

    private Room room;
//    private final Board board;
//    private final Vamp player;
    private GLU glu;

    /**
     * position of camera
     */
    private Vector3D cameraPos = new Vector3D(0.0,48.0,150.0);
    private Vector3D lookAt = new Vector3D(0.0,42.0,0.0);
    private Vector3D cameraTop = new Vector3D(0.0,1.0,0.0);

    private float[] lightPos = new float[]{10f,100f,30f,1f};
    private float[] lightStr = new float[]{1f,1f,1f,1f};

    double[] modelview = new double[16];

    private double angle = 0;

    private int shadowMapTex[] = new int[1];
//    private Texture texture;

    public Renderer(Board board, int uid) {
//        this.board = board;
//        this.player = board.getCharacter(uid);
    }

    public Renderer(Room room) {
       this.room = room;
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        glu = GLU.createGLU(gl);
//        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 1.0f, 0.5f);    // Black Background
//        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(0f,0f,0f,1f);
        enableLighting(gl);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);

        initShadowMap(gl);
        
//        try {
//			texture = TextureIO.newTexture(new File("wall.jpg"), false);
//
//			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
//	        // Use linear filter for texture if image is smaller than the original texture
//	        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
//		} catch (GLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    private void initShadowMap(GL2 gl) {

        gl.glTexImage2D(GL2.GL_TEXTURE_2D,0,GL2.GL_DEPTH_COMPONENT, width,height,0,GL2.GL_DEPTH_COMPONENT,GL2.GL_UNSIGNED_BYTE,null);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);

        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S,GL2.GL_CLAMP);
        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T,GL2.GL_CLAMP);

        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL2.GL_TEXTURE_COMPARE_MODE,GL2.GL_COMPARE_R_TO_TEXTURE);

        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL2.GL_TEXTURE_COMPARE_FUNC,GL.GL_LEQUAL);

        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL2.GL_DEPTH_TEXTURE_MODE,GL.GL_LUMINANCE);

        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);
        gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);
        gl.glTexGeni(GL2.GL_Q, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);

        double[][] gen = new double[][]{{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};

        gl.glTexGendv(GL2.GL_S,GL2.GL_EYE_PLANE,gen[0],0);
        gl.glTexGendv(GL2.GL_T,GL2.GL_EYE_PLANE,gen[1],0);
        gl.glTexGendv(GL2.GL_R,GL2.GL_EYE_PLANE,gen[2],0);
        gl.glTexGendv(GL2.GL_Q,GL2.GL_EYE_PLANE,gen[3],0);
    }

    private void enableLighting(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
//        gl.glEnable(gl.GL_COLOR_MATERIAL);
//        gl.glEnable(GL2.GL_NORMALIZE);
//        gl.glEnable(GL.GL_DEPTH_TEST);
//        gl.glShadeModel(GL2.GL_FLAT);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        update(gl);
        enableShadowMapping(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.height = height;
        this.width = width;
        GL2 gl = drawable.getGL().getGL2();

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(35.0f, (float) width / (float) height, 1.0, 300.0);

        // change back to model view matrix
        gl.glMatrixMode(GL2.GL_MODELVIEW);


    }

    private void update(GL2 gl) {
        setCamera(gl);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION, lightPos,0);
    }

    private void setCamera(GL2 gl) {
        // change to projection matrix
        gl.glLoadIdentity();
        glu.gluLookAt(cameraPos.x(), cameraPos.y(), cameraPos.z(), lookAt.x(), lookAt.y(), lookAt.z(), cameraTop.x(), cameraTop.y(), cameraTop.z());
        gl.glRotated(angle,0,1,0);
    }

    private void enableShadowMapping(GL2 gl) {
        createDepthBuffer(gl);
//        applyDepthBuffer(gl);
        System.out.println("hoge");
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//        render(gl);
    }

    private void createDepthBuffer(GL2 gl) {
        int[] viewport = new int[4];
        double[] projection = new double[16];

        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

        gl.glGetIntegerv(GL.GL_VIEWPORT,viewport,0);

        gl.glViewport(0,0, width,height);

        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX,projection,0);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluPerspective(40.0,width/height,1, 300);
        glu.gluLookAt((double)lightPos[0],(double)lightPos[1],(double)lightPos[2],0,0,0,0,1,0);

        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelview,0);

        gl.glColorMask(false,false,false,false);

        gl.glDisable(GL2.GL_LIGHTING);

        gl.glCullFace(GL.GL_FRONT); //##########################

        render(gl);

        gl.glCopyTexSubImage2D(GL.GL_TEXTURE_2D,0,0,0,0,0,width,height);

        gl.glViewport(viewport[0],viewport[1],viewport[2],viewport[3]);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadMatrixd(projection, 0);
        gl.glColorMask(true, true, true, true);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glCullFace(GL.GL_BACK); //##########################

    }

    private void applyDepthBuffer(GL2 gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION, lightPos,0);

        gl.glMatrixMode(GL2.GL_TEXTURE);
        gl.glLoadIdentity();

        gl.glTranslated(0.5,0.5,0.5);
        gl.glScaled(0.5, 0.5, 0.5);

        gl.glMultMatrixd(modelview,0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
        gl.glEnable(GL2.GL_TEXTURE_GEN_R);
        gl.glEnable(GL2.GL_TEXTURE_GEN_Q);

        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_DIFFUSE, lightStr,0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_SPECULAR, lightStr,0);

        render(gl);

        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T);
        gl.glDisable(GL2.GL_TEXTURE_GEN_R);
        gl.glDisable(GL2.GL_TEXTURE_GEN_Q);
        gl.glDisable(GL.GL_TEXTURE_2D);

    }

    private void render(GL2 gl) {

//        board.getRoomContainsPlayer(player).render(gl,texture);
        room.render(gl);
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
        if (keycode == KeyEvent.VK_LEFT) {rotateL();}
        if (keycode == KeyEvent.VK_RIGHT) {rotateR();}

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
}
