package cz.nitramek.bia.cz.nitramek.bia.util;


import java.util.function.DoublePredicate;

public final class Util {
    /**
     * Square pow
     *
     * @return Druha mocnina cisla
     */
    public static double square(double x) {
        return Math.pow(x, 2);
    }

    public static DoublePredicate eq(double number) {
        return x -> x == number;
    }

}
