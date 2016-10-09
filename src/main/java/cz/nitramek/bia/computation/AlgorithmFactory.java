package cz.nitramek.bia.computation;


import cz.nitramek.bia.function.EvaluatingFunction;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.function.Function;


public final class AlgorithmFactory {

    private static AlgorithmFactory instance = null;

    private AlgorithmFactory() {
    }


    public static AlgorithmFactory $() {
        if (instance == null) {
            instance = new AlgorithmFactory();
        }
        return instance;
    }


    public Algorithm identity(EvaluatingFunction evaluatingFunction, int generationSize) {
        Pair<Double, Double> xBoundary = new Pair<>((double) evaluatingFunction
                .getOptimalXMin(), (double) evaluatingFunction.getOptimalXMax());
        Pair<Double, Double> yBoundary = new Pair<>((double) evaluatingFunction
                .getOptimalYMin(), (double) evaluatingFunction.getOptimalYMax());
        return new Algorithm(Arrays.asList(xBoundary, yBoundary), generationSize);
    }

    public Algorithm walker(EvaluatingFunction evaluatingFunction, int generationSize) {

        Algorithm identity = this.identity(evaluatingFunction, generationSize);

        Function<Individual, Individual> walk = individual -> {
            individual.replaceParam(0, x -> x +x );
            return individual;
        };

        identity.setManipulation(walk);
        return identity;
    }


}
