package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class Devoxx2017D {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        // # articles per year
        Entry<Integer, Long> maxNumberOFArticlesPerYear =
                articles.stream()
                        .collect(collectingAndThen(
                                groupingByAndCounting(Article::getInceptionYear), maxByValue()
                        ));
        System.out.println("maxNumberOFArticlesPerYear = " + maxNumberOFArticlesPerYear);
    }

    public static <T, K> Collector<T, ?, Map<K, Long>>
    groupingByAndCounting(Function<T, K> classifier) {
        return Collectors.groupingBy(
                classifier,
                Collectors.counting()
        );
    }

    public static <K, V extends Comparable<? super V>> Function<Map<K, V>, Entry<K, V>>
    maxByValue() {
        return maxBy(Entry.<K, V>comparingByValue());
    }

    private static <K, V extends Comparable<? super V>> Function<Map<K, V>, Entry<K, V>>
    maxBy(Comparator<Entry<K, V>> comparator) {
        return map -> map.entrySet().stream()
                .max(comparator)
                .get();
    }
}
