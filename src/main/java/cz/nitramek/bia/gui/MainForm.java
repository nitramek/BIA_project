package cz.nitramek.bia.gui;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainForm.java
 *
 * Created on Mar 13, 2010, 2:46:54 PM
 */


import cz.nitramek.bia.function.*;
import net.sf.surfaceplot.SurfaceCanvas;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author siva
 */
public class MainForm extends javax.swing.JFrame {

    private FunctionSurfaceModel model;

    public MainForm() throws ClassNotFoundException {
        BorderLayout borderLayout = new BorderLayout();
        Container container = getContentPane();
        container.setLayout(borderLayout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(800, 600);
        //8fci - 2 unimodalni, 6 multimodalnich
        Function firstDeJongFunction = new FirstJong();
        this.model = new FunctionSurfaceModel(firstDeJongFunction);


        SurfaceCanvas canvas = new SurfaceCanvas();
        canvas.setModel(this.model);
        canvas.setSize(400, 100);
        JPanel eastPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(eastPanel, BoxLayout.Y_AXIS);
        eastPanel.setLayout(boxLayout);


        Class<?>[] functionClasszes = {FirstJong.class, RosenBrock.class, ThirdJong.class, ForthJong.class, Rastrigin
                .class, Schwefel.class, Griewangk.class, SineEnvelope.class, StretchedSin.class, AckleyOne.class,
                AckleyTwo.class, EggHolder.class, Rana.class, Patological.class, Michalewicz.class, MastersCosine
                .class};

        Arrays.stream(functionClasszes).forEach(functionClass -> {
            eastPanel.add(addFunctionButton(functionClass, canvas));
        });

        container.add(canvas, BorderLayout.CENTER);
        container.add(eastPanel, BorderLayout.EAST);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
//        southPanel.add(new JLabel("Rotate: Mouse Click & Drag"));
//        southPanel.add(new JLabel("Zoom: Shift Key + Mouse Click & Drag"));
//        southPanel.add(new JLabel("Move: Control Key + Mouse Click & Drag"));
        JSlider slider = new JSlider(-30, 30);
        slider.addChangeListener(e -> {
            int value = slider.getValue();
            this.model.setMin(value);
            canvas.setModel(model);
            canvas.revalidate();
        });
        JSlider zMaxSlider = new JSlider(0, 100);
        zMaxSlider.addChangeListener(l -> {
            this.model.setZMax(zMaxSlider.getValue() * 100.f);
            canvas.setModel(model);
            canvas.invalidate();
        });

        southPanel.add(new JLabel("xMax/yMax"));
        southPanel.add(slider);
        southPanel.add(new JLabel("ZMax"));
        southPanel.add(zMaxSlider);
        //container.add(southPanel, BorderLayout.SOUTH);
        canvas.repaint();
        this.setVisible(true);

    }

    public static void main(String args[]) throws ClassNotFoundException {
        new MainForm().setVisible(true);
    }

    private JButton addFunctionButton(Class<?> functionClass, SurfaceCanvas canvas) {
        try {
            Function function = (Function) functionClass.newInstance();
            JButton button = new JButton(function.getName());
            int height = 30;
            int width = 170;
            button.setMaximumSize(new Dimension(width, height));
            button.setMinimumSize(new Dimension(width, height));
            button.setPreferredSize(new Dimension(width, height));
            button.addActionListener(e -> {
                this.model = new FunctionSurfaceModel(function);
                canvas.setModel(model);
                canvas.revalidate();
            });
            return button;
        } catch (InstantiationException | IllegalAccessException e1) {
            e1.printStackTrace();
            throw new RuntimeException(e1);
        }
    }
}
