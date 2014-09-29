package rendering;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innocence on 25/09/2014.
 */
public class Room {
    private float rquad = 0.0f;
    private List<Wall> walls = new ArrayList<Wall>();

    public Room(List<Wall> walls) {
        this.walls = walls;
    }

    public void render(GL2 gl) {
        float x = 10.0f;
        float y = 10.0f;
        float z = 10.0f;

        gl.glLoadIdentity();
//        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);            // Draw A Quad

        gl.glNormal3f(0.0f,0.0f,-1.0f);
        gl.glColor3f(1.0f, 0.5f, 0.0f);     // Set The Color To Green
        gl.glVertex3f(x, y, -z);   // Top Right Of The Quad (Top)
        gl.glVertex3f(-x, y, -z);  // Top Left Of The Quad (Top)
        gl.glVertex3f(-x, y, z);   // Bottom Left Of The Quad (Top)
        gl.glVertex3f(x, y, z);    // Bottom Right Of The Quad (Top)

        gl.glNormal3f(0.0f,-1.0f,0.0f);
        gl.glColor3f(1.0f, 0.5f, 0.0f);     // Set The Color To Orange
        gl.glVertex3f(x, -y, z);   // Top Right Of The Quad (Bottom)
        gl.glVertex3f(-x, -y, z);  // Top Left Of The Quad (Bottom)
        gl.glVertex3f(-x, -y, -z); // Bottom Left Of The Quad (Bottom)
        gl.glVertex3f(x, -y, -z);  // Bottom Right Of The Quad (Bottom)
        rquad += 0.15;

        for (Wall w: walls) {w.render(gl);}
    }
}
