package cz.nitramek.bia.cz.nitramek.bia.util;


import cz.nitramek.bia.function.EvaluatingFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static EvaluatingFunction createFunction(Class<?> function) {
        try {
            return (EvaluatingFunction) function.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method m, Object instance, Object... parameters) {
        try {
            return (T) m.invoke(instance, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
