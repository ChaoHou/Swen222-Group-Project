package rendering.primitive;

import com.jogamp.opengl.util.gl2.GLUT;
import rendering.Vector3D;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Draw a sphere by given x y z and rad
 * Created by Kyohei Kudo.
 * ID:300287923
 */
public class Sphere {
    private static GLUT glut = new GLUT();
    public static void render(GL2 gl, Vector3D pos, double rad) {
        gl.glPushMatrix();
        gl.glTranslated(pos.x(), pos.y(), pos.z());
        glut.glutSolidSphere(rad, 16, 16);
        gl.glPopMatrix();
    }
}
