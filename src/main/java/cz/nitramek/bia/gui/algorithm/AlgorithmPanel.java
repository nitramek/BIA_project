package cz.nitramek.bia.gui.algorithm;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.DoubleConsumer;

import javax.swing.*;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.cz.nitramek.bia.util.Util;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.AccessLevel;
import lombok.Setter;

public abstract class AlgorithmPanel extends JPanel {

    private final ActionListener guiUpdateListener;

    @Setter(AccessLevel.PRIVATE)
    private long delay;
    private int usedRows = 0;

    public AlgorithmPanel(ActionListener guiUpdateListener) {
        super(new GridBagLayout());
        this.addInGrid("Delay", 500, (delay1) -> this.setDelay((long) delay1));
        this.guiUpdateListener = guiUpdateListener;
    }

    public JTextField addInGrid(String label, Number value) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = this.usedRows++;
        this.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JTextField textField = new JTextField(value.toString());
        this.add(textField, gbc);
        return textField;
    }

    public void addInGrid(String label, double value, DoubleConsumer setter) {
        setter.accept(value);
        Util.bindProperty(addInGrid(label, value), setter);
    }


    public abstract Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary>
            boundaries, int generationSize, int
            maximumGeneration, boolean discrete);

    /**
     * Actually runs in another thread and updates GUI accordingly
     */
    protected void run(Algorithm algorithm) {
        Thread t = new Thread(() -> {
            while (!algorithm.isFinished()) {
                algorithm.advance();
                if (this.delay != 0) {
                    this.requestPlotUpdate();
                    try {
                        Thread.sleep(this.delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.requestPlotUpdate();
        });
        t.start();
    }

    public abstract String getAlgorithmName();

    public void requestPlotUpdate() {
        this.guiUpdateListener.actionPerformed(new ActionEvent(this, 0, "refresh"));
    }
}
