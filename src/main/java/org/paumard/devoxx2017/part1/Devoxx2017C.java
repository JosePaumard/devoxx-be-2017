package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.paumard.devoxx2017.util.CollectorsUtils.groupingBySelfAndCounting;
import static org.paumard.devoxx2017.util.CollectorsUtils.maxByValue;

public class Devoxx2017C {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        Map<Author, Long> numberOfArticlesPerAuthor =
                articles.stream()
                        .flatMap(article -> article.getAuthors().stream())
                        .collect(
                                groupingBySelfAndCounting()
                        );
        System.out.println("numberOfArticlesPerAuthor.size() = " + numberOfArticlesPerAuthor.size());

        Map.Entry<Author, Long> authorWithTheMostArticles =
                articles.stream()
                        .flatMap(article -> article.getAuthors().stream())
                        .collect(Collectors.collectingAndThen(
                                groupingBySelfAndCounting(),
                                maxByValue()
                                )
                        );
        System.out.println("authorWithTheMostArticles = " + authorWithTheMostArticles);

    }

}
