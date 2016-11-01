package cz.nitramek.bia.gui.algorithm;

import java.awt.event.ActionListener;
import java.util.List;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.DifferentialEvolution;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.AccessLevel;
import lombok.Setter;


public class DifferentialEvolutionPanel extends AlgorithmPanel {

    @Setter(AccessLevel.PRIVATE)
    private double F;

    @Setter(AccessLevel.PRIVATE)
    private double CR;

    public DifferentialEvolutionPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener);
        super.addInGrid("F", 0.9, this::setF);
        super.addInGrid("CR", 0.85, this::setCR);
    }

    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int
            generationSize, int maximumGeneration, boolean discrete) {
        DifferentialEvolution evolution = new DifferentialEvolution(boundaries,
                generationSize, discrete, evaluationFunction,
                maximumGeneration, this.F, this.CR);
        super.run(evolution);
        return evolution;
    }

    @Override
    public String getAlgorithmName() {
        return "Differential evolution";
    }
}
