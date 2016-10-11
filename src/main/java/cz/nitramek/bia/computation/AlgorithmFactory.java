package cz.nitramek.bia.computation;


import cz.nitramek.bia.gui.AlgorithmSimulationModel;
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


    public Algorithm identity(AlgorithmSimulationModel model, int generationSize, boolean discrete) {
        Pair<Double, Double> xBoundary = new Pair<>((double) model.getXMin(),
                (double)model.getXMax());
        Pair<Double, Double> yBoundary = new Pair<>((double) model.getYMin(), (double) model.getYMax());
        return new Algorithm(Arrays.asList(xBoundary, yBoundary), generationSize, discrete);
    }

    public Algorithm walker(AlgorithmSimulationModel model, int generationSize, boolean discrete) {
        Algorithm identity = this.identity(model, generationSize, discrete);
        Function<Individual, Individual> walk = individual -> {
            if(discrete)
                individual.replaceParam(0, (int x) -> x + x/2);
            else
                individual.replaceParam(0, (double x) -> x + x/2);
            return individual;
        };
        identity.setManipulation(walk);
        return identity;
    }


}
