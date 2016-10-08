package cz.nitramek.bia.function;


import cz.nitramek.bia.cz.nitramek.bia.util.BiDoubleFunction;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public interface EvaluatingFunction {
    static double calculateSumForTwo(double[] params, BiDoubleFunction function) {
        return IntStream.rangeClosed(1, params.length - 1).mapToDouble(i -> function.apply(params[i - 1], params[i]))
                        .sum();
    }

    static DoubleStream calculateForTwo(double[] params, BiDoubleFunction function) {
        return IntStream.rangeClosed(1, params.length - 1).mapToDouble(i -> function.apply(params[i - 1], params[i]));
    }

    static DoubleStream calculateForOne(double[] params, DoubleUnaryOperator function) {
        return Arrays.stream(params).map(function);
    }

    static double calculateSumForOne(double[] params, DoubleUnaryOperator function) {
        return Arrays.stream(params).map(function).sum();
    }

    double getValue(double... params);

    default String getName() {
        return this.getClass().getSimpleName();
    }

    int getOptimalXMax();

    /**
     * @return Defualtni implementace je že vrátí X hodnotu
     */
    default int getOptimalYMax() {
        return this.getOptimalXMax();
    }

    double getOptimalZMax();

    default int getOptimalYMin() {
        return -this.getOptimalYMax();
    }

    default int getOptimalXMin() {
        return -this.getOptimalXMax();
    }

    /**
     * @return Defaultně vrátí nulu
     */
    default double getOptimalZMin() {
        return 0;
    }
}
