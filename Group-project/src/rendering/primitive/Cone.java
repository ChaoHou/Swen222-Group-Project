package rendering.primitive;

import com.jogamp.opengl.util.gl2.GLUT;
import rendering.Vector3D;

import javax.media.opengl.GL2;

/**
 * Created by innocence on 07/10/2014.
 */
public class Cone {
    public static void render(GL2 gl, Vector3D pos, double rad, double height, int axis) {
        GLUT glut = new GLUT();
        gl.glPushMatrix();
        gl.glTranslated(pos.x(), pos.y(), pos.z());
        switch(axis) {
            case 0:
                gl.glRotated(-90.0,0.0,1.0,0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            case 1:
                gl.glRotated(-90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -height);
                break;
            default:
                gl.glTranslated(0.0, 0.0, -height);
        }
        glut.glutSolidCone(rad,height,16,16);

        gl.glPopMatrix();

    }
}
