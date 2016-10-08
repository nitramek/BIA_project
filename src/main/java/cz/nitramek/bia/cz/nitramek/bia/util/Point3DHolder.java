package cz.nitramek.bia.cz.nitramek.bia.util;


import static cz.nitramek.bia.cz.nitramek.bia.util.Util.square;


public class Point3DHolder {
    public static float xRangeThreshold = 1;
    public static float yRangeThreshold = 1;


    public final float x;
    public final float y;
    public final float z;

    public Point3DHolder(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point3DHolder(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }
    public Point3DHolder(float x, float y) {
        this(x, y, 0.f);

    }
    public Point3DHolder(double x, double y) {
        this(x, y, 0);
    }


    public boolean isClose(float x, float y) {
        double distance = Math.sqrt(square(this.x - x) + square(this.y - y));
        return distance <= xRangeThreshold;
    }
}
