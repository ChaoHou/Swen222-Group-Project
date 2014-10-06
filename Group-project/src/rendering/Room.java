package rendering;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyohei Kudo on 25/09/2014.
 */
public class Room {
	
    private float rquad = 0.0f;
    private List<Wall> walls = new ArrayList<Wall>();

    public Room(List<Wall> walls) {
        this.walls = walls;
    }

    public void render(GL2 gl/*,Texture texture*/) {
        float x = 10.0f;
        float y = 10.0f;
        float z = 10.0f;

//        gl.glLoadIdentity();
//        gl.glTranslatef(0.0f, 0.0f, -6.0f);
//        gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
        
//        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBegin(GL2.GL_QUADS);            // Draw A Quad

		
        //texture.enable(gl);
        //texture.bind(gl);
        
        gl.glNormal3f(0.0f,0.0f,-1.0f);
        gl.glColor3f(1.0f, 0.5f, 0.0f);     // Set The Color To Green
//        gl.glTexCoord2f(texture.getImageTexCoords().left(), texture.getImageTexCoords().bottom());
        gl.glVertex3f(x, y, -z);   // Top Right Of The Quad (Top)
//        gl.glTexCoord2f(texture.getImageTexCoords().right(), texture.getImageTexCoords().bottom());
        gl.glVertex3f(-x, y, -z);  // Top Left Of The Quad (Top)
//        gl.glTexCoord2f(texture.getImageTexCoords().right(), texture.getImageTexCoords().top());
        gl.glVertex3f(-x, y, z);   // Bottom Left Of The Quad (Top)
//        gl.glTexCoord2f(texture.getImageTexCoords().left(), texture.getImageTexCoords().top());
        gl.glVertex3f(x, y, z);    // Bottom Right Of The Quad (Top)

        
        gl.glEnd();
        
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glColor3f(1.0f, 0.5f, 0.0f);     // Set The Color To Orange
//        gl.glTexCoord2f(texture.getImageTexCoords().left(), texture.getImageTexCoords().bottom());
        gl.glVertex3f(x, -y, z);   // Top Right Of The Quad (Bottom)
//        gl.glTexCoord2f(texture.getImageTexCoords().right(), texture.getImageTexCoords().bottom());
        gl.glVertex3f(-x, -y, z);  // Top Left Of The Quad (Bottom)
//        gl.glTexCoord2f(texture.getImageTexCoords().right(), texture.getImageTexCoords().top());
        gl.glVertex3f(-x, -y, -z); // Bottom Left Of The Quad (Bottom)
//        gl.glTexCoord2f(texture.getImageTexCoords().left(), texture.getImageTexCoords().top());
        gl.glVertex3f(x, -y, -z);  // Bottom Right Of The Quad (Bottom)

        gl.glEnd();
//        gl.glDisable(GL.GL_TEXTURE_2D);
        
        for (Wall w: walls) {w.render(gl);}
    }

}
