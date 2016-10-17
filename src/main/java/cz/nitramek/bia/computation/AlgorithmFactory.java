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

    public Algorithm identity(AlgorithmSimulationModel model, int generationSize, boolean discrete) {
        return new Algorithm(model.getBoundaries(), generationSize, discrete);
    }
}
