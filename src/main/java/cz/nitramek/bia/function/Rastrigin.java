package cz.nitramek.bia.function;


import java.util.Arrays;

import static java.lang.Math.*;

public class Rastrigin implements Function {
    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        if (Arrays.stream(params).sum() == 0.0) {
            return -200 * dimension;
        } else {
            double sum = Arrays.stream(params).map(xi -> pow(xi, 2) - 10 * cos(PI * xi)).sum();
            return 2 * dimension * sum;
        }
    }

    @Override
    public int getOptimalXMax() {
        return 5;
    }


    @Override
    public double getOptimalZMax() {
        return 1000;
    }
}
