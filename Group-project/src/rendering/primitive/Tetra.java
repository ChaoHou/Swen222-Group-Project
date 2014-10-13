package rendering.primitive;

import rendering.Vector3D;

import javax.media.opengl.GL2;

/**
 * Created by Kyohei Kudo
 */
public class Tetra {
    public static void render(GL2 gl, Vector3D pos, Vector3D size, int axis) {
        switch(axis) {
            case 0: //top
                gl.glRotated(-90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
            case 1: //north
                gl.glRotated(180.0, 0.0, 1.0, 0.0);
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
            case 2: //east
                gl.glRotated(90.0, 0.0, 1.0, 0.0);
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
            case 3: //south
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
            case 4: //west
                gl.glRotated(-90.0,0.0,1.0,0.0);
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
            case 5: //bottom
                gl.glRotated(90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
            default:
                gl.glRotated(-90.0, 1.0, 0.0, 0.0);
                gl.glTranslated(0.0, 0.0, -size.y());
                break;
        }
        Vector3D[] corner = new Vector3D[8];

        corner[0] = new Vector3D(0,0,0);
        corner[1] = new Vector3D(size.x(), 0, 0);
        corner[2] = new Vector3D(size.x()/2, 0, size.z());
        corner[3] = new Vector3D(size.x()/2, size.y(), 0);

        int[][] index = new int[][]{ { 0, 1, 2 },
                { 0, 2, 3 },
                { 0, 3, 1 },
                { 1, 3, 2 } };

        gl.glPushMatrix();
        gl.glTranslated(pos.x(),pos.y(),pos.z());
        gl.glBegin(GL2.GL_TRIANGLES);

        Vector3D[] temp = new Vector3D[3];
        for(int i = 0; i < 3; ++i) {temp[i] = corner[index[0][i]];}
        gl.glNormal3dv(Vector3D.normal(temp).toArray(),0);
        for(int i = 0; i < 3; ++i) gl.glVertex3dv(corner[index[0][i]].toArray(),0);

        for(int i = 0; i < 3; ++i) {temp[i] = corner[index[1][i]];}
        gl.glNormal3dv(Vector3D.normal(temp).toArray(),0);
        for(int i = 0; i < 3; ++i) gl.glVertex3dv(corner[index[1][i]].toArray(),0);

        for(int i = 0; i < 3; ++i) {temp[i] = corner[index[2][i]];}
        gl.glNormal3dv(Vector3D.normal(temp).toArray(),0);
        for(int i = 0; i < 3; ++i) gl.glVertex3dv(corner[index[2][i]].toArray(),0);

        for(int i = 0; i < 3; ++i) {temp[i] = corner[index[3][i]];}
        gl.glNormal3dv(Vector3D.normal(temp).toArray(),0);
        for(int i = 0; i < 3; ++i) gl.glVertex3dv(corner[index[3][i]].toArray(),0);


        gl.glEnd();
        gl.glPopMatrix();

    }
}
