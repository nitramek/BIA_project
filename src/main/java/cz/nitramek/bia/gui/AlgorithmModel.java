package cz.nitramek.bia.gui;


import cz.nitramek.bia.computation.Algorithm;
import cz.nitramek.bia.computation.Individual;
import cz.nitramek.bia.function.EvaluatingFunction;


public class AlgorithmModel {

    private final int generationSize;
    private final int dimension;
    private final EvaluatingFunction evaluatingFunction;
    private final Algorithm algorithm;

    private Double[][] data;

    public AlgorithmModel(EvaluatingFunction function, Algorithm algorithm) {
        this.evaluatingFunction = function;
        this.algorithm = algorithm;
        this.dimension = this.algorithm.getBoundaries().size();
        this.generationSize = this.algorithm.getGenerationSize();
        this.data = new Double[this.generationSize][this.dimension + 1];
    }

    public Double[][] getData(){
        return this.data;
    }

    public void updateData() {
        for (int i = 0; i < this.generationSize; i++) {
            Individual individual = this.algorithm.getGeneration().get(i);
            double[] parameters = individual.getParameters();
            for (int j = 0; j < this.dimension; j++) {
                data[i][j] = parameters[j];
            }
            data[i][this.dimension] = individual.getFitness(this.evaluatingFunction);
        }
    }



}
