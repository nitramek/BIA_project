package cz.nitramek.bia.function;

import java.util.Arrays;

import static java.lang.Math.*;


public class AckleyTwo implements Function {
    @Override
    public double getValue(double... params) {
        if (Arrays.stream(params).allMatch(x -> x == 0)) {
            return 0;
        } else {
            return Function.calculateSumForTwo(params, this::calculateSingle);
        }

    }

    private double calculateSingle(double xi, double xi_1) {
        double exponent = 0.5 * (cos(2 * xi) + cos(2 * xi_1));
        return 20.0 + E - 20.0 / pow(E, 0.2 * sqrt((pow(xi, 2) + pow(xi_1, 2)) / 2)) - pow(E, exponent);
    }

    @Override
    public int getOptimalXMax() {
        return 20;
    }

    @Override
    public double getOptimalZMax() {
        return 20;
    }
}
