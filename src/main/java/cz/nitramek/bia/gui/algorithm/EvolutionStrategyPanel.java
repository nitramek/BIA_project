package cz.nitramek.bia.gui.algorithm;

import java.awt.event.ActionListener;
import java.util.List;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.EvolutionStrategy;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.AccessLevel;
import lombok.Setter;


public class EvolutionStrategyPanel extends AlgorithmPanel {


    @Setter(AccessLevel.PRIVATE)
    private double mixParents;

    @Setter(AccessLevel.PRIVATE)
    private double deviation;

    @Setter(AccessLevel.PRIVATE)
    private double FV;

    public EvolutionStrategyPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener);
        this.addInGrid("Mix parents", 0, this::setMixParents);
        this.addInGrid("Deviation", 0.5, this::setDeviation);
        this.addInGrid("FV", 0.5, this::setFV);
    }

    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int
            generationSize, int maximumGeneration, boolean discrete) {
        EvolutionStrategy evolutionStrategy = new EvolutionStrategy(boundaries, generationSize,
                discrete, evaluationFunction, maximumGeneration, mixParents > 0, deviation, FV);
        this.run(evolutionStrategy);
        return evolutionStrategy;
    }

    @Override
    public String getAlgorithmName() {
        return "Evolution Strategy";
    }
}
