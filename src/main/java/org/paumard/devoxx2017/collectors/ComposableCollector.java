package org.paumard.devoxx2017.collectors;

import java.util.stream.Collector;
import java.util.stream.Stream;

public interface ComposableCollector<T, A, R> extends Collector<T, A, Stream<R>> {


    default <RR> ComposableCollector<T, ?, RR> thenCollect(ComposableCollector<R, ?, RR> composableFiltering) {
        return null;
    };

    default <RR> Collector<T, ?, RR> thenCollect(Collector<R, ?, RR> composableFiltering) {
        return null;
    };
}
