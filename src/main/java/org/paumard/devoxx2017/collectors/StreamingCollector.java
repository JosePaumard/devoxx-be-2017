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
        return Stream::builder;
    }

    @Override
    public BiConsumer<Stream.Builder<T>, T> accumulator() {
        return Stream.Builder::add;
    }

    @Override
    public BinaryOperator<Stream.Builder<T>> combiner() {
        return (builder1, builder2) -> {
            builder2.build().forEach(builder1::add);
            return builder1;
        };
    }

    @Override
    public Function<Stream.Builder<T>, Stream<T>> finisher() {
        return Stream.Builder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.CONCURRENT);
    }
}
