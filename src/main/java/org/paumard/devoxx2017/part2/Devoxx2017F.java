package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Devoxx2017F {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        // articles with the most authors
        Article articleWithTheMostAuthors =
                articles.stream()
                        .filter(article -> article.getInceptionYear() > 1900)
                        .max(
                                Comparator.comparing(article -> article.getAuthors().size())
                        )
                        .get();
        System.out.println("articleWithTheMostAuthors = " +
                articleWithTheMostAuthors.getTitle() + " - " +
                articleWithTheMostAuthors.getAuthors().size());

        // articles with the most authors for each year
        Collector<Article, ?, Article> collector =
                Collectors.filtering(
                        article -> article.getInceptionYear() > 1900,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        Comparator.comparing(article -> article.getAuthors().size())
                                ),
                                Optional::get
                        )
                );

        articles.stream()
                .collect(
                        Collectors.groupingBy(
                                Article::getInceptionYear,
                                collector
                        )
                );
    }
}
