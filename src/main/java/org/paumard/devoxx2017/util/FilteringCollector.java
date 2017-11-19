package org.paumard.devoxx2017.util;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public class FilteringCollector<T, A, R> implements Collector<T, A, R> {

    private Predicate<T> filter;
    private Collector<T, A, R> downstream;

    public FilteringCollector(Predicate<T> filter, Collector<T, A, R> downstream) {
        this.filter = filter;
        this.downstream = downstream;
    }

    @Override
    public Supplier<A> supplier() {
        return downstream.supplier();
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return (container, element) -> {
            if (filter.test(element))
                downstream.accumulator().accept(container, element);
        };
    }

    @Override
    public BinaryOperator<A> combiner() {
        return downstream.combiner();
    }

    @Override
    public Function<A, R> finisher() {
        return downstream.finisher();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return downstream.characteristics();
    }
}
