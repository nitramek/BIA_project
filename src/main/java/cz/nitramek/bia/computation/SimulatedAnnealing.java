package cz.nitramek.bia.computation;


import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimulatedAnnealing extends Algorithm {

    private double acceptanceProbability;

    private Individual individual;

    private final double radius;
    private final double probabilityDiff;

    private Random random = new Random();


    public SimulatedAnnealing(List<Boundary> boundaries, boolean discrete, EvaluatingFunction
            evaluatingFunction, int maximumGeneration, double radius, double acceptanceProbability, double
            probabilityDiff) {
        super(boundaries, 1, discrete, evaluatingFunction, maximumGeneration);
        this.radius = radius;
        this.acceptanceProbability = acceptanceProbability;
        this.probabilityDiff = probabilityDiff;
        this.individual = this.getGeneration().get(0);
    }

    @Override
    public void advance() {
        List<Boundary> boundaries = Arrays.stream(individual.getParameters())
                                          .mapToObj(p -> new Boundary(p - radius, p + radius))
                                          .collect(Collectors.toList());
        Individual newIndividual = Individual.generate(boundaries, this.random, super.isDiscrete());
        //jestlize je lepsi, tak se nahradi vzdycky s urcitou pravdepodobnosti i ten horsi

        double newFitness = newIndividual.getFitness(this.getEvaluatingFunction());
        double fitness = this.individual
                .getFitness(this.getEvaluatingFunction());
        if (newFitness < fitness) {
            //nove fitness je lepší jako staré
            this.individual = newIndividual;
        } else {
            //sance prijmuti horsiho vysledku
            if (this.random.nextDouble() > this.acceptanceProbability) {
                super.checkBoundaries(this.individual);
                this.individual = newIndividual;
            }
        }
        this.acceptanceProbability -= this.probabilityDiff;
        this.getGeneration().set(0, this.individual);
        super.advance();
    }
}
