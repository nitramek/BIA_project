package cz.nitramek.bia.function;


import java.util.Arrays;

import static java.lang.Math.*;

public class Griewangk implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        if (Arrays.stream(params).sum() == 0.0) {
            return 0.0;
        } else {
            double sum = 0;
            double product = 1;
            for (int i = 1; i < params.length; i++) {
                double xi = params[i];
                sum += pow(xi, 2) / 4000.0;
                product *= cos(xi / sqrt(i));
            }
            return 1 + sum - product;
        }
    }

    @Override
    public int getOptimalXMax() {
        return 50;
    }

    @Override
    public double getOptimalZMax() {
        return 3;
    }
}
