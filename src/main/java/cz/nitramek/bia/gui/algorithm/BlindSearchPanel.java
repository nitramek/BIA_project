package cz.nitramek.bia.gui.algorithm;


import java.awt.event.ActionListener;
import java.util.List;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.BlindSearch;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

public class BlindSearchPanel extends AlgorithmPanel {


    public BlindSearchPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener);
    }

    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int generationSize, int maximumGeneration, boolean
            discrete) {
        BlindSearch algorithm = new BlindSearch(boundaries, generationSize, discrete, evaluationFunction,
                maximumGeneration);
        this.run(algorithm);
        return algorithm;
    }

    @Override
    public String getAlgorithmName() {
        return "Blind Search";
    }
}
