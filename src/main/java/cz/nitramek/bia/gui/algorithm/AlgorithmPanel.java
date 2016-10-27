package cz.nitramek.bia.gui.algorithm;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

public abstract class AlgorithmPanel extends JPanel {

    private final ActionListener guiUpdateListener;

    public AlgorithmPanel(ActionListener guiUpdateListener, LayoutManager layoutManager) {
        super(layoutManager);
        this.guiUpdateListener = guiUpdateListener;
    }
    public AlgorithmPanel(ActionListener guiUpdateListener) {
        super();
        this.guiUpdateListener = guiUpdateListener;
    }

    public void addInGrid(int row, int column, JComponent component){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = column;
        gbc.gridy = row;
        this.add(component, gbc);
    }
    public JTextField addInGrid(String label, Number value, int row){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        this.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JTextField textField = new JTextField(value.toString());;
        this.add(textField, gbc);
        return textField;
    }


    public abstract Algorithm start(EvaluatingFunction evaluationFunction, List<Boundary> boundaries, int generationSize, int
            maximumGeneration, boolean discrete);

    /**
     * Actually runs in another thread and updates GUI accordingly
     */
    protected void run(Algorithm algorithm) {
        Thread t = new Thread(() -> {
            while (!algorithm.isFinished()) {
                algorithm.advance();
                this.requestPlotUpdate();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public abstract String getAlgorithmName();

    public void requestPlotUpdate() {
        this.guiUpdateListener.actionPerformed(new ActionEvent(this, 0, "refresh"));
    }
}
