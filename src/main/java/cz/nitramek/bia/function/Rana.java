package cz.nitramek.bia.function;

import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;


public class Rana implements Function {
    private static DoubleUnaryOperator sqrtAbs = x -> sqrt(abs(x));

    @Override
    public double getValue(double... params) {
        return Function.calculateSumForTwo(params, (xi, xi_1) -> {
            double minusXi = sqrtAbs.applyAsDouble(xi_1 + 1 - xi);
            double plusXi = sqrtAbs.applyAsDouble(xi_1 + 1 + xi);
            return xi * sin(minusXi) * cos(plusXi) + (xi_1 + 1) * cos(minusXi) * sin(plusXi);
        });
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
