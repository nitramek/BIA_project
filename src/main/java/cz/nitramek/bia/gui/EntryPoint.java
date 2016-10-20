package cz.nitramek.bia.gui;


import cz.nitramek.bia.function.*;
import cz.nitramek.bia.gui.algorithm.AlgorithmPanel;
import cz.nitramek.bia.gui.algorithm.BlindSearchPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class EntryPoint {
    static final int HEIGHT = 600;
    static final int WIDTH = 400;
    static final Class<?>[] functions = {Paret.class, FirstJong.class, RosenBrock.class, ThirdJong.class, ForthJong
            .class,
            Rastrigin.class, Schwefel.class, Griewangk.class, SineEnvelope.class, StretchedSin.class, AckleyOne.class,
            AckleyTwo.class, EggHolder.class, Rana.class, Patological.class, Michalewicz.class, MastersCosine.class};

    static final List<Function<ActionListener, AlgorithmPanel>> algorithmPanels = Arrays.asList(BlindSearchPanel::new);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AlgorithmsForm frame = new AlgorithmsForm("FunctionForm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, HEIGHT);
            frame.setVisible(true);
        });

    }
}
