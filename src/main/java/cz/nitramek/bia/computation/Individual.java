package cz.nitramek.bia.computation;


import cz.nitramek.bia.function.EvaluatingFunction;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
public class Individual {

    @Getter
    private final double parameters[];


    public int getDimension() {
        return this.parameters.length;
    }

    double getParam(int index) {
        return this.parameters[index];

    }

    void setParam(int index, double param) {
        this.parameters[index] = param;
    }

    double getFitness(@NonNull EvaluatingFunction evaluatingEvaluatingFunction) {
        return evaluatingEvaluatingFunction.getValue(this.getParameters());
    }
}
