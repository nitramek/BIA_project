package cz.nitramek.bia.gui.algorithm;

import java.awt.event.ActionListener;
import java.util.List;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.SimulatedAnnealing;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.AccessLevel;
import lombok.Setter;


public class SimulatedAnnealingPanel extends AlgorithmPanel {

    @Setter(AccessLevel.PRIVATE)
    private double beta;
    @Setter(AccessLevel.PRIVATE)
    private double radius;

    @Setter(AccessLevel.PRIVATE)
    private double temperature;

    @Setter(AccessLevel.PRIVATE)
    private double finalTemperature;

    public SimulatedAnnealingPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener);
        super.addInGrid("Radius: ", 1, this::setRadius);
        super.addInGrid("Temperature: ", 1, this::setTemperature);
        super.addInGrid("Final temperature: ", 0.1, this::setFinalTemperature);
        super.addInGrid("Beta: ", 0.3, this::setBeta);

    }


    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int
            generationSize, int maximumGeneration, boolean discrete) {

        SimulatedAnnealing annealing = new SimulatedAnnealing(
                boundaries, discrete, evaluationFunction, maximumGeneration,
                this.radius, this.temperature, this.finalTemperature, this.beta);
        this.run(annealing);
        return annealing;
    }

    @Override
    public String getAlgorithmName() {
        return "Simulated annealing";
    }
}
