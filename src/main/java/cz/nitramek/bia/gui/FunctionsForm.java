package cz.nitramek.bia.gui;


import cz.nitramek.bia.function.Function;
import lombok.val;
import net.sf.surfaceplot.SurfaceCanvas;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class FunctionsForm extends JFrame {


    private final SurfaceCanvas canvas;
    private JButton applyAxisButton;
    private JComboBox<ComboItem<Function>> comboBox;
    private FunctionSurfaceModel model;


    private List<JSpinner> spinners;
    private List<java.util.function.Function<FunctionSurfaceModel, Float>> getters = Arrays
            .asList(FunctionSurfaceModel::getXMin, FunctionSurfaceModel::getXMax, FunctionSurfaceModel::getYMin,
                    FunctionSurfaceModel::getYMax, FunctionSurfaceModel::getZMin, FunctionSurfaceModel::getZMax);
    private List<java.util.function.BiConsumer<FunctionSurfaceModel, Float>> setters = Arrays
            .asList(FunctionSurfaceModel::setXMin, FunctionSurfaceModel::setXMax, FunctionSurfaceModel::setYMin,
                    FunctionSurfaceModel::setYMax, FunctionSurfaceModel::setZMin, FunctionSurfaceModel::setZMax);


    public FunctionsForm(String frameName) {
        super(frameName);
        this.getContentPane().setLayout(new BorderLayout());
        this.canvas = new SurfaceCanvas();
        this.comboBox = new JComboBox<>();


        JPanel functionsPanel = new JPanel(new GridBagLayout());
        Dimension size = new Dimension(200, EntryPoint.HEIGHT);
        functionsPanel.setMinimumSize(size);
        functionsPanel.setPreferredSize(size);
        functionsPanel.setSize(size);

        this.setupAxisPanel(functionsPanel);


        this.add(this.canvas, BorderLayout.CENTER);
        this.add(functionsPanel, BorderLayout.EAST);


        this.setupData();
        this.setupListeners();
    }

    public static Function createFunction(Class<?> function) {
        try {
            return (Function) function.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupAxisPanel(JPanel functionsPanel) {
        JSpinner spinnerXMin = new JSpinner();
        JSpinner spinnerXMax = new JSpinner();
        JSpinner spinnerYMin = new JSpinner();
        JSpinner spinnerYMax = new JSpinner();
        JSpinner spinnerZMin = new JSpinner();
        JSpinner spinnerZMax = new JSpinner();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.weighty = 0.;
        gbc.gridy = 0;
        gbc.gridx = 0;
        functionsPanel.add(this.comboBox, gbc);

        JPanel axisPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 0.;
        functionsPanel.add(axisPanel, gbc);
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //filler, which pushes components up the top
        functionsPanel.add(new JPanel(), gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 1;
        gbc.weightx = 0.3;


        //first row
        axisPanel.add(new JLabel("Axis"), gbc);
        gbc.gridx = 1;
        axisPanel.add(new JLabel("Min"), gbc);
        gbc.gridx = 2;
        axisPanel.add(new JLabel("Max"), gbc);

        //second row
        gbc.gridx = 0;
        gbc.gridy = 1;
        axisPanel.add(new JLabel("X"), gbc);
        gbc.gridx = 1;
        gbc.ipadx = 50;
        axisPanel.add(spinnerXMin, gbc);
        gbc.gridx = 2;
        axisPanel.add(spinnerXMax, gbc);

        //third row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        axisPanel.add(new JLabel("Y"), gbc);
        gbc.gridx = 1;
        gbc.ipadx = 50;
        axisPanel.add(spinnerYMin, gbc);
        gbc.gridx = 2;
        axisPanel.add(spinnerYMax, gbc);


        //fourth row
        gbc.gridx = 0;
        gbc.gridy = 3;
        axisPanel.add(new JLabel("Z"), gbc);
        gbc.gridx = 1;

        axisPanel.add(spinnerZMin, gbc);
        gbc.gridx = 2;
        axisPanel.add(spinnerZMax, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        this.applyAxisButton = new JButton("Apply");
        axisPanel.add(applyAxisButton, gbc);
        this.spinners = Arrays.asList(spinnerXMin, spinnerXMax, spinnerYMin, spinnerYMax, spinnerZMin, spinnerZMax);
    }

    private void setupData() {
        val comboBoxModel = Arrays.stream(EntryPoint.functions)
                                  .map(FunctionsForm::createFunction)
                                  .map(ComboItem::createComboItem)
                                  .collect(collectingAndThen(
                                          toCollection(Vector::new),
                                          DefaultComboBoxModel::new));
        this.comboBox.setModel(comboBoxModel);
        this.model = new FunctionSurfaceModel(createFunction(EntryPoint.functions[0]));
        this.invalidateCanvas();
    }

    private void setupListeners() {
        //change function on change in combo box
        this.comboBox.addActionListener(e -> {
            ComboBoxModel<ComboItem<Function>> model = this.comboBox.getModel();
            ComboItem<Function> comboItem = model.getElementAt(this.comboBox.getSelectedIndex());
            Function function = comboItem.getItem();
            this.model = new FunctionSurfaceModel(function);
            this.invalidateCanvas();
        });
        //on axis apply button
        this.applyAxisButton.addActionListener(e -> {
            IntStream.range(0, this.spinners.size()).forEach(i -> {
                Number number = (Number) this.spinners.get(i).getValue();
                this.setters.get(i).accept(this.model, number.floatValue());
            });
            this.invalidateCanvas();
        });
    }

    private void invalidateCanvas() {
        IntStream.range(0, this.spinners.size()).forEach(i -> {
            this.spinners.get(i).setValue(this.getters.get(i).apply(this.model));
        });

        this.canvas.setModel(this.model);
        this.canvas.invalidate();
    }
}
