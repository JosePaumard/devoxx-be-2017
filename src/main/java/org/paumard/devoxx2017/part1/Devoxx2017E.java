package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Devoxx2017E {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        // # articles per year
        Map<Integer, Long> numberOfArticlePerYear =
                articles.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Article::getInceptionYear,
                                        Collectors.counting()
                                )
                        );
        System.out.println("numberOfArticlePerYear.size() = " + numberOfArticlePerYear.size());

        Map.Entry<Long, List<Map.Entry<Integer, Long>>> allMaxesNumberOFArticlesPerYear =
                numberOfArticlePerYear
                        .entrySet().stream() // Stream<Map.Entry<Integer, Long>>
                        .collect(
                                Collectors.groupingBy(
                                        entry -> entry.getValue()
                                )
                        )
                        .entrySet().stream()
                        .max(Comparator.comparing(entry -> entry.getKey()))
                        .get();
        System.out.println("allMaxesNumberOFArticlesPerYear = " + allMaxesNumberOFArticlesPerYear);

    }
}
