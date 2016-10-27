package cz.nitramek.bia.computation;


import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

public class SimulatedAnnealing extends Algorithm {

    private static final double BETA = 0.0003;
    private final double[][] covariance;

    private Individual solution;

    private final double radius;
    private final double finalTemperature;

    private double temperature;


    private Random random = new Random();


    public SimulatedAnnealing(List<Boundary> boundaries, boolean discrete, EvaluatingFunction
            evaluatingFunction, int maximumGeneration, double radius, double temperature, double
                                      finalTemperature) {
        super(boundaries, 1, discrete, evaluatingFunction, maximumGeneration);
        this.radius = radius;
        this.temperature = temperature;
        this.finalTemperature = finalTemperature;
        this.solution = this.getGeneration().get(0);
        System.out.println("Starting with + " + this.solution);
        double[] diagonal = IntStream.range(0, boundaries.size()).mapToDouble(i ->
                radius).toArray();
        this.covariance = MatrixUtils.createRealDiagonalMatrix(diagonal).getData();
    }


    @Override
    public boolean isFinished() {
        return this.temperature <= this.finalTemperature;
    }


    @Override
    public void advance() {
        do {
            List<Boundary> boundaries = Arrays.stream(this.solution.getParameters()).mapToObj
                    (p -> new Boundary(p -
                            radius, p + radius)).collect(Collectors.toList());
            for (int i = 0; i < this.getGenerationSize(); i++) {

                Individual newSolution = Individual.generate(boundaries, random, this.isDiscrete());
                super.checkBoundaries(newSolution);

                double newEnergy = newSolution.getFitness(this.getEvaluatingFunction());
                double energy = this.solution.getFitness(this.getEvaluatingFunction());

                double v = acceptanceProbability(energy, newEnergy);
                System.out.println(v);
                if (v > Math.random()) {
                    this.solution = newSolution;
                    this.getGeneration().set(0, this.solution);
                }
            }
            this.chill();
        } while (!this.isFinished());
    }

    public double acceptanceProbability(double energy, double newEnergy) {
        if (newEnergy < energy) {
            return 1;
        } else {
            return Math.exp((energy - newEnergy) / this.temperature);
        }
    }

    public void chill() {
        this.temperature = this.temperature / (1 + BETA * this.temperature);
    }
}
