package cz.nitramek.bia.function;


import java.util.Arrays;

import cz.nitramek.bia.cz.nitramek.bia.util.Util;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Schwefel implements EvaluatingFunction {

    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        if (Arrays.stream(params).allMatch(Util.eq(420.969))) {
            return -418.983 * dimension;
        } else {
            return Arrays.stream(params).map(xi -> -xi * sin(sqrt(abs(xi)))).sum();
        }
    }

    @Override
    public int getOptimalXMax() {
        return 500;
    }

    @Override
    public double getOptimalZMax() {
        return 1000;
    }

    @Override
    public double getOptimalZMin() {
        return -1000;
    }
}
