package cz.nitramek.bia.gui;


import com.carrotsearch.hppc.DoubleArrayList;
import cz.nitramek.bia.function.EvaluatingFunction;
import cz.nitramek.bia.function.Paret;
import lombok.val;
import net.sf.surfaceplot.SurfaceCanvas;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

import static java.lang.Math.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class FunctionsForm extends JFrame {


    private final SurfaceCanvas canvas;
    private JButton applyAxisButton;
    private JComboBox<ComboItem<EvaluatingFunction>> comboBox;
    private FunctionSurfaceModel model;


    private List<JTextField> spinners;
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

    public static EvaluatingFunction createFunction(Class<?> function) {
        try {
            return (EvaluatingFunction) function.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupAxisPanel(JPanel functionsPanel) {
        JTextField spinnerXMin = new JTextField();
        JTextField spinnerXMax = new JTextField();
        JTextField spinnerYMin = new JTextField();
        JTextField spinnerYMax = new JTextField();
        JTextField spinnerZMin = new JTextField();
        JTextField spinnerZMax = new JTextField();
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


        gbc.gridy = 3;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //filler, which pushes components up the top
        functionsPanel.add(new JPanel(), gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = 0.;
        JButton paretBtn = new JButton("Paret");
        paretBtn.addActionListener(e -> {
            XYChart chart = new XYChartBuilder().width(800).height(600).title("Gaussian Blobs").xAxisTitle("f1")
                                                .yAxisTitle("g").build();

            // Customize Chart
            chart.getStyler().setChartTitleVisible(false);
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
            chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
            chart.getStyler().setMarkerSize(4);
            int maxSize = 30_000;
            DoubleArrayList xList = new DoubleArrayList(maxSize);
            DoubleArrayList yList = new DoubleArrayList(maxSize);

            Random random = new Random();
            for (int i = 0; i < maxSize; i++) {
                float x1 = random.nextFloat();
                float x2 = random.nextFloat();
                float f1 = x1;
                float g = 10 + x2;
                double alfa = 0.25 + 3.75 * ((g - Paret.G_STAR_STAR) / (Paret.G_STAR - Paret.G_STAR_STAR));
                double f1Slashg = f1 / g;
                double h = pow(f1Slashg, alfa) - f1Slashg *
                        sin(PI * Paret.F * f1 * g);


                xList.add(f1);
                yList.add(g * h);

            }

            chart.addSeries("Paret", xList.toArray(), yList.toArray());


            new SwingWrapper<>(chart).displayChart();
        });
        functionsPanel.add(paretBtn, gbc);

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
            ComboBoxModel<ComboItem<EvaluatingFunction>> model = this.comboBox.getModel();
            ComboItem<EvaluatingFunction> comboItem = model.getElementAt(this.comboBox.getSelectedIndex());
            EvaluatingFunction evaluatingFunction = comboItem.getItem();
            this.model = new FunctionSurfaceModel(evaluatingFunction);
            this.invalidateCanvas();
        });
        //on axis apply button
        this.applyAxisButton.addActionListener(e -> {
            IntStream.range(0, this.spinners.size()).forEach(i -> {
                Float number = Float.parseFloat(this.spinners.get(i).getText());
                this.setters.get(i).accept(this.model, number);
            });
            this.invalidateCanvas();
        });
    }

    private void invalidateCanvas() {
        IntStream.range(0, this.spinners.size())
                 .forEach(i -> this.spinners.get(i).setText(this.getters.get(i).apply(this.model).toString()));

        this.canvas.setModel(this.model);
        this.canvas.invalidate();
    }
}
