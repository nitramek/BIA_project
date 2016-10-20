package cz.nitramek.bia.gui.algorithm;


import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.BlindSearch;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BlindSearchPanel extends AlgorithmPanel {


    public BlindSearchPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener, new BorderLayout());
        this.add(new JLabel("Just a blind search"));
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
