package cz.nitramek.bia.function;


import static java.lang.Math.*;

public class StretchedSin implements EvaluatingFunction {

    @Override
    public double getValue(double... params) {
        return EvaluatingFunction.calculateSumForTwo(params, (xi, xi_1) -> {
            double xi_pow2 = pow(xi, 2);
            double xi_1_pow2 = pow(xi_1, 2);
            return cbrt(xi_pow2 + xi_1_pow2) * pow(sin(50.0 * pow(xi_pow2 + xi_1_pow2, 0.1)), 2) + 1;
        });
    }

    @Override
    public int getOptimalXMax() {
        return 10;
    }

    @Override
    public double getOptimalZMax() {
        return 6;
    }

}
