package cz.nitramek.bia.gui;


import cz.nitramek.bia.function.*;

import javax.swing.*;

public class EntryPoint {
    static final int HEIGHT = 600;
    static final int WIDTH = 400;
    static final Class<?>[] functions = {Paret.class, FirstJong.class, RosenBrock.class, ThirdJong.class, ForthJong.class,
            Rastrigin.class, Schwefel.class, Griewangk.class, SineEnvelope.class, StretchedSin.class, AckleyOne.class,
            AckleyTwo.class, EggHolder.class, Rana.class, Patological.class, Michalewicz.class, MastersCosine.class};

    public static void main(String[] args) {

        AlgorithmsForm frame = new AlgorithmsForm("FunctionForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(HEIGHT, 600);
        frame.setVisible(true);
    }
}
