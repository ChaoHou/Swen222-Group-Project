package rendering;

/**
 * Stores three floats as a vector information
 * Created by Kyohei Kudo.
 */
public class Vector3df {
    private final double x;
    private final double y;
    private final double z;
    public Vector3df(double x, double y, double z) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3df)) return false;

        Vector3df vector3df = (Vector3df) o;

        if (Double.compare(vector3df.x, x) != 0) return false;
        if (Double.compare(vector3df.y, y) != 0) return false;
        if (Double.compare(vector3df.z, z) != 0) return false;

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

    public Vector3df clone(Object o) {
        if (!(o instanceof Vector3df)) return null;

        Vector3df v = (Vector3df) o;

        return new Vector3df(v.x(),v.y(),v.z());
    }
}
