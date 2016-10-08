package cz.nitramek.bia.cz.nitramek.bia.util;


import java.util.function.DoublePredicate;
import java.util.function.Supplier;

public final class Util {
    /**
     * Square pow
     *
     * @return Druha mocnina cisla
     */
    public static double square(double x) {
        return Math.pow(x, 2);
    }
    public static float square(float x) {
        return (float) Math.pow(x, 2);
    }

    public static DoublePredicate eq(double number) {
        return x -> x == number;
    }

    public static Supplier<RuntimeException> exceptionMessage(String message){
        return () -> new RuntimeException(message);
    }

}
