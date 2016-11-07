package cz.nitramek.bia.gui.algorithm;


import java.awt.event.ActionListener;
import java.util.List;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.SomaAllToOne;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.AccessLevel;
import lombok.Setter;

public class SomaPanel extends AlgorithmPanel {

    @Setter(AccessLevel.PRIVATE)
    private double PRT;

    @Setter(AccessLevel.PRIVATE)
    private double pathLength;

    @Setter(AccessLevel.PRIVATE)
    private double step;

    public SomaPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener);
        this.addInGrid("PTR", 0.1, this::setPRT);
        this.addInGrid("PathLength", 3, this::setPathLength);
        this.addInGrid("Step", 0.11, this::setStep);
    }

    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int
            generationSize, int maximumGeneration, boolean discrete) {
        SomaAllToOne somaAllToOne = new SomaAllToOne(boundaries, generationSize, discrete,
                evaluationFunction,
                maximumGeneration, PRT, pathLength, step, -1);
        this.run(somaAllToOne);
        return somaAllToOne;
    }

    @Override
    public String getAlgorithmName() {
        return "SOMA";
    }
}
