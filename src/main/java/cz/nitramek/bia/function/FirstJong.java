package cz.nitramek.bia.function;


import cz.nitramek.bia.cz.nitramek.bia.util.Util;

import java.util.Arrays;

public class FirstJong implements EvaluatingFunction {

    @Override
    public double getValue(double... params) {
        boolean allZeros = Arrays.stream(params).allMatch(Util.eq(0));
        if (allZeros) {
            return 0;
        } else {
            return Arrays.stream(params).map(Util::square).sum();
        }
    }

    @Override
    public int getOptimalXMax() {
        return 5;
    }

    @Override
    public double getOptimalZMax() {
        return 40;
    }
}
