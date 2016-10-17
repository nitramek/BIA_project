package cz.nitramek.bia.computation;


import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.cz.nitramek.bia.util.Util;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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


    public Algorithm(List<Boundary> boundaries, int generationSize, boolean discrete, EvaluatingFunction evaluatingFunction) {
        this.boundaries = boundaries;
        this.generationSize = generationSize;
        this.discrete = discrete;
        this.evaluatingFunction = evaluatingFunction;
        Random random = new Random();

        this.generation = IntStream.range(0, generationSize)
                                   .mapToObj(i -> Individual.generate(boundaries, random, discrete))
                                   .collect(Collectors.toList());
    }


    public void advance() {
        this.generation.replaceAll(manipulation::apply);
        generationIndex++;
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
