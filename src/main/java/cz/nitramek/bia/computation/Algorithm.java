package cz.nitramek.bia.computation;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.cz.nitramek.bia.util.Util;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


public class Algorithm {

    @Getter
    private final List<Boundary> boundaries;
    @Getter
    private final int generationSize;

    @Getter
    private final boolean discrete;

    @Getter
    private int generationIndex = 1;

    @Getter
    @NonNull
    private List<Individual> generation;

    @Getter
    @Setter
    @NonNull
    private EvaluatingFunction evaluatingFunction;


    @NonNull
    private Function<Individual, Individual> manipulation = Function.identity();

    @Getter
    private final int maximumGeneration;


    public Algorithm(List<Boundary> boundaries, int generationSize, boolean discrete,
                     EvaluatingFunction evaluatingFunction, int maximumGeneration) {
        this.boundaries = boundaries;
        this.generationSize = generationSize;
        this.discrete = discrete;
        this.evaluatingFunction = evaluatingFunction;
        this.maximumGeneration = maximumGeneration;
        Random random = new Random();

        this.generation = IntStream.range(0, generationSize)
                .mapToObj(i -> Individual.generate(boundaries, random, discrete))
                .collect(Collectors.toList());
    }


    public void advance() {
        if (!this.isFinished()) {
            this.generation = this.generation.stream()
                    .parallel()
                    .map(this.manipulation)
                    .collect(Collectors.toList());
            generationIndex++;
        }
    }

    public boolean isFinished() {
        return this.generationIndex > this.maximumGeneration;
    }

    protected void checkBoundaries(Individual individual) {
        double[] parameters = individual.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = this.boundaries.get(i).getInRange(parameters[i]);
        }
    }

    public void setManipulation(Function<Individual, Individual> manipulation) {
        this.setManipulations(Collections.singletonList(manipulation));
    }

    public void setManipulations(List<Function<Individual, Individual>> manipulations) {
        this.manipulation = manipulations.stream()
                .reduce(Function.identity(), Function::andThen);
        this.manipulation = this.manipulation.andThen(i -> {
            this.checkBoundaries(i);
            return i;
        });
    }


    public Individual getBest() {
        return getGeneration().stream()
                .sorted(Comparator.comparing(i -> i.getFitness(this.evaluatingFunction)))
                .findFirst().orElseThrow(Util.exceptionMessage("There are no individuals"));
    }
}
