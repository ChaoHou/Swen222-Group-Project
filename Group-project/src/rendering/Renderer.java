package rendering;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.PMVMatrix;
import rendering.primitive.*;
import ui.Board;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.FloatBuffer;

/**
 * Created by Kyohei Kudo on 25/09/2014.
 */
public class Renderer implements GLEventListener, KeyListener {
    private int texheight = 512;
    private int texwidth = 512;

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

    private float[] lightPos = new float[]{10.0f,100.0f,30.0f,1.0f};
    private float[] lightStr = new float[]{1.0f,1.0f,1.0f,1.0f};
    private float[] lightAmb = new float[]{0.1f,0.1f,0.1f,1.0f};

    double[] modelview = new double[16];

    private double angle = 0;

    private int[] shadowMapTex = new int[100];
    private float[] CPMatrix = new float[16];
    private float[] CVMatrix = new float[16];
    private float[] LPMatrix = new float[16];
    private float[] LVMatrix = new float[16];
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

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.5f, 0.5f, 1.0f, 0.5f);    // Black Background
        gl.glColor4d(1.0,1.0,1.0,1.0);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,lightAmb,0);

        gl.glEnable(GL.GL_CULL_FACE);

        initShadowMap(gl);
//        enableLighting(gl); //for test

        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        float[] white = new float[]{1.0f,1.0f,1.0f,1.0f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, white, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 16.0f);
//        gl.glCullFace(GL.GL_BACK);


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

        gl.glGenTextures(1,shadowMapTex,0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D,shadowMapTex[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D,0,GL2.GL_DEPTH_COMPONENT, texwidth, texheight,0,GL2.GL_DEPTH_COMPONENT,GL2.GL_UNSIGNED_BYTE,null);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);


        gl.glPushMatrix();

        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, (float) texwidth / texheight, 1.0f, 600.0f);
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, CPMatrix,0);

        gl.glLoadIdentity();
//        glu.gluLookAt(cameraPos.x, cameraPos.y,cameraPos.z,
//                0.0f, 0.0f, 0.0f,
//                0.0f, 1.0f, 0.0f);
        glu.gluLookAt(cameraPos.x(), cameraPos.y(), cameraPos.z(), lookAt.x(), lookAt.y(), lookAt.z(), cameraTop.x(), cameraTop.y(), cameraTop.z());
        setCamera(gl);
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, CVMatrix,0);

        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, 1.0f, 2.0f, 600.0f);
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, LPMatrix,0);

        gl.glLoadIdentity();
        glu.gluLookAt( lightPos[0], lightPos[1], lightPos[2],
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, LVMatrix,0);

//        for (int i = 0; i < 4; ++i) {
//            System.out.println(CVMatrix[i]);
//            System.out.println(CPMatrix[i]);
//            System.out.println(LVMatrix[i]);
//            System.out.println(LPMatrix[i]);
//        }

        gl.glPopMatrix();

        //######



    }

    private void enableLighting(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glCullFace(GL.GL_BACK);
//        gl.glEnable(GL2.GL_LIGHTING);
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
//        update(gl);
        enableShadowMapping(gl);
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.texheight = height;
        this.texwidth = width;
        GL2 gl = drawable.getGL().getGL2();

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);

        gl.glLoadIdentity();
        glu.gluPerspective(35.0f, (float) width / (float) height, 1.0, 600.0);

        // change back to model view matrix
