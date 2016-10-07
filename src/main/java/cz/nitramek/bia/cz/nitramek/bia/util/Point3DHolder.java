package cz.nitramek.bia.cz.nitramek.bia.util;


import lombok.Data;

import static java.lang.Math.*;

@Data
public class Point3DHolder {
    public static float xRangeTreshold = 1;
    public static float yRangeTreshold = 1;
    public static float zRangeTreshold = 1;


    public final float x;
    public final float y;
    public final float z;

    public boolean isXCloseTo(float x){
        float range = abs(abs(x) - abs(this.x));
        return range <= xRangeTreshold;
    }
    public boolean isYCloseTo(float y){
        float range = abs(abs(y) - abs(this.x));
        return range <= yRangeTreshold;
    }
    public boolean isZCloseTo(float z){
        float range = abs(abs(z) - abs(this.x));
        return range <= zRangeTreshold;
    }

}
