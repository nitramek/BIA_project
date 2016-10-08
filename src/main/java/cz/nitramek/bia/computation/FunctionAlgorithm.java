package cz.nitramek.bia.computation;

import cz.nitramek.bia.function.EvaluatingFunction;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class FunctionAlgorithm extends Algorithm {


    /**
     * Randomly generates parameters according to boundaries size / 2
     *
     * @param boundaries     first number is
     * @param generationSize
     */
    private FunctionAlgorithm(List<Pair<Double, Double>> boundaries, int generationSize) {
        super(boundaries, generationSize);
    }

    public static FunctionAlgorithm create(EvaluatingFunction evaluatingFunction, int generationSize) {

        Pair<Double, Double> xBoundary = new Pair<>((double) evaluatingFunction
                .getOptimalXMin(), (double) evaluatingFunction.getOptimalXMax());
        Pair<Double, Double> yBoundary = new Pair<>((double) evaluatingFunction
                .getOptimalYMin(), (double) evaluatingFunction.getOptimalYMax());

        return new FunctionAlgorithm(Arrays.asList(xBoundary, yBoundary), generationSize);

    }
}
