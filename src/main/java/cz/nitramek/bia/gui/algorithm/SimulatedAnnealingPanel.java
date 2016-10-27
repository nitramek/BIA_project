package cz.nitramek.bia.gui.algorithm;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.SimulatedAnnealing;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;


public class SimulatedAnnealingPanel extends AlgorithmPanel {

    private final JTextField radiusField;
    private final JTextField temperatureField;
    private final JTextField finalTemperatureField;

    public SimulatedAnnealingPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener, new GridBagLayout());
        this.radiusField = super.addInGrid("Radius: ", 1, 0);
        this.temperatureField = super.addInGrid("Temperature: ", 800, 1);
        this.finalTemperatureField = super.addInGrid("Final temperature: ", 30, 2);
    }


    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int
            generationSize, int maximumGeneration, boolean discrete) {
        double radius = Math.abs(Double.parseDouble(this.radiusField.getText()));
        double temperature = Math.abs(Double.parseDouble(this.temperatureField.getText()));
        double finalTemperature = Math.abs(Double.parseDouble(this.finalTemperatureField.getText
                ()));


        SimulatedAnnealing annealing = new SimulatedAnnealing(boundaries, discrete,
                evaluationFunction,
                maximumGeneration, radius, temperature, finalTemperature);
        this.run(annealing);
        return annealing;
    }

    @Override
    public String getAlgorithmName() {
        return "Simulated annealing";
    }
}
