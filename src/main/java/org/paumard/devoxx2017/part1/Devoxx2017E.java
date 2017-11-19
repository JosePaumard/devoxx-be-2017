package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.paumard.devoxx2017.util.CollectorsUtils.groupingByAndCounting;

public class Devoxx2017E {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        // # articles per year
        Map<Integer, Long> numberOfArticlePerYear =
                articles.stream()
                        .collect(
                                groupingByAndCounting(Article::getInceptionYear)
                        );
        System.out.println("numberOfArticlePerYear.size() = " + numberOfArticlePerYear.size());

        Map.Entry<Long, List<Integer>> allMaxesNumberOFArticlesPerYear =
                numberOfArticlePerYear
                        .entrySet().stream() // Stream<Map.Entry<Integer, Long>>
                        .collect(
                                Collectors.groupingBy(
                                        entry -> entry.getValue(),
                                        Collectors.mapping(
                                                entry -> entry.getKey(),
                                                Collectors.toList()
                                        )
                                )
                        )
                        .entrySet().stream()
                        .max(Comparator.comparing(entry -> entry.getKey()))
                        .get();
        System.out.println("allMaxesNumberOFArticlesPerYear = " + allMaxesNumberOFArticlesPerYear);

        Map.Entry<Long, List<Integer>> allMaxesNumberOFArticlesPerYear2 =
                numberOfArticlePerYear
                        .entrySet().stream() // Stream<Map.Entry<Integer, Long>>
                        .collect(
                                Collectors.groupingBy(
                                        entry -> entry.getValue()
                                )
                        )
                        .entrySet().stream()
                        .collect(
                                Collectors.toMap(
                                        entry -> entry.getKey(),
                                        entry -> entry.getValue().stream().map(e -> e.getKey()).collect(Collectors.toList())
                                )
                        )
                        .entrySet().stream()
                        .max(Comparator.comparing(entry -> entry.getKey()))
                        .get();
        System.out.println("allMaxesNumberOFArticlesPerYear2 bis = " + allMaxesNumberOFArticlesPerYear2);
    }
}
