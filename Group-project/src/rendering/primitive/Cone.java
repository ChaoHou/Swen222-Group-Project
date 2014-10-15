package rendering.primitive;

import com.jogamp.opengl.util.gl2.GLUT;
import rendering.Vector3D;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Created by innocence on 07/10/2014.
 */
public class Cone {
    public static void render(GL2 gl, Vector3D pos, double rad, double height, int axis) {
        GLUT glut = new GLUT();
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        gl.glPushMatrix();
        gl.glTranslated(pos.x(), pos.y(), pos.z());
        switch(axis) {
            case 0: //top
                gl.glRotated(-90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            case 1: //north
                gl.glRotated(180.0, 0.0, 1.0, 0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            case 2: //east
                gl.glRotated(90.0, 0.0, 1.0, 0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            case 3: //south
                gl.glTranslated(0.0, 0.0, -height);
                break;
            case 4: //west
                gl.glRotated(-90.0,0.0,1.0,0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            case 5: //bottom
                gl.glRotated(90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            default:
                gl.glRotated(-90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
        }
        glut.glutSolidCone(rad, height, 16, 16);

        gl.glPushMatrix();
        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        glu.gluDisk(quadric, 0.0, rad, 16, 16);
        gl.glPopMatrix();

        gl.glPopMatrix();

    }
}
