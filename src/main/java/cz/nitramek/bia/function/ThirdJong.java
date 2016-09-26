package cz.nitramek.bia.function;


import java.util.Arrays;

public class ThirdJong implements Function {

    @Override
    public double getValue(double... params) {
        return Arrays.stream(params).map(Math::abs).sum();
    }

    @Override
    public int getOptimalXMax() {
        return 2;
    }

    @Override
    public int getOptimalYMax() {
        return 2;
    }

    @Override
    public double getOptimalZMax() {
        return 4;
    }
}
