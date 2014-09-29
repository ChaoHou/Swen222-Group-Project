package rendering;

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by innocence on 25/09/2014.
 */
public class RendererTest {
    public static void main(String[] args) {
        GLProfile glprofile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
        Renderer renderer = new Renderer(setRoom());
        glcanvas.addGLEventListener(renderer);
        glcanvas.addKeyListener(renderer);

        final JFrame jframe = new JFrame( "Rendering window" );
        jframe.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                jframe.dispose();
                System.exit( 0 );
            }
        });

        FPSAnimator animator= new FPSAnimator(glcanvas,60);
        animator.start();

        jframe.getContentPane().add( glcanvas, BorderLayout.CENTER );
        jframe.setSize( 640, 480 );
        jframe.setVisible( true );
    }

    private static Room setRoom() {
        List<Wall> walls = new ArrayList<Wall>();
        walls.add(new Wall(Wall.DIRECTION.NORTH));
        walls.add(new Wall(Wall.DIRECTION.SOUTH));
        walls.add(new Wall(Wall.DIRECTION.WEST));
        walls.add(new Wall(Wall.DIRECTION.EAST));
        Room tmp = new Room(walls);
        return tmp;
    }
}
