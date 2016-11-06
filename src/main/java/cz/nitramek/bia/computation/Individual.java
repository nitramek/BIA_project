package cz.nitramek.bia.computation;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.NonNull;


public class Individual{


    private static int idGenerator = 0;
    @Getter
    private final double parameters[];

    @Getter
    private double fitness;

    private final int id;

    public Individual(double[] parameters) {
        this.parameters = parameters;
        this.id = Individual.idGenerator++;
    }

    public static Individual generate(List<Boundary> boundaries, Random random, boolean discrete) {
        double[] parameters = boundaries.stream()
                                        .mapToDouble(p -> discrete ? p.randomInt(random) : p.randomDouble(random))
                                        .toArray();
        //polymorfmismus

        return new Individual(parameters);
    }

    public int getDimension() {
        return this.parameters.length;
    }

    public double getParam(int index) {
        return this.parameters[index];
    }

    public void setParam(int index, double param) {
        this.parameters[index] = param;
    }

    public void replaceParam(int index, DoubleUnaryOperator operator) {
        this.parameters[index] = operator.applyAsDouble(this.parameters[index]);
    }

    public void replaceParam(int index, IntUnaryOperator operator) {
        this.parameters[index] = operator.applyAsInt((int) this.parameters[index]);
    }

    public void replaceParam(double[] parameters) {
        assert parameters.length == this.parameters.length;
        IntStream.range(0, this.parameters.length)
                 .forEach(i -> this.parameters[i] = parameters[i]);
    }

    public double getFitness(@NonNull EvaluatingFunction evaluatingEvaluatingFunction) {
        this.fitness = evaluatingEvaluatingFunction.getValue(this.getParameters());
        return fitness;
    }
    public Individual clone(){
        return new Individual(this.getParameters().clone());
    }

    @Override
    public String toString() {
        return "Individual{" +
                "parameters=" + Arrays.toString(parameters) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
