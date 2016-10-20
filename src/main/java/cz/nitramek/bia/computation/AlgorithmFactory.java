package cz.nitramek.bia.computation;


import cz.nitramek.bia.gui.AlgorithmSimulationModel;


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


    public Algorithm identity(AlgorithmSimulationModel model, int generationSize, boolean discrete, int
            maximumGeneration) {
        return new Algorithm(model.getBoundaries(), generationSize, discrete, model
                .getEvaluatingFunction(), maximumGeneration);
    }

    public Algorithm blindWalker(AlgorithmSimulationModel model, int generationSize, boolean discrete, int
            maximumGeneration) {
        return new BlindSearch(model.getBoundaries(), generationSize, discrete, model
                .getEvaluatingFunction(), maximumGeneration);
    }
}
