package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Devoxx2017D {

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
        System.out.println("numberOfArticlePerYear = " + numberOfArticlePerYear);

        Function<Map<Integer, Long>, Entry<Integer, Long>> maxByValue =
                map -> map.entrySet().stream()
                        .max(Entry.comparingByValue())
                        .get();

        Entry<Integer, Long> maxNumberOFArticlesPerYear =
                maxByValue.apply(numberOfArticlePerYear);
        System.out.println("maxNumberOFArticlesPerYear = " + maxNumberOFArticlesPerYear);
    }
}
