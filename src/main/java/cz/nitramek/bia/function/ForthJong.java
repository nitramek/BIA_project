package cz.nitramek.bia.function;


import java.util.stream.IntStream;

public class ForthJong implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        int length = params.length;
        return IntStream.range(1, length).mapToDouble(i -> {
            double xi = params[i - 1];
            return i * Math.pow(xi, 4.0);
        }).sum();
    }

    @Override
    public int getOptimalXMax() {
        return 2;
    }

    @Override
    public double getOptimalZMax() {
        return 8;
    }
}
