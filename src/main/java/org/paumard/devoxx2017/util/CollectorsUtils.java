package org.paumard.devoxx2017.util;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
}
