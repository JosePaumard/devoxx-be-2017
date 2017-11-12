package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.stream.Collectors;

public class Devoxx2017A {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();
        System.out.println("articles read = " + articles.size());


        long count = articles.stream()
                .collect(Collectors.counting());
//                .count();
        System.out.println("count = " + count);

        int minYear =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .map(Article::getInceptionYear)
//                .min(Comparator.naturalOrder())
                        .collect(Collectors.minBy(Comparator.naturalOrder()))
                        .get();
        System.out.println("minYear = " + minYear);

        int maxYear =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .map(Article::getInceptionYear)
//                        .max(Comparator.naturalOrder())
                        .collect(Collectors.maxBy(Comparator.naturalOrder()))
                        .get();
        System.out.println("maxYear = " + maxYear);

        IntSummaryStatistics statistics =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .mapToInt(Article::getInceptionYear)
                        .summaryStatistics();
        System.out.println("statistics = " + statistics);

        IntSummaryStatistics collectedStatistics =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .collect(Collectors.summarizingInt(Article::getInceptionYear));
        System.out.println("collectedStatistics = " + collectedStatistics);

        String article19160 = articles.stream()
                .filter(article -> article.getInceptionYear() == 1960)
                .map(Article::getTitle)
                .collect(Collectors.joining(", "));
        System.out.println("article19160 = " + article19160);
    }
}