//        gl.glMatrixMode(GL2.GL_MODELVIEW);


    }

    private void update(GL2 gl) {
        setCamera(gl);
//        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION, lightPos,0);
    }

    private void setCamera(GL2 gl) {
        // change to projection matrix
//        glu.gluLookAt(cameraPos.x(), cameraPos.y(), cameraPos.z(), lookAt.x(), lookAt.y(), lookAt.z(), cameraTop.x(), cameraTop.y(), cameraTop.z());
        gl.glRotated(angle,0,1,0);
    }

    private void enableShadowMapping(GL2 gl) {
        createDepthBuffer(gl);
        applyDepthBuffer(gl);
//        System.out.println("hoge");
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//        render(gl);
    }

    private void createDepthBuffer(GL2 gl) {
//        int[] viewport = new int[4];
//        double[] projection = new double[16];

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadMatrixf(LPMatrix,0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadMatrixf(LVMatrix,0);

//        gl.glGetIntegerv(GL.GL_VIEWPORT,viewport,0);

        gl.glViewport(0,0, texwidth, texheight);

//        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX,projection,0);

//        gl.glMatrixMode(GL2.GL_PROJECTION);
//        gl.glLoadIdentity();
//
//        gl.glMatrixMode(GL2.GL_MODELVIEW);
//        gl.glLoadIdentity();
//        glu.gluPerspective(100.0, texwidth / texheight,1, 300);
//        glu.gluLookAt((double)lightPos[0],(double)lightPos[1],(double)lightPos[2],0.0,0.0,0.0,0.0,1.0,0.0);
//        gl.glRotated(angle,0,1,0);
//
//        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelview,0);

        gl.glColorMask(false,false,false,false);
        gl.glShadeModel(GL2.GL_FLAT);
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glCullFace(GL.GL_FRONT);

        render(gl);

//        gl.glBindTexture(GL2.GL_TEXTURE_2D, shadowMapTex[0]);
        gl.glCopyTexSubImage2D(GL.GL_TEXTURE_2D,0,0,0,0,0, texwidth, texheight);

//        gl.glViewport(viewport[0],viewport[1],viewport[2],viewport[3]);
//        gl.glMatrixMode(GL2.GL_PROJECTION);
//        gl.glLoadMatrixd(projection, 0);
        gl.glColorMask(true, true, true, true);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glCullFace(GL.GL_BACK); //##########################

    }

    private void applyDepthBuffer(GL2 gl) {
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadMatrixf(CPMatrix, 0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadMatrixf(CVMatrix,0);

        gl.glViewport(0,0,texwidth,texheight);
//        gl.glLoadIdentity();
        setCamera(gl);

        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION, lightPos,0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_DIFFUSE, lightStr,0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightStr, 0);
        render(gl);

        float[] biasMatrix = new float[]{0.5f,0.0f,0.0f,0.0f,0.0f,0.5f,0.0f,0.0f,0.0f,0.0f,0.5f,0.0f,0.5f,0.5f,0.5f,1.0f};
        float[] textureMatrix = Matrix.multMatrix(biasMatrix,LPMatrix);
        textureMatrix = Matrix.multMatrix(textureMatrix,LVMatrix);

        FloatBuffer buf1,buf2,buf3,buf4;
        float[] temp1 = new float[4],temp2 = new float[4],temp3 = new float[4],temp4 = new float[4];
        for (int i = 1; i< 4;++i)  {
            temp1[i] = textureMatrix[i];
            temp2[i] = textureMatrix[i+4];
            temp3[i] = textureMatrix[i+8];
            temp4[i] = textureMatrix[i+12];
        }
//        temp1 = new float[]{1,0,0,0};
//        temp2 = new float[]{0,1,0,0};
//        temp3 = new float[]{0,0,1,0};
//        temp4 = new float[]{0,0,0,1};
        buf1 = FloatBuffer.wrap(temp1);
        buf2 = FloatBuffer.wrap(temp2);
        buf3 = FloatBuffer.wrap(temp3);
        buf4 = FloatBuffer.wrap(temp4);

//        System.out.printf("buf1 %.3f, %.3f, %.3f, %.3f\n", buf1.array()[0],buf1.array()[1],buf1.array()[2],buf1.array()[3]);
//        System.out.printf("buf2 %.3f, %.3f, %.3f, %.3f\n", buf2.array()[0],buf2.array()[1],buf2.array()[2],buf2.array()[3]);
//        System.out.printf("buf3 %.3f, %.3f, %.3f, %.3f\n", buf3.array()[0],buf3.array()[1],buf3.array()[2],buf3.array()[3]);
//        System.out.printf("buf4 %.3f, %.3f, %.3f, %.3f\n", buf4.array()[0],buf4.array()[1],buf4.array()[2],buf4.array()[3]);

        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);
        gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);
        gl.glTexGeni(GL2.GL_Q, GL2.GL_TEXTURE_GEN_MODE,GL2.GL_EYE_LINEAR);


        gl.glTexGenfv(GL2.GL_S,GL2.GL_EYE_PLANE, buf1);
        gl.glTexGenfv(GL2.GL_T, GL2.GL_EYE_PLANE, buf2);
        gl.glTexGenfv(GL2.GL_R, GL2.GL_EYE_PLANE, buf3);
        gl.glTexGenfv(GL2.GL_Q, GL2.GL_EYE_PLANE, buf4);

        gl.glMatrixMode(GL2.GL_TEXTURE);
//        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, (float) texwidth / texheight, 1.0f, 300.0f);
        glu.gluLookAt(cameraPos.x(), cameraPos.y(), cameraPos.z(), lookAt.x(), lookAt.y(), lookAt.z(), cameraTop.x(), cameraTop.y(), cameraTop.z());
        setCamera(gl);
//        gl.glTranslated(0.5,0.5,0.5);
//        gl.glScaled(0.5, 0.5, 0.5);
//
//        gl.glMultMatrixf(textureMatrix,0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

//        gl.glEnable(GL2.GL_ALPHA_TEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
        gl.glEnable(GL2.GL_TEXTURE_GEN_R);
        gl.glEnable(GL2.GL_TEXTURE_GEN_Q);

//        gl.glBindTexture(GL2.GL_TEXTURE_2D, shadowMapTex[0]);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_COMPARE_MODE, GL2.GL_COMPARE_R_TO_TEXTURE);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_COMPARE_FUNC, GL.GL_LEQUAL);

        gl.glTexParameteri(GL.GL_TEXTURE_2D,GL2.GL_DEPTH_TEXTURE_MODE,GL.GL_LUMINANCE);



        render(gl);

        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T);
        gl.glDisable(GL2.GL_TEXTURE_GEN_R);
        gl.glDisable(GL2.GL_TEXTURE_GEN_Q);
        gl.glDisable(GL2.GL_TEXTURE_2D);
//        gl.glDisable(GL2.GL_ALPHA_TEST);


    }

    private void render(GL2 gl) {

//        board.getRoomContainsPlayer(player).render(gl,texture);
        room.render(gl);
        Cylinder.render(gl, new Vector3D(0.0, 20.0, 0.0), 2.5, 20.0, 0);
        Sphere.render(gl, new Vector3D(0.0, 20.0, 0.0), 5);
        Cone.render(gl, new Vector3D(0.0, 30.0, 0.0), 5, 30.0, 5);
        Box.render(gl,new Vector3D(-64,0,0), new Vector3D(20,5,20));
        Tetra.render(gl, new Vector3D(-5, 10, 0), new Vector3D(20, 5, 20),0);
//        gl.glFlush();

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
