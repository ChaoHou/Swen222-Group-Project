package rendering.primitive;

import rendering.Vector3D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Created by innocence on 04/10/2014.
 */
public class Cylinder {
    private static GLU glu = new GLU();
    public static void render(GL2 gl, Vector3D pos, double len, double rad, int axis) {
        GLUquadric quadric = glu.gluNewQuadric();
        gl.glPushMatrix();
        switch(axis) {
            case 0:
                gl.glRotated(-90.0,0.0,1.0,0.0);
                gl.glTranslated(0.0, 0.0, -0.5 * len);
                break;
            case 1:
                gl.glRotated(-90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -0.5 * len);
                break;
            case 2:
                gl.glTranslated(0.0, 0.0, -0.5 * len);
                break;
            default:
                gl.glTranslated(0.0, 0.0, -0.5 * len);
        }

        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
        glu.gluCylinder(quadric, rad, rad, len, 16, 16);

        gl.glPushMatrix();
        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        glu.gluDisk(quadric, 0.0, rad, 16, 16);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, len);
        glu.gluDisk(quadric, 0.0, rad, 16, 16);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }
}
