package cz.nitramek.bia.function;


public class ProtocolThree implements Function {

    public static final int G_STAR_STAR = 12;
    public static final int G_STAR = 11;
    private static final double F = 1;

    @Override
    public double getValue(double... params) {
        int dimension = params.length;
        assert dimension == 2;
        double g = 10 + params[1];
        double alfa = 0.25 + 3.75 * ((g - G_STAR_STAR) / (G_STAR - G_STAR_STAR));
        double f1 = params[0];
        double f1Slashg = f1 / g;
        double h = Math.pow(f1Slashg, alfa) - f1Slashg * Math
                .sin(Math.PI * F * f1 * g);
        return h;
    }

    @Override
    public int getOptimalXMax() {
        return 20;
    }

    @Override
    public double getOptimalZMax() {
        return 20;
    }
}
