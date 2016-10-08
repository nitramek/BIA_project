package cz.nitramek.bia.function;

import static java.lang.Math.*;

//TODO kontrola
public class Patological implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        return EvaluatingFunction.calculateSumForTwo(params, (xi, xi_1) -> {
            double xi2 = pow(xi, 2);
            double xi_12 = pow(xi_1, 2);
            double divident = sin(pow(sqrt(100 * xi2 - xi_12), 2)) - 0.5;
            double divider = 1 + 0.001 * pow((xi2 - 2 * xi * xi_1 + xi_12), 2);
            return 0.5 + divident / divider;
        });
    }

    @Override
    public int getOptimalXMax() {
        return 100;
    }

    @Override
    public double getOptimalZMax() {
        return 1;
    }
}
