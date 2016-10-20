package cz.nitramek.bia.computation;


import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

import java.util.List;
import java.util.Random;

public class BlindSearch extends Algorithm {
    private Random random = new Random();

    public BlindSearch(List<Boundary> boundaries, int generationSize, boolean discrete, EvaluatingFunction evaluatingFunction,int
            maximumGeneration) {
        super(boundaries, generationSize , discrete, evaluatingFunction, maximumGeneration);
        this.setManipulation(this::mutate);
    }

    @Override
    public void advance() {
        Individual best = new Individual(getBest().getParameters());
        super.advance();
        super.getGeneration().set(0, best);
    }

    private Individual mutate(Individual individual) {
        return Individual.generate(this.getBoundaries(), this.random, this.isDiscrete());
    }


}
