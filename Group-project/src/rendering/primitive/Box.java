package rendering.primitive;

import rendering.Vector3D;

import javax.media.opengl.GL2;

/**
 * Created by Kyohei Kudo.
 */
public class Box {
    public static void render(GL2 gl, Vector3D pos, Vector3D size) {
        Vector3D[] corner = new Vector3D[8];

        corner[0] = new Vector3D(0,0,0);
        corner[1] = new Vector3D(0, size.y(), 0);
        corner[2] = new Vector3D(0, size.y(), size.z());
        corner[3] = new Vector3D(0, 0, size.z());

        corner[4] = new Vector3D( size.x(), 0, 0);
        corner[5] = new Vector3D( size.x(), size.y(), 0);
        corner[6] = new Vector3D( size.x(), size.y(), size.z());
        corner[7] = new Vector3D( size.x(), 0, size.z());

        int[][] index = new int[][]{ { 3, 2, 1, 0 },
            { 4, 5, 6, 7 },
            { 3, 0, 4, 7 },
            { 1, 2, 6, 5 },
            { 0, 1, 5, 4 },
            { 2, 3, 7, 6 } };

        gl.glPushMatrix();
        gl.glTranslated(pos.x(),pos.y(),pos.z());
        gl.glBegin(GL2.GL_QUADS);

        gl.glNormal3d(-1.0, 0.0, 0.0);
        for(int i = 0; i < 4; ++i) gl.glVertex3dv(corner[index[0][i]].toArray(),0);
        gl.glNormal3d(1.0, 0.0, 0.0);
        for(int i = 0; i < 4; ++i) gl.glVertex3dv(corner[index[1][i]].toArray(),0);

        gl.glNormal3d(0.0, -1.0, 0.0);
        for(int i = 0; i < 4; ++i) gl.glVertex3dv(corner[index[2][i]].toArray(),0);
        gl.glNormal3d(0.0, 1.0, 0.0);
        for(int i = 0; i < 4; ++i) gl.glVertex3dv(corner[index[3][i]].toArray(),0);

        gl.glNormal3d(0.0, 0.0, -1.0);
        for(int i = 0; i < 4; ++i) gl.glVertex3dv(corner[index[4][i]].toArray(),0);
        gl.glNormal3d(0.0, 0.0, 1.0);
        for(int i = 0; i < 4; ++i) gl.glVertex3dv(corner[index[5][i]].toArray(),0);

        gl.glEnd();
        gl.glPopMatrix();

    }
}
