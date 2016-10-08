package cz.nitramek.bia.function;


import cz.nitramek.bia.cz.nitramek.bia.util.Util;

import java.util.Arrays;

import static java.lang.Math.*;

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
        return 500;
    }

    @Override
    public double getOptimalZMin() {
        return -500;
    }
}
