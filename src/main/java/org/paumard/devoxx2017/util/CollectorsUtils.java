package org.paumard.devoxx2017.util;

import org.paumard.devoxx2017.model.Author;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsUtils {

    public static <T, K> Collector<T, ?, Map<K, Long>>
    groupingByAndCounting(Function<T, K> classifier) {
        return Collectors.groupingBy(
                classifier,
                Collectors.counting()
        );
    }

    public static <K, V extends Comparable<? super V>> Function<Map<K, V>, Map.Entry<K, V>>
    maxByValue() {
        return maxBy(Map.Entry.<K, V>comparingByValue());
    }

    public static <K, V extends Comparable<? super V>> Function<Map<K, V>, Map.Entry<K, V>>
    maxBy(Comparator<Map.Entry<K, V>> comparator) {
        return map -> map.entrySet().stream()
                .max(comparator)
                .get();
    }

    public static <T> Collector<T, ?, Map<T, Long>>
    groupingBySelfAndCounting() {
        return groupingByAndCounting(Function.identity());
    }

    public static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>>
    toNaturalMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <K, V> Function<Map<K, Stream<V>>, Map<K, V>>
    removeEmptyStreams() {
        return map -> map
                .entrySet().stream()
                .flatMap(
                        entry -> entry.getValue().map(e -> Map.entry(entry.getKey(), e))
                )
                .collect(toNaturalMap());
    }
}
