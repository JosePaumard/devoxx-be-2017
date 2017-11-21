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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Devoxx2017G {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        Function<Stream<Author>, Stream<Entry<Author, Author>>> function =
                authorStream -> StreamsUtils.crossProductOrdered(authorStream, Comparator.comparing(Author::getLastName));

        Map.Entry<Entry<Author, Author>, Long> mostSeenDuo =
                articles.stream()
                        .flatMap(article -> function.apply(article.getAuthors().stream()))
                        .collect(
                                Collectors.collectingAndThen(
                                        CollectorsUtils.groupingBySelfAndCounting(),
                                        CollectorsUtils.maxByValue()
                                )
                        );
        System.out.println("mostSeenDuo = " + mostSeenDuo);

    }

}
