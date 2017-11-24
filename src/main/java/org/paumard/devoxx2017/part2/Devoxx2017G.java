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

import static java.util.Comparator.comparing;
import static org.paumard.devoxx2017.util.CollectorsUtils.removeEmptyStreams;

public class Devoxx2017G {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        Function<Stream<Author>, Stream<Entry<Author, Author>>> function =
                authorStream -> StreamsUtils.crossProductOrdered(authorStream, comparing(Author::getLastName));

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

//        Map<Integer, Entry<Entry<Author, Author>, Long>> mostSeenDuoPerYear =
//        Map<Integer, Stream<Entry<Entry<Author, Author>, Long>>> mostSeenDuoPerYear =
        Map<Integer, Entry<Entry<Author, Author>, Long>> mostSeenDuoPerYear =
                articles.stream()
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.groupingBy(
                                                Article::getInceptionYear,
                                                getMostSeenDuo(article -> article.getAuthors().stream(), comparing(Author::getLastName))
                                        ),
                                        removeEmptyStreams()
                                )
                        );

        System.out.println("mostSeenDuoPerYear = " + mostSeenDuoPerYear.size());
        System.out.println("mostSeenDuoPerYear = " + mostSeenDuoPerYear);
        mostSeenDuoPerYear.forEach(
                (year, duoEntry) -> System.out.println(year + ": " + duoEntry)
        );
    }

    public static <T, V> Collector<T, ?, Stream<Entry<Entry<V, V>, Long>>>
    getMostSeenDuo(
            Function<T, Stream<V>> streamMapper, Comparator<V> comparator
    ) {

        return Collectors.flatMapping(
                article -> StreamsUtils.crossProductOrdered(streamMapper.apply(article), comparator),
                Collectors.collectingAndThen(
                        CollectorsUtils.groupingBySelfAndCounting(),
                        map -> map.entrySet().stream()
                                .max(Entry.<Entry<V, V>, Long>comparingByValue())
                                .stream()
                )
        );
    }

}
