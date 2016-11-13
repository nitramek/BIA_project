package cz.nitramek.bia.computation;


import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import cz.nitramek.bia.cz.nitramek.bia.util.Boundary;
import cz.nitramek.bia.function.EvaluatingFunction;

public class SomaAllToOne extends Algorithm {

    private final double PRT;
    private final double pathLength;
    private final double step;
    private Individual best;

    public SomaAllToOne(List<Boundary> boundaries, int generationSize, boolean discrete,
                        EvaluatingFunction evaluatingFunction, int maximumGeneration, double PRT,
                        double pathLength, double step, double minDiv) {
        super(boundaries, generationSize, discrete, evaluatingFunction, maximumGeneration);
        this.PRT = PRT;
        this.pathLength = pathLength;
        this.step = step;
        this.getGeneration()
                .forEach(i -> i.updateFitness(this.getEvaluatingFunction()));
        this.setManipulation(this::perturbate);
    }

    private Individual perturbate(Individual individual) {
        final double[] startParams = individual.getParameters();
        Individual bestJump = individual;
        bestJump.updateFitness(this.getEvaluatingFunction());
        double params[] = individual.getParameters().clone();
        for (double t = step; t <= pathLength; t += step) {
            int[] prtVector = genPRTVector();
            for (int i = 0; i < getDimension(); i++) {
                params[i] = startParams[i] + (best.getParam(i) - startParams[i]) * t * prtVector[i];
                if(isDiscrete()){
                    params[i] = (int) params[i];
                }
            }
            Individual jump = new Individual(params);
            jump.updateFitness(this.getEvaluatingFunction());
            if(jump.getFitness() < bestJump.getFitness()){
                bestJump = jump;
            }
        }
        return bestJump;
    }

    @Override
    public void advance() {
        this.best = this.getBest();
        super.advance();
    }

    public int[] genPRTVector() {
        while(true){
            int[] prt = IntStream.range(0, super.getDimension())
                    .mapToDouble(i -> Math.random())
                    .mapToInt(rand -> rand < this.PRT ? 1 : 0)
                    .toArray();
            if(Arrays.stream(prt).anyMatch(i -> i == 1)){
                return prt;
            }
        }

    }
}
