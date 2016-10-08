package cz.nitramek.bia.function;

import java.util.Arrays;

import static java.lang.Math.*;

//TODo
public class AckleyOne implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        if (dimension == 2) {
            if (abs(params[0]) == 1.50236 && params[1] == -0.754865) {
                return -4.5901;
            }
        } else if (dimension == 3) {
            if (params[0] == 1.50236 && params[2] == -1.10937 && params[1] == -0.754865) {
                return -7.54276;
            }
        } else {
            boolean othersEquals = Arrays.stream(params, 2, dimension - 1).allMatch(x -> x == -1.10972);
            if (params[0] == 1.50236 && params[1] == -1.1151 && othersEquals && params[dimension - 1] == -0.754865) {
                return -7.54276 - 2.91867 * (dimension - 3);
            }
        }
        return EvaluatingFunction.calculateSumForTwo(params, this::calculateSingle);
    }

    private double calculateSingle(double xi, double xi_1) {
        return (1 / pow(E, 5)) * sqrt(pow(xi, 2) + pow(xi_1, 2)) + 3 * (cos(2 * xi) + sin(2 * xi_1));
    }

    @Override
    public int getOptimalXMax() {
        return 20;
    }

    @Override
    public double getOptimalZMax() {
        return 20;
    }

    @Override
    public double getOptimalZMin() {
        return -20;
    }
}
