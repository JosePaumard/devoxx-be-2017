package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.Set;

public class Devoxx2017A {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();
        System.out.println("articles read = " + articles.size());


        long count = articles.stream()
                .count();
        System.out.println("count = " + count);

        int minYear =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .map(Article::getInceptionYear)
                        .min(Comparator.naturalOrder())
                        .get();
        System.out.println("minYear = " + minYear);

        int maxYear =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .map(Article::getInceptionYear)
                        .max(Comparator.naturalOrder())
                        .get();
        System.out.println("maxYear = " + maxYear);
    }
}
