package cz.nitramek.bia.computation;


import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.DoubleUnaryOperator;

@RequiredArgsConstructor()
public class Individual {

    @Getter
    private final double parameters[];


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

    public double getFitness(@NonNull EvaluatingFunction evaluatingEvaluatingFunction) {
        return evaluatingEvaluatingFunction.getValue(this.getParameters());
    }
}
