package cz.nitramek.bia.cz.nitramek.bia.util;


import com.carrotsearch.hppc.DoubleArrayList;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Random;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import cz.nitramek.bia.function.EvaluatingFunction;

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


    public static Supplier<RuntimeException> exceptionMessage(String message) {
        return () -> new RuntimeException(message);
    }

    public static EvaluatingFunction createFunction(Class<?> function) {
        try {
            return (EvaluatingFunction) function.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param middle
     * @param deviation
     * @return 70% of produced numbers will be in &lt middle - deviation, middle + deviation &gt
     */
    public DoubleArrayList normalDistribution(double middle, double deviation, int count) {
        Random random = new Random();
        return IntStream.range(0, count).mapToDouble(i -> deviation * random.nextGaussian() + middle)
                .collect(DoubleArrayList::new, DoubleArrayList::add, DoubleArrayList::addAll);
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
    public static ToDoubleFunction<Boundary> forPairDouble(DoubleBinaryOperator operator) {
        return t -> operator.applyAsDouble(t.min, t.max);
    }

    public static void bindProperty(JTextComponent text, DoubleConsumer consumer) {
        addChangeListener(text, __ -> {
            try {
                consumer.accept(Double.parseDouble(text.getText()));
            }catch (NumberFormatException e){/*ignore*/}
        });
        consumer.accept(Double.parseDouble(text.getText()));
    }
    public static void bindProperty(JTextComponent text, IntConsumer consumer) {
        addChangeListener(text, __ -> {
            try {
                consumer.accept(Integer.parseInt(text.getText()));
            }catch (NumberFormatException e){/*ignore*/}
        });
        consumer.accept(Integer.parseInt(text.getText()));
    }
    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document)e.getOldValue();
            Document d2 = (Document)e.getNewValue();
            if (d1 != null) d1.removeDocumentListener(dl);
            if (d2 != null) d2.addDocumentListener(dl);
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) d.addDocumentListener(dl);
    }
}
