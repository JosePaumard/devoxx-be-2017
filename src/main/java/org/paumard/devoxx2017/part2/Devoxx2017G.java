package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;
import org.paumard.devoxx2017.util.CollectorsUtils;
import org.paumard.streams.StreamsUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Devoxx2017G {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        Function<Stream<Author>, Stream<Entry<Author, Author>>> function =
                authorStream -> StreamsUtils.crossProductOrdered(authorStream, Comparator.comparing(Author::getLastName));

        Collector<Article, ?, Entry<Entry<Author, Author>, Long>> mostSeenDuoCollector =
                Collectors.flatMapping(
                        article -> function.apply(article.getAuthors().stream()),
                        Collectors.collectingAndThen(
                                CollectorsUtils.groupingBySelfAndCounting(),
                                CollectorsUtils.maxByValue()
                        )
                );

        Map.Entry<Entry<Author, Author>, Long> mostSeenDuo =
                articles.stream()
                        .collect(
                                mostSeenDuoCollector
                        );
        System.out.println("mostSeenDuo = " + mostSeenDuo);

//        Collector<Article, ?, Entry<Entry<Author, Author>, Long>> mostSeenDuoCollector2 =
        Collector<Article, ?, Stream<Entry<Entry<Author, Author>, Long>>> mostSeenDuoCollector2 =
                Collectors.flatMapping(
                        article -> function.apply(article.getAuthors().stream()),
                        Collectors.collectingAndThen(
                                CollectorsUtils.groupingBySelfAndCounting(),
                                map -> map.entrySet().stream()
                                        .max(Entry.<Entry<Author, Author>, Long>comparingByValue())
                                        .stream()
                        )
                );

//        Map<Integer, Entry<Entry<Author, Author>, Long>> mostSeenDuoPerYear =
//        Map<Integer, Stream<Entry<Entry<Author, Author>, Long>>> mostSeenDuoPerYear =
        Map<Integer, Entry<Entry<Author, Author>, Long>> mostSeenDuoPerYear =
                articles.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Article::getInceptionYear,
                                        mostSeenDuoCollector2
                                )
                        )
                        .entrySet().stream()
                        .flatMap(
                                entry -> entry.getValue().map(e -> Map.entry(entry.getKey(), e))
                        )
                        .collect(CollectorsUtils.toNaturalMap());
        System.out.println("mostSeenDuoPerYear = " + mostSeenDuoPerYear.size());
    }
}
