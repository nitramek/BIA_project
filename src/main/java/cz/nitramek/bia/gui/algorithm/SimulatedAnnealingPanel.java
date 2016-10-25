package cz.nitramek.bia.gui.algorithm;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.SimulatedAnnealing;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;


public class SimulatedAnnealingPanel extends AlgorithmPanel {

    private final JTextField radiusField;
    private final JTextField propabilityField;
    private final JTextField probabilityDiffField;

    public SimulatedAnnealingPanel(ActionListener guiUpdateListener) {
        super(guiUpdateListener, new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Radius: "), gbc);

        gbc.gridx = 1;
        this.radiusField = new JTextField("1");
        this.add(this.radiusField, gbc);

        //2 radek
        gbc.gridy++;
        gbc.gridx = 0;
        this.add(new JLabel("Probability: "), gbc);

        gbc.gridx = 1;
        this.propabilityField = new JTextField("0.8");
        this.add(this.propabilityField, gbc);


        //3 radek
        gbc.gridy++;
        gbc.gridx = 0;
        this.add(new JLabel("Prob. diff: "), gbc);

        gbc.gridx = 1;
        this.probabilityDiffField = new JTextField("0.1");
        this.add(this.probabilityDiffField, gbc);
    }



    @Override
    public Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int generationSize, int
            maximumGeneration, boolean discrete) {
        double radius = Math.abs(Double.parseDouble(this.radiusField.getText()));
        double acceptanceProbability = Math.abs(Double.parseDouble(this.propabilityField.getText()));
        double acceptanceDiff = Math.abs(Double.parseDouble(this.probabilityDiffField.getText()));


        SimulatedAnnealing annealing = new SimulatedAnnealing(boundaries, discrete, evaluationFunction,
                maximumGeneration, radius, acceptanceProbability, acceptanceDiff);
        this.run(annealing);
        return annealing;
    }

    @Override
    public String getAlgorithmName() {
        return "Simulated annealing";
    }
}
