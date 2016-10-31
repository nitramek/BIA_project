package cz.nitramek.bia.computation;


import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

public class SimulatedAnnealing extends Algorithm {

    private final double beta;
    private final double[][] covariance;

    private Individual solution;

    private final double radius;
    private final double finalTemperature;

    private double temperature;


    private Random random = new Random();


    public SimulatedAnnealing(List<Boundary> boundaries, boolean discrete, EvaluatingFunction
            evaluatingFunction, int maximumGeneration, double radius, double temperature,
                              double finalTemperature, double beta) {
        super(boundaries, 1, discrete, evaluatingFunction, maximumGeneration);
        this.beta = beta;
        this.radius = radius;
        this.temperature = temperature;
        this.finalTemperature = finalTemperature;
        this.solution = this.getGeneration().get(0);
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

        for (int i = 0; i < this.getMaximumGeneration(); i++) {
            MultivariateNormalDistribution mvn = new MultivariateNormalDistribution(this.solution
                    .getParameters(), this.covariance);
//            List<Boundary> boundaries = Arrays.stream(this.solution.getParameters())
//                    .mapToObj(p -> new Boundary(p - radius, p + radius))
//                    .collect(Collectors.toList());
//            Individual newSolution = Individual.generate(boundaries, new Random(), this
//                    .isDiscrete());
            double newParameters[] = mvn.sample();
            if(super.isDiscrete()) {
                newParameters = Arrays.stream(newParameters).map(p -> (int) p).toArray();
            }
            Individual newSolution = new Individual(newParameters);
            super.checkBoundaries(newSolution);

            double newEnergy = newSolution.getFitness(this.getEvaluatingFunction());
            double currentEnergy = this.solution.getFitness(this.getEvaluatingFunction());

            if (this.acceptanceProbability(currentEnergy, newEnergy) > Math.random()) {
                this.solution = newSolution;
                this.getGeneration().set(0, this.solution);
            }
        }
        this.chill();
    }

    public double acceptanceProbability(double energy, double newEnergy) {
        if (newEnergy < energy) {
            return 1;
        } else {
            return Math.exp(-((newEnergy - energy)) / this.temperature);

        }
    }

    public void chill() {
        this.temperature = this.temperature / (1 + this.beta * this.temperature);
    }
}
