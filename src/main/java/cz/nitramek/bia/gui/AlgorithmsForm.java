package cz.nitramek.bia.gui;


import com.carrotsearch.hppc.DoubleArrayList;

import net.sf.surfaceplot.SurfaceCanvas;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.Individual;
import cz.nitramek.bia.cz.nitramek.bia.util.Util;
import cz.nitramek.bia.function.EvaluatingFunction;
import cz.nitramek.bia.function.Paret;
import cz.nitramek.bia.gui.algorithm.AlgorithmPanel;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.val;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class AlgorithmsForm extends JFrame implements ActionListener {


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
    private JComboBox<ComboItem<AlgorithmPanel>> algorithmsComboBox;
    private JTextArea individualsInput;
    private JButton paretBtn;
    private JButton runButton;
    private JCheckBox discreteCheckBox;
    private JTextField generationSizeField;
    private JPanel algorithmHolderPanel;

    @Setter(AccessLevel.PRIVATE)
    private int dimension;
    private JButton openTableBtn;
    private JFrame generationFrame;
    private final JTable generationTable;


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

        this.algorithmHolderPanel.add(this.algorithmsComboBox.getModel().getElementAt(0).getItem());

        this.generationFrame = new JFrame("Generation");
        this.generationTable = new JTable();
        this.generationTable.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(generationTable);
        generationFrame.add(scroll);
        generationFrame.pack();
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


        this.setupAlgorithmPanel(algorithmPanel);


        gbc.gridy++;
        gbc.weighty = 0.;
        gbc.gridx = 0;

        this.algorithmHolderPanel = new JPanel(new BorderLayout());
        functionsPanel.add(this.algorithmHolderPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        functionsPanel.add(new JPanel(), gbc);
        //filler, which pushes components up the top
    }

    private void setupAlgorithmPanel(JPanel algorithmPanel) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.weighty = 0.;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;

        algorithmPanel.add(new JLabel("Algorithm"), gbc);


        this.algorithmsComboBox = new JComboBox<>();
        gbc.gridy++;
        algorithmPanel.add(algorithmsComboBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        algorithmPanel.add(new JLabel("Generation size"), gbc);
        this.individualsInput = new JTextArea("10");

        gbc.gridx = 1;
        algorithmPanel.add(individualsInput, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        this.discreteCheckBox = new JCheckBox("Discrete");
        algorithmPanel.add(discreteCheckBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        algorithmPanel.add(new JLabel("Generations: "), gbc);

        gbc.gridx = 1;
        this.generationSizeField = new JTextField("10");
        algorithmPanel.add(generationSizeField, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        algorithmPanel.add(new JLabel("Dimension: "), gbc);

        gbc.gridx = 1;
        JTextField dimensionsField = new JTextField("2");

        Util.bindProperty(dimensionsField, this::setDimension);
        algorithmPanel.add(dimensionsField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        this.openTableBtn = new JButton("Table");
        algorithmPanel.add(openTableBtn, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        this.runButton = new JButton("Run");
        algorithmPanel.add(runButton, gbc);

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
        this.spinners = Arrays.asList(spinnerXMin, spinnerXMax, spinnerYMin, spinnerYMax,
                spinnerZMin, spinnerZMax);
    }

    private void setupData() {
        val comboBoxModel = Arrays.stream(EntryPoint.functions)
                .map(Util::createFunction)
                .map(ComboItem::createComboItem)
                .collect(collectingAndThen(
                        toCollection(Vector::new),
                        DefaultComboBoxModel::new));
        this.comboBox.setModel(comboBoxModel);
        val algorithmModel =
                EntryPoint.algorithmPanels.stream()
                        .map(constructor -> constructor.apply(this))
                        .map(panel -> new ComboItem<>(panel, panel.getAlgorithmName()))
                        .collect(collectingAndThen(toCollection(Vector::new),
                                DefaultComboBoxModel::new));
        this.algorithmsComboBox.setModel(algorithmModel);

        EvaluatingFunction evaluatingFunction = Util.createFunction(EntryPoint.functions[0]);
        this.model = new AlgorithmSimulationModel(evaluatingFunction);
        this.invalidateCanvas();
    }

    private void setupListeners() {
        //change function on change in combo box
        this.comboBox.addActionListener(e -> {
            ComboBoxModel<ComboItem<EvaluatingFunction>> model = this.comboBox.getModel();
            ComboItem<EvaluatingFunction> comboItem = model.getElementAt(this.comboBox
                    .getSelectedIndex());
            EvaluatingFunction evaluatingFunction = comboItem.getItem();
            this.model = new AlgorithmSimulationModel(evaluatingFunction, this.model.getAlgorithm
                    ());
            this.invalidateCanvas();
        });
        //on axis applyAsDouble button
        this.applyAxisButton.addActionListener(e -> {
            IntStream.range(0, this.spinners.size()).forEach(i -> {
                Float number = Float.parseFloat(this.spinners.get(i).getText());
                this.setters.get(i).accept(this.model, number);
            });
            this.invalidateCanvas();
        });
        //paret button function, opens new JFrame
        this.paretBtn.addActionListener(e -> {
            XYChart chart = new XYChartBuilder().width(800).height(600).title("Gaussian Blobs")
                    .xAxisTitle("f1")
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
                double alfa = 0.25 + 3.75 * ((g - Paret.G_STAR_STAR) / (Paret.G_STAR - Paret
                        .G_STAR_STAR));
                double f1Slashg = f1 / g;
                double h = pow(f1Slashg, alfa) - f1Slashg *
                        sin(PI * Paret.F * f1 * g);
                xList.add(f1);
                yList.add(g * h);
            }

            chart.addSeries("Paret", xList.toArray(), yList.toArray());
            new SwingWrapper<>(chart).displayChart();
        });
        this.algorithmsComboBox.addActionListener(e -> {
            AlgorithmPanel algorithmPanel = this.algorithmsComboBox.getModel()
                    .getElementAt(this.algorithmsComboBox.getSelectedIndex())
                    .getItem();
            this.algorithmHolderPanel.removeAll();
            this.algorithmHolderPanel.add(algorithmPanel, BorderLayout.CENTER);
            this.algorithmHolderPanel.revalidate();

        });

        this.runButton.addActionListener(e -> {
            int individualCount = Integer.parseInt(individualsInput.getText());
            int maximumGenerations = Integer.parseInt(this.generationSizeField.getText());
            AlgorithmPanel algorithmPanel = this.algorithmsComboBox.getModel()
                    .getElementAt(this.algorithmsComboBox.getSelectedIndex()).getItem();
            Algorithm algorithm = algorithmPanel.start(this.model.getEvaluatingFunction(),
                    this.model.getBoundaries(), individualCount, maximumGenerations,
                    this.discreteCheckBox.isSelected());
            this.model.setAlgorithm(algorithm);
        });
        this.openTableBtn.addActionListener(e -> {
            this.generationFrame.setVisible(true);
        });
    }

    private void invalidateCanvas() {
        IntStream.range(0, this.spinners.size())
                .forEach(i -> this.spinners.get(i).setText(this.getters.get(i).apply(this.model)
                        .toString()));

        this.canvas.setModel(this.model);
        this.canvas.invalidate();
    }

    //function to update GUI, it is expected to be called from another thread, thus invokelater
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SwingUtilities.invokeAndWait(this::generationChanged);
        } catch (InterruptedException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    private void generationChanged() {
        Object[] columnNames = Stream.concat(
                                  IntStream.range(0, this.dimension).mapToObj(i -> "x"+ i),
                                  Stream.of("Fitness"))
                              .toArray();
        int generationSize = Integer.parseInt(this.generationSizeField.getText());
        Double[][] data = new Double[generationSize][this.dimension + 1];
        List<Individual> individuals = this.model.getAlgorithm().getGeneration();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j == data[0].length - 1) {
                    data[i][j] = individuals.get(i).getFitness(this.model.getEvaluatingFunction());
                } else {
                    data[i][j] = individuals.get(i).getParam(j);
                }
            }
        }
        this.generationTable.setModel(new DefaultTableModel(data, columnNames));
        this.invalidateCanvas();
    }
}
