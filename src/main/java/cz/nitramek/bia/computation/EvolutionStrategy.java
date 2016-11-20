package cz.nitramek.bia.computation;


import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.val;

public class EvolutionStrategy extends Algorithm {
    private final boolean mixParents;
    private final double stdDeviation;
    private final double FV;
    private final double[][] normalDistribution;
    private Random random = new Random();

    public EvolutionStrategy(List<Boundary> boundaries, int generationSize, boolean discrete,
                             EvaluatingFunction evaluatingFunction, int maximumGeneration,
                             boolean mixParents, double stdDeviation, double FV) {
        super(boundaries, generationSize, discrete, evaluatingFunction, maximumGeneration);
        this.mixParents = mixParents;
        this.stdDeviation = stdDeviation;
        this.FV = FV;
        double[] means = DoubleStream.iterate(0, DoubleUnaryOperator.identity())
                .limit(getDimension())
                .toArray();
        double[] deviations = DoubleStream.iterate(stdDeviation, DoubleUnaryOperator.identity())
                .limit(getDimension())
                .toArray();

        val mvn = new MultivariateNormalDistribution(means,
                MatrixUtils.createRealDiagonalMatrix(deviations).getData());
        this.setManipulation(this::mutate);
        this.normalDistribution = mvn.sample(maximumGeneration + 1);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || this.getBest().getFitness(this.getEvaluatingFunction()) <= FV;
    }

    public Individual mutate(Individual individual) {
        double[] parameters = individual.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            double normalDistributionRandom = random.nextGaussian() * stdDeviation;
            parameters[i] += normalDistributionRandom;
            if(this.isDiscrete()){
                parameters[i] = (int)parameters[i];
            }
        }
        return new Individual(parameters);
    }

    @Override
    public void advance() {
        List<Individual> parents = new ArrayList<>(this.getGeneration());
        super.advance();
        if (this.mixParents) {
            this.generation = Stream.concat(parents.stream(), this.getGeneration().stream())
                    .sorted(Comparator.comparing(i -> i.getFitness(this.getEvaluatingFunction())))
                    .limit(getGenerationSize())
                    .collect(Collectors.toList());
        }

    }
}
