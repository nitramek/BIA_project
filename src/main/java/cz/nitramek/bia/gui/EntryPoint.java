package cz.nitramek.bia.gui;


import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.*;

import cz.nitramek.bia.function.AckleyOne;
import cz.nitramek.bia.function.AckleyTwo;
import cz.nitramek.bia.function.EggHolder;
import cz.nitramek.bia.function.FirstJong;
import cz.nitramek.bia.function.ForthJong;
import cz.nitramek.bia.function.Griewangk;
import cz.nitramek.bia.function.MastersCosine;
import cz.nitramek.bia.function.Michalewicz;
import cz.nitramek.bia.function.Paret;
import cz.nitramek.bia.function.Patological;
import cz.nitramek.bia.function.Rana;
import cz.nitramek.bia.function.Rastrigin;
import cz.nitramek.bia.function.RosenBrock;
import cz.nitramek.bia.function.Schwefel;
import cz.nitramek.bia.function.SineEnvelope;
import cz.nitramek.bia.function.StretchedSin;
import cz.nitramek.bia.function.ThirdJong;
import cz.nitramek.bia.gui.algorithm.AlgorithmPanel;
import cz.nitramek.bia.gui.algorithm.BlindSearchPanel;
import cz.nitramek.bia.gui.algorithm.DifferentialEvolutionPanel;
import cz.nitramek.bia.gui.algorithm.SimulatedAnnealingPanel;
import cz.nitramek.bia.gui.algorithm.SomaPanel;

public class EntryPoint {
    static final int HEIGHT = 600;
    static final int WIDTH = 400;
    static final Class<?>[] functions = {
            Paret.class, FirstJong.class, RosenBrock.class,
            ThirdJong.class, ForthJong.class, Rastrigin.class, Schwefel.class, Griewangk.class,
            SineEnvelope.class, StretchedSin.class, AckleyOne.class,
            AckleyTwo.class, EggHolder.class, Rana.class, Patological.class, Michalewicz.class,
            MastersCosine.class
    };

    static final List<Function<ActionListener, AlgorithmPanel>> algorithmPanels =
            Arrays.asList(BlindSearchPanel::new, SimulatedAnnealingPanel::new,
                    DifferentialEvolutionPanel::new, SomaPanel::new);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AlgorithmsForm frame = new AlgorithmsForm("FunctionForm");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(800, HEIGHT);
            frame.setVisible(true);
        });

    }
}
