package rendering;

/**
 * Stores three floats as a vector information
 * Created by Kyohei Kudo.
 */
public class Vector3D {

	public final double x;
    public final double y;
    public final double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public static Vector3D normal(Vector3D... vertices) {
        if (vertices.length != 3) {
            System.err.printf("Can't calculate normal. Number of vertices are inappropriate.\n");
            return null;
        }
        return normalize(cross(subtract(vertices[1], vertices[0]), subtract(vertices[2], vertices[0])));
    }

    private static Vector3D normalize(Vector3D vector) {
        double length = Math.sqrt(vector.x()*vector.x()+vector.y()*vector.y()+vector.z()*vector.z());
        return new Vector3D(vector.x()/length,vector.y()/length,vector.z()/length);
    }

    private static Vector3D cross(Vector3D vec1, Vector3D vec2) {
        return new Vector3D(vec1.y()*vec2.z()-vec1.z()*vec2.y(),
                vec1.z()*vec2.x()-vec1.x()*vec2.z(),
                vec1.x()*vec2.y()-vec1.y()*vec2.x());
    }

    private static Vector3D subtract(Vector3D vec1, Vector3D vec2) {
        return new Vector3D(vec1.x()-vec2.x(),vec1.y()-vec2.y(),vec1.z()-vec2.z());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3D)) return false;

        Vector3D vector3D = (Vector3D) o;

        if (Double.compare(vector3D.x, x) != 0) return false;
        if (Double.compare(vector3D.y, y) != 0) return false;
        if (Double.compare(vector3D.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Vector3D clone(Object o) {
        if (!(o instanceof Vector3D)) return null;

        Vector3D v = (Vector3D) o;

        return new Vector3D(v.x(),v.y(),v.z());
    }

    public double[] toArray() {
        return new double[]{x,y,z};
    }
}
