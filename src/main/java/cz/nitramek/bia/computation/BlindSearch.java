package cz.nitramek.bia.computation;


import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

import java.util.List;
import java.util.Random;

public class BlindSearch extends Algorithm {

    private final int maxEvaluationCount;
    private double bestResult;
    private Random random = new Random();
    private int evaluatinCount = 0;

    public BlindSearch(List<Boundary> boundaries, boolean discrete, EvaluatingFunction evaluatingFunction, int
            maxEvaluationCount) {
        super(boundaries, 1, discrete, evaluatingFunction);
        this.maxEvaluationCount = maxEvaluationCount;
        this.bestResult = this.getBest().getFitness(this.getEvaluatingFunction());
    }

    @Override
    public void advance() {
        Individual blindMan;
        while (true) {
            blindMan = Individual.generate(this.getBoundaries(), this.random, this.isDiscrete());
            this.checkBoundaries(blindMan);
            double newBestResult = blindMan.getFitness(this.getEvaluatingFunction());
            this.evaluatinCount++;
            if(this.evaluatinCount > this.maxEvaluationCount){
                return;
            }
            if (newBestResult < this.bestResult) {
                this.bestResult = newBestResult;
                this.getGeneration().set(0, blindMan);
                break;
            }
        }
    }


}
