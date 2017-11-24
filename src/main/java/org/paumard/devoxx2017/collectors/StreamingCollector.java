package org.paumard.devoxx2017.collectors;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class StreamingCollector<T> implements Collector<T, Stream.Builder<T>, Stream<T>> {
    @Override
    public Supplier<Stream.Builder<T>> supplier() {
        return null;
    }

    @Override
    public BiConsumer<Stream.Builder<T>, T> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<Stream.Builder<T>> combiner() {
        return null;
    }

    @Override
    public Function<Stream.Builder<T>, Stream<T>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
