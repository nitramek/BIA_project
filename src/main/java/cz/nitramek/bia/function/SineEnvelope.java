package cz.nitramek.bia.function;


import static java.lang.Math.pow;
import static java.lang.Math.sin;

//TODO ověřit
public class SineEnvelope implements Function {

    @Override
    public double getValue(double... params) {
        return -Function.calculateSumForTwo(params, (xi, xi_1) -> {
            double xi_pow2 = pow(xi, 2);
            double xi_1_pow2 = pow(xi_1, 2);
            double topSide = sin(pow(xi_pow2 + xi_1_pow2 - 0.5, 2));
            double bottomSide = pow(1. + 0.001 * (xi_pow2 + xi_1_pow2), 2);
            return 0.5 + topSide / bottomSide;
        });
    }

    @Override
    public int getOptimalXMax() {
        return 10;
    }

    @Override
    public double getOptimalZMax() {
        return 0.5;
    }

    @Override
    public double getOptimalZMin() {
        return -1.5;
    }
}
