package cz.nitramek.bia.computation;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

public class DifferentialEvolution extends Algorithm {

    private final double F;
    private final double CR;

    private Random random = new Random();

    public DifferentialEvolution(List<Boundary> boundaries, int generationSize, boolean discrete,
                                 EvaluatingFunction evaluatingFunction, int maximumGeneration,
                                 double F, double CR) {
        super(boundaries, generationSize, discrete, evaluatingFunction, maximumGeneration);
        this.F = F;
        this.CR = CR;
        super.setManipulation(this::crossOver);
    }

    public Individual crossOver(Individual old) {
        Individual noise = this.createNoise(old);
        Individual newIndividual = old.clone();
        for (int i = 0; i < noise.getDimension(); i++) {
            if (random.nextDouble() <= this.CR) {
                newIndividual.getParameters()[i] = noise.getParam(i);
            }
        }
        boolean isNewBetter = newIndividual.getFitness(super.getEvaluatingFunction()) < old
                .getFitness(super.getEvaluatingFunction());
        return isNewBetter ? newIndividual : old;
    }

    public Individual createNoise(Individual individual) {
        List<Individual> chosenOnes = random.ints(0, super.getGenerationSize())
                .mapToObj(super.getGeneration()::get)
                .filter(i -> !i.equals(individual))
                .limit(3)
                .collect(Collectors.toList());
        Individual x1 = chosenOnes.get(0);
        Individual x2 = chosenOnes.get(1);
        Individual x3 = chosenOnes.get(2);
        double[] noiseParameters = new double[x1.getDimension()];
        for (int i = 0; i < x1.getDimension(); i++) {
            noiseParameters[i] = x1.getParam(i) + this.F * (x2.getParam(i) - x3.getParam(i));
            if (super.isDiscrete()) {
                noiseParameters[i] = (int) noiseParameters[i];
            }
        }
        return new Individual(noiseParameters);
    }

}
