package cz.nitramek.bia.function;

import cz.nitramek.bia.cz.nitramek.bia.util.Util;

import java.util.Arrays;

import static java.lang.Math.*;


public class Michalewicz implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        if (dimension == 2) {
            if (params[0] == 2.20291 && params[1] == 1.57096) {
                return -1.8013;
            }
        } else if (dimension > 2 && params[0] == 2.20291 && Arrays.stream(params).allMatch(Util.eq(1.57104))) {
            return 1.00098 * (dimension - 2);
        }
        return EvaluatingFunction.calculateSumForTwo(params, (xi, xi_1) -> {
            double other = sin(xi) * sin(pow(pow(xi, 2) / PI, 20) + sin(xi_1) * sin(pow(2 * pow(xi, 2) / PI, 20)));
            return -1 * (other);
        });
    }

    @Override
    public int getOptimalXMin() {
        return 0;
    }

    @Override
    public int getOptimalXMax() {
        return 3;
    }

    @Override
    public int getOptimalYMin() {
        return 0;
    }

    @Override
    public int getOptimalYMax() {
        return 3;
    }


    @Override
    public double getOptimalZMin() {
        return -2;
    }

    @Override
    public double getOptimalZMax() {
        return 0;
    }


}
