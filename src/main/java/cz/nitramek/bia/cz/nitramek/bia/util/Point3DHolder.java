package cz.nitramek.bia.cz.nitramek.bia.util;


import static java.lang.Math.*;


public class Point3DHolder {
    public static float xRangeTreshold = 1;
    public static float yRangeTreshold = 1;
    public static float zRangeTreshold = 1;


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


    public boolean isXCloseTo(float x){
        float range = abs(abs(x) - abs(this.x));
        return range <= xRangeTreshold;
    }
    public boolean isYCloseTo(float y){
        float range = abs(abs(y) - abs(this.y));
        return range <= yRangeTreshold;
    }
    public boolean isZCloseTo(float z){
        float range = abs(abs(z) - abs(this.z));
        return range <= zRangeTreshold;
    }

}
