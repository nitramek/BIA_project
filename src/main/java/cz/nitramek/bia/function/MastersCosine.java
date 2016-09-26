package cz.nitramek.bia.function;

import cz.nitramek.bia.cz.nitramek.bia.util.Util;

import java.util.Arrays;

import static java.lang.Math.*;


public class MastersCosine implements Function {
    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        if (Arrays.stream(params).allMatch(Util.eq(0))) {
            return -1 * dimension;
        }
        return Function.calculateSumForTwo(params, (xi, xi_1) -> {
            double xi_12 = pow(xi_1, 2);
            double xi2 = pow(xi, 2);
            double half = 0.5 * xi * xi_1;
            return pow(E, (-(xi2 + xi_12 + half) / 8)) * cos(4 * sqrt(xi2 + xi_12 + half));
        });
    }

    @Override
    public int getOptimalXMax() {
        return 5;
    }

    @Override
    public double getOptimalZMax() {
        return 0.5;
    }

    @Override
    public double getOptimalZMin() {
        return -1;
    }
}
