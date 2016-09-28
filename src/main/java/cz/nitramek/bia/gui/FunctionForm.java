package cz.nitramek.bia.gui;

import cz.nitramek.bia.function.Function;
import net.sf.surfaceplot.SurfaceCanvas;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;


public class FunctionForm {
    private final SurfaceCanvas canvas;
    private FunctionSurfaceModel model;
    private JPanel mainPanel;
    private JPanel functionPanel;
    private JPanel controlsPanel;
    private JComboBox<ComboItem<Function>> comboBox;

    public FunctionForm() {
        //setup GUI
        this.functionPanel.setSize(EntryPoint.WIDTH, EntryPoint.HEIGHT);
        this.canvas = new SurfaceCanvas();
        this.functionPanel.add(this.canvas);

        Dimension size = new Dimension(200, EntryPoint.HEIGHT);
        this.controlsPanel.setMinimumSize(size);
        this.controlsPanel.setPreferredSize(size);
        this.controlsPanel.setSize(size);


        this.setupData();
        this.addListeners();
    }

    private static Function createFunction(Class<?> function) {
        try {
            return (Function) function.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



    private void setupData() {
        @SuppressWarnings("unchecked")
        DefaultComboBoxModel<ComboItem<Function>> comboBoxModel = Arrays.stream(EntryPoint.functions)
                                                                        .map(FunctionForm::createFunction)
                                                                        .map(ComboItem::createComboItem)
                                                                        .collect(collectingAndThen(
                                                                                toCollection(Vector::new),
                                                                                DefaultComboBoxModel::new));

        this.comboBox.setModel(comboBoxModel);
        this.model = new FunctionSurfaceModel(createFunction(EntryPoint.functions[0]));
        this.canvas.setModel(this.model);
    }

    private void addListeners() {
        //change function on change in combo box
        this.comboBox.addActionListener(e -> {
            ComboBoxModel<ComboItem<Function>> model = this.comboBox.getModel();
            ComboItem<Function> comboItem = model.getElementAt(this.comboBox.getSelectedIndex());
            Function item = comboItem.getItem();
            this.model.setFunction(item);
            this.canvas.setModel(this.model);
            this.canvas.invalidate();
        });
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
