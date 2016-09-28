package cz.nitramek.bia.gui;


import cz.nitramek.bia.function.Function;
import lombok.val;
import net.sf.surfaceplot.SurfaceCanvas;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class FunctionsForm extends JFrame {


    private final SurfaceCanvas canvas;
    private final JSpinner spinnerXMin;
    private final JSpinner spinnerXMax;
    private final JSpinner spinnerYMin;
    private final JSpinner spinnerYMax;
    private final JSpinner spinnerZMin;
    private final JSpinner spinnerZMax;
    private final JButton applyAxisButton;
    private FunctionSurfaceModel model;
    private JComboBox<ComboItem<Function>> comboBox;

    public FunctionsForm(String frameName) {
        super(frameName);
        this.getContentPane().setLayout(new BorderLayout());
        this.canvas = new SurfaceCanvas();
        this.comboBox = new JComboBox<>();
        this.spinnerXMin = new JSpinner();
        this.spinnerXMax = new JSpinner();
        this.spinnerYMin = new JSpinner();
        this.spinnerYMax = new JSpinner();
        this.spinnerZMin = new JSpinner();
        this.spinnerZMax = new JSpinner();


        JPanel functionsPanel = new JPanel(new GridBagLayout());
        Dimension size = new Dimension(200, EntryPoint.HEIGHT);
        functionsPanel.setMinimumSize(size);
        functionsPanel.setPreferredSize(size);
        functionsPanel.setSize(size);

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
        axisPanel.add(this.spinnerXMin, gbc);
        gbc.gridx = 2;
        axisPanel.add(this.spinnerXMax, gbc);

        //third row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        axisPanel.add(new JLabel("Y"), gbc);
        gbc.gridx = 1;
        gbc.ipadx = 50;
        axisPanel.add(this.spinnerYMin, gbc);
        gbc.gridx = 2;
        axisPanel.add(this.spinnerYMax, gbc);


        //fourth row
        gbc.gridx = 0;
        gbc.gridy = 3;
        axisPanel.add(new JLabel("Z"), gbc);
        gbc.gridx = 1;

        axisPanel.add(this.spinnerZMin, gbc);
        gbc.gridx = 2;
        axisPanel.add(this.spinnerZMax, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        this.applyAxisButton = new JButton("Apply");
        axisPanel.add(applyAxisButton, gbc);


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
            Function item = comboItem.getItem();
            this.model.setFunction(item);
            this.invalidateCanvas();
        });
        this.applyAxisButton.addActionListener(e -> {

        });
    }

    private void invalidateCanvas() {
        this.canvas.setModel(this.model);
        this.canvas.invalidate();
    }
}
