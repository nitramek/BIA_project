package cz.nitramek.bia.function;

import cz.nitramek.bia.cz.nitramek.bia.util.Util;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Math.*;


public class RosenBrock implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        boolean allOnes = Arrays.stream(params).allMatch(Util.eq(1));
        if (allOnes) {
            return 0;
        } else {
            return IntStream.range(0, dimension - 1).mapToDouble(i -> {
                //unimodalni jedno minimum
                //100 * (xi*2 - xi+1
                double firstBracket = pow(params[i], 2) - params[i + 1];
                double secondBracket = 1 - params[i];
                return 100.0 * pow(firstBracket, 2) + pow(secondBracket, 2);
            }).sum();

        }
    }

    @Override
    public int getOptimalXMax() {
        return 2;
    }

    @Override
    public double getOptimalZMax() {
        return 3000;
    }
}
