package cz.nitramek.bia.computation;


import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.cz.nitramek.bia.util.Util;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
    private int generationIndex = 1;

    @Getter
    @NonNull
    private List<Individual> generation;

    @Getter
    @Setter
    @NonNull
    private EvaluatingFunction evaluatingFunction;

    @Setter
    @NonNull
    private Function<Individual, Individual> manipulation = Function.identity();


    public Algorithm(List<Boundary> boundaries, int generationSize, boolean discrete) {
        this(boundaries, generationSize);
        Random random = new Random();

        this.generation = IntStream.range(0, generationSize)
                                   .mapToObj(i -> Individual.generate(boundaries, random, discrete))
                                   .collect(Collectors.toList());
    }

    private Algorithm(List<Boundary> boundaries, int generationSize) {
        this.boundaries = boundaries;
        this.generationSize = generationSize;
    }

    public void advance() {
        this.generation.replaceAll(manipulation::apply);
        this.generation.forEach(this::checkBoundaries);
        generationIndex++;
    }

    private void checkBoundaries(Individual individual) {
        double[] parameters = individual.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = this.boundaries.get(i).getInRange(parameters[i]);
        }
    }

    public void setManupulations(List<Function<Individual, Individual>> manipulations) {
        this.manipulation = manipulations.stream()
                                         .reduce(Function.identity(), Function::andThen);
    }


    public Individual getBest() {
        return getGeneration().stream()
                              .sorted(Comparator.comparing(i -> i.getFitness(this.evaluatingFunction)))
                              .findFirst().orElseThrow(Util.exceptionMessage("There are no individuals"));
    }
}
