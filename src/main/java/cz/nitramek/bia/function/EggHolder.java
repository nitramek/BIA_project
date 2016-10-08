package cz.nitramek.bia.function;


import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.sin;

public class EggHolder implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        return EvaluatingFunction.calculateSumForTwo(params, this::calculateSingle);
    }

    private double calculateSingle(double xi, double xi_1) {
        return -xi * sin(sqrt(abs(xi - xi_1 - 47))) - (xi_1 + 47) * sin(sqrt(abs(xi_1 + 47 + xi / 2.0)));
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
        return -500;
    }
}
