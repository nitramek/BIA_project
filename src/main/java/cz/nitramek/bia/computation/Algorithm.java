package cz.nitramek.bia.computation;


import cz.nitramek.bia.cz.nitramek.bia.util.Util;
import cz.nitramek.bia.function.EvaluatingFunction;
import javafx.util.Pair;
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

    private final List<Pair<Double, Double>> boundaries;
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

    /**
     * Randomly generates parameters according to boundaries size / 2
     *
     * @param boundaries first number is
     */
    public Algorithm(List<Pair<Double, Double>> boundaries, int generationSize) {
        this.boundaries = boundaries;
        this.generationSize = generationSize;
        Random random = new Random();
        this.generation = IntStream.range(0, generationSize)
                                   .mapToObj(i -> new Individual(generateParameters(boundaries, random)))
                                   .collect(Collectors.toList());
    }

    private double[] generateParameters(List<Pair<Double, Double>> boundaries, Random random) {
        return boundaries.stream()
                         .mapToDouble(p -> randomDouble(p, random))
                         .toArray();
    }

    private double randomDouble(Pair<Double, Double> boundary, Random random) {
        return random.nextDouble() * (boundary.getValue() - boundary.getKey()) + boundary.getKey();
    }

    public void advance() {
        this.generation.replaceAll(manipulation::apply);
        this.generation.forEach(this::checkBoundaries);
        generationIndex++;
    }

    private void checkBoundaries(Individual individual){
        double[] parameters = individual.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            double p = parameters[i];
            Double minimum = this.boundaries.get(i).getKey();
            Double maximum = this.boundaries.get(i).getValue();
            if(p < minimum){
                p+=minimum;
            }
            if(p > maximum){
                p-= maximum;
            }
            parameters[i] = p;
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
