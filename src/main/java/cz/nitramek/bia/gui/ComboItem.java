package cz.nitramek.bia.gui;


import cz.nitramek.bia.function.EvaluatingFunction;

import java.util.function.Function;

public class ComboItem<E> {
    public E item;
    public String str;

    public ComboItem(E item, String str) {
        this.item = item;
        this.str = str;
    }

    public static <E> ComboItem<E> createComboItem(E item, Function<E, String> strFunction) {
        return new ComboItem<>(item, strFunction.apply(item));
    }

    public static ComboItem<EvaluatingFunction> createComboItem(EvaluatingFunction
                                                                        evaluatingFunction) {
        return createComboItem(evaluatingFunction, EvaluatingFunction::getName);
    }

    public E getItem() {
        return item;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return this.getStr();
    }
}
