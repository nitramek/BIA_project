package cz.nitramek.bia.function;


//TODO?
public class PseudoDirak implements EvaluatingFunction {
    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        double product = 1;
//        for (int i = 0; i < dimension - 1; i++) {
//            product *= () / Math.pow(Math.E,
//        }
        return product;
    }

    @Override
    public int getOptimalXMax() {
        return 5;
    }

    @Override
    public double getOptimalZMin() {
        return -5;
    }

    @Override
    public double getOptimalZMax() {
        return 0;
    }
}
