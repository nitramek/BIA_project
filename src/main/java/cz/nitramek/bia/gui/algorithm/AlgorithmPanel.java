package cz.nitramek.bia.gui.algorithm;


import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
                    Thread.sleep(500);
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
