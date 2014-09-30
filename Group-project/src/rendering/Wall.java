package rendering;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLAutoDrawable;

/**
 * Created by Kyohei Kudo on 25/09/2014.
 */
public class Wall {
    private final DIRECTION dir;
    private float rquad = 0.0f;
    private float x = 10.0f;
    private float y = 10.0f;
    private float z = 10.0f;

    public Wall(DIRECTION dir) {
        this.dir = dir;
    }

    public enum DIRECTION {
        NORTH,
        SOUTH,
        WEST,
        EAST,
    }

    public void render(GL2 gl) {
        gl.glLoadIdentity();
        gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);            // Draw A Quad

        switch (dir) {
            case NORTH:
                gl.glColor3f(1.0f, 0.0f, 0.0f);     // Set The Color To Red
                gl.glVertex3f(x, y, z);    // Top Right Of The Quad (Front)
                gl.glVertex3f(-x, y, z);   // Top Left Of The Quad (Front)
                gl.glVertex3f(-x, -y, z);  // Bottom Left Of The Quad (Front)
                gl.glVertex3f(x, -y, z);   // Bottom Right Of The Quad (Front)
                break;

            case SOUTH:
                gl.glNormal3f(-1.0f, 0.0f, 0.0f);
                gl.glColor3f(1.0f, 1.0f, 0.0f);     // Set The Color To Yellow
                gl.glVertex3f(x, -y, -z);  // Bottom Left Of The Quad (Back)
                gl.glVertex3f(-x, -y, -z); // Bottom Right Of The Quad (Back)
                gl.glVertex3f(-x, y, -z);  // Top Right Of The Quad (Back)
                gl.glVertex3f(x, y, -z);   // Top Left Of The Quad (Back)
                break;

            case WEST:
                gl.glNormal3f(-1.0f, 0.0f, 0.0f);
                gl.glColor3f(0.0f, 0.0f, 1.0f);     // Set The Color To Blue
                gl.glVertex3f(-x, y, z);   // Top Right Of The Quad (Left)
                gl.glVertex3f(-x, y, -z);  // Top Left Of The Quad (Left)
                gl.glVertex3f(-x, -y, -z); // Bottom Left Of The Quad (Left)
                gl.glVertex3f(-x, -y, z);  // Bottom Right Of The Quad (Left)
                break;

            case EAST:
                gl.glNormal3f(-1.0f, 0.0f, 0.0f);
                gl.glColor3f(1.0f, 0.0f, 1.0f);     // Set The Color To Violet
                gl.glVertex3f(x, y, -z);   // Top Right Of The Quad (Right)
                gl.glVertex3f(x, y, z);    // Top Left Of The Quad (Right)
                gl.glVertex3f(x, -y, z);   // Bottom Left Of The Quad (Right)
                gl.glVertex3f(x, -y, -z);  // Bottom Right Of The Quad (Right)
                break;
        }
        gl.glEnd();                         // Done Drawing The Quad

//        gl.glBegin(GL2.GL_QUADS);
//        gl.glNormal3d(0.0, 1.0, 0.0);
//        gl.glColor3f(1, 1, 1);
//        gl.glVertex3d(0.1,0.1,-10.0);
//        gl.glVertex3d(0.1,0.9,-10.0);
//        gl.glVertex3d(0.9,0.9,-10.0);
//        gl.glVertex3d(0.9,0.1,-10.0);
//        gl.glEnd();
    }

    public void rotateR() {
        rquad += 90.0;

    }

    public void rotateL() {
        rquad -= 90.0;

    }
}
