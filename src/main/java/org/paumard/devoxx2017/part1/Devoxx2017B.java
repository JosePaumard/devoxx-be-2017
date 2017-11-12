package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Devoxx2017B {

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

        // the year with the most articles
        Map.Entry<Integer, Long> maxNumberOFArticlesPerYear =
                numberOfArticlePerYear
                        .entrySet().stream()
                        .max(Comparator.comparing(entry -> entry.getValue()))
                        .get();
        System.out.println("maxNumberOFArticlesPerYear = " + maxNumberOFArticlesPerYear);

    }
}
