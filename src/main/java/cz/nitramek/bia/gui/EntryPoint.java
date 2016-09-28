package cz.nitramek.bia.gui;


import cz.nitramek.bia.function.*;

import javax.swing.*;

public class EntryPoint {
    static final int HEIGHT = 600;
    static final int WIDTH = 400;
    static final Class<?>[] functions = {FirstJong.class, RosenBrock.class, ThirdJong.class, ForthJong.class,
            Rastrigin.class, Schwefel.class, Griewangk.class, SineEnvelope.class, StretchedSin.class, AckleyOne.class,
            AckleyTwo.class, EggHolder.class, Rana.class, Patological.class, Michalewicz.class, MastersCosine.class};

    public static void main(String[] args) {
        JFrame frame = new JFrame("FunctionForm");
        frame.setContentPane(new FunctionForm().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
