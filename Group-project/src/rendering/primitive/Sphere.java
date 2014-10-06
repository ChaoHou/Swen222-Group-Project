package rendering.primitive;

import com.jogamp.opengl.util.gl2.GLUT;
import rendering.Vector3df;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Draw a sphere by given x y z and rad
 * Created by Kyohei Kudo.
 */
public class Sphere {
    private static GLUT glut = new GLUT();
    public static void render(GL2 gl, Vector3df vec, double rad) {
        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Set The Color To Blue
//        GLUquadric sphere = glu.gluNewQuadric();
        gl.glPushMatrix();
        gl.glTranslated(vec.x(),vec.y(),vec.z());
        glut.glutSolidSphere(rad, 16, 16);
        gl.glPopMatrix();
//        glu.gluDeleteQuadric(sphere);
    }
}
