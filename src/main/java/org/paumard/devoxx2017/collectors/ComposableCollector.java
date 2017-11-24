package org.paumard.devoxx2017.collectors;

import java.util.Collections;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ComposableCollector<T, A, R> extends Collector<T, A, Stream<R>> {


    default <RR> ComposableCollector<T, A, RR> thenCollect(ComposableCollector<R, ?, RR> downstream) {
        return new ComposableCollector<>() {
            @Override
            public Supplier<A> supplier() {
                return ComposableCollector.this.supplier();
            }

            @Override
            public BiConsumer<A, T> accumulator() {
                return ComposableCollector.this.accumulator();
            }

            @Override
            public BinaryOperator<A> combiner() {
                return ComposableCollector.this.combiner();
            }

            @Override
            public Function<A, Stream<RR>> finisher() {
                return container -> ComposableCollector.this.finisher().apply(container).collect(downstream);
            }

            @Override
            public Set<Characteristics> characteristics() {
                Set<Characteristics> characteristics =
                        ComposableCollector.this.characteristics()
                                .stream()
                                .filter(downstream.characteristics()::contains)
                                .collect(Collectors.toSet());
                characteristics.remove(Characteristics.IDENTITY_FINISH);
                return Collections.unmodifiableSet(characteristics);
            }
        };
    };

    default <RR> Collector<T, A, RR> thenCollect(Collector<R, ?, RR> downstream) {
        return new Collector<T, A, RR>() {
            @Override
            public Supplier<A> supplier() {
                return ComposableCollector.this.supplier();
            }

            @Override
            public BiConsumer<A, T> accumulator() {
                return ComposableCollector.this.accumulator();
            }

            @Override
            public BinaryOperator<A> combiner() {
                return ComposableCollector.this.combiner();
            }

            @Override
            public Function<A, RR> finisher() {
                return container -> ComposableCollector.this.finisher().apply(container).collect(downstream);
            }

            @Override
            public Set<Characteristics> characteristics() {
                Set<Characteristics> characteristics =
                        ComposableCollector.this.characteristics()
                                .stream()
                                .filter(downstream.characteristics()::contains)
                                .collect(Collectors.toSet());
                characteristics.remove(Characteristics.IDENTITY_FINISH);
                return Collections.unmodifiableSet(characteristics);
            }
        };
    }

    static <T, U> ComposableCollector<T, ?, U> mapping(Function<T, U> mapper) {
        return new ComposableCollector<T, Stream.Builder<U>, U>() {
            @Override
            public Supplier<Stream.Builder<U>> supplier() {
                return Stream::builder;
            }

            @Override
            public BiConsumer<Stream.Builder<U>, T> accumulator() {
                return (builder, t) -> builder.add(mapper.apply(t));
            }

            @Override
            public BinaryOperator<Stream.Builder<U>> combiner() {
                return (builder1, builder2) -> {
                    builder2.build().forEach(builder1::add);
                    return builder1;
                };
            }

            @Override
            public Function<Stream.Builder<U>, Stream<U>> finisher() {
                return Stream.Builder::build;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.CONCURRENT);
            }
        };
    }

    static <T> ComposableCollector<T, ?, T> filtering(Predicate<T> filter) {
        return new ComposableCollector<T, Stream.Builder<T>, T>() {
            @Override
            public Supplier<Stream.Builder<T>> supplier() {
                return Stream::builder;
            }

            @Override
            public BiConsumer<Stream.Builder<T>, T> accumulator() {
                return (builder, t) -> {
                    if (filter.test(t)) {
                        builder.add(t);
                    }
                };
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
        };
    }
}
