package cz.nitramek.bia.gui;


import com.carrotsearch.hppc.DoubleArrayList;
import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.AlgorithmFactory;
import cz.nitramek.bia.cz.nitramek.bia.util.Util;
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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

import static java.lang.Math.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class AlgorithmsForm extends JFrame {


    private final SurfaceCanvas canvas;
    private JButton applyAxisButton;
    private JComboBox<ComboItem<EvaluatingFunction>> comboBox;
    private AlgorithmSimulationModel model;


    private List<JTextField> spinners;
    private List<java.util.function.Function<AlgorithmSimulationModel, Float>> getters = Arrays
            .asList(AlgorithmSimulationModel::getXMin, AlgorithmSimulationModel::getXMax,
                    AlgorithmSimulationModel::getYMin,
                    AlgorithmSimulationModel::getYMax, AlgorithmSimulationModel::getZMin,
                    AlgorithmSimulationModel::getZMax);
    private List<java.util.function.BiConsumer<AlgorithmSimulationModel, Float>> setters = Arrays
            .asList(AlgorithmSimulationModel::setXMin, AlgorithmSimulationModel::setXMax,
                    AlgorithmSimulationModel::setYMin,
                    AlgorithmSimulationModel::setYMax, AlgorithmSimulationModel::setZMin,
                    AlgorithmSimulationModel::setZMax);
    private JComboBox<ComboItem<Method>> algorithmsComboBox;
    private JButton generateButton;
    private JTextArea individualsInput;
    private JButton paretBtn;
    private JButton advanceButton;


    public AlgorithmsForm(String frameName) {
        super(frameName);
        this.getContentPane().setLayout(new BorderLayout());
        this.canvas = new SurfaceCanvas();
        this.comboBox = new JComboBox<>();


        JPanel functionsPanel = new JPanel(new GridBagLayout());
        Dimension size = new Dimension(200, EntryPoint.HEIGHT);
        functionsPanel.setMinimumSize(size);
        functionsPanel.setPreferredSize(size);
        functionsPanel.setSize(size);

        this.setupFunctionPanel(functionsPanel);


        this.add(this.canvas, BorderLayout.CENTER);
        this.add(functionsPanel, BorderLayout.EAST);


        this.setupData();
        this.setupListeners();
    }

    private void setupFunctionPanel(JPanel functionsPanel) {
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
        this.setAxisPanel(axisPanel);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty = 0.;
        paretBtn = new JButton("Paret");
        functionsPanel.add(paretBtn, gbc);

        gbc.gridy++;
        JPanel algorithmPanel = new JPanel(new GridBagLayout());
        functionsPanel.add(algorithmPanel, gbc);


        setupAlgorithmPanel(algorithmPanel);

        //bump panel up
        gbc.gridy++;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //filler, which pushes components up the top
        functionsPanel.add(new JPanel(), gbc);
    }

    private void setupAlgorithmPanel(JPanel algorithmPanel) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.weighty = 0.;
        gbc.gridy = 0;
        gbc.gridx = 0;

        algorithmPanel.add(new JLabel("Algorithm"), gbc);


        this.algorithmsComboBox = new JComboBox<>();
        gbc.gridy++;
        algorithmPanel.add(algorithmsComboBox, gbc);

        this.individualsInput = new JTextArea("10");
        gbc.gridy++;
        algorithmPanel.add(individualsInput, gbc);

        gbc.gridy++;
        this.generateButton = new JButton("Generate");
        algorithmPanel.add(generateButton, gbc);

        gbc.gridy++;
        this.advanceButton = new JButton("Advance");
        algorithmPanel.add(advanceButton, gbc);
    }

    private void setAxisPanel(JPanel axisPanel) {
        GridBagConstraints gbc;
        JTextField spinnerXMin = new JTextField();
        JTextField spinnerXMax = new JTextField();
        JTextField spinnerYMin = new JTextField();
        JTextField spinnerYMax = new JTextField();
        JTextField spinnerZMin = new JTextField();
        JTextField spinnerZMax = new JTextField();
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
                                  .map(Util::createFunction)
                                  .map(ComboItem::createComboItem)
                                  .collect(collectingAndThen(
                                          toCollection(Vector::new),
                                          DefaultComboBoxModel::new));
        this.comboBox.setModel(comboBoxModel);

        Method[] methods = AlgorithmFactory.class.getDeclaredMethods();
        val algorithmComboBoxModel = Arrays.stream(methods)
                                           .filter(m -> !m.isSynthetic()) //odebere lambdy..
                                           .filter(m -> m.getReturnType() //vybere jen takové, kde je navr.typ Algoritm
                                                         .isAssignableFrom(Algorithm.class))
                                           .map(m -> ComboItem.createComboItem(m, Method::getName))
                                           .collect(collectingAndThen(
                                                   toCollection(Vector::new),
                                                   DefaultComboBoxModel::new));

        this.algorithmsComboBox.setModel(algorithmComboBoxModel);

        EvaluatingFunction evaluatingFunction = Util.createFunction(EntryPoint.functions[0]);
        this.model = new AlgorithmSimulationModel(evaluatingFunction);
        this.invalidateCanvas();
    }

    private void setupListeners() {
        //change function on change in combo box
        this.comboBox.addActionListener(e -> {
            ComboBoxModel<ComboItem<EvaluatingFunction>> model = this.comboBox.getModel();
            ComboItem<EvaluatingFunction> comboItem = model.getElementAt(this.comboBox.getSelectedIndex());
            EvaluatingFunction evaluatingFunction = comboItem.getItem();
            this.model = new AlgorithmSimulationModel(evaluatingFunction, this.model.getAlgorithm());
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
        //paret button function, opens new JFrame
        this.paretBtn.addActionListener(e -> {
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
        //this button is for generating new population on a new algorithm
        this.generateButton.addActionListener((e) -> {
            int individualCount = Integer.parseInt(individualsInput.getText());
            EvaluatingFunction evaluatingFunction = this.model.getEvaluatingFunction();
            //TODO choose functions
            Method algorithmFactoryMethod = this.algorithmsComboBox.getModel().getElementAt(this.algorithmsComboBox
                    .getSelectedIndex()).getItem();
            Algorithm algorithm = Util
                    .invokeMethod(algorithmFactoryMethod, AlgorithmFactory.$(), evaluatingFunction, individualCount);
            this.model = new AlgorithmSimulationModel(evaluatingFunction, algorithm);
            this.invalidateCanvas();
        });
        this.advanceButton.addActionListener(e -> {
            this.model.getAlgorithm().advance();
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
