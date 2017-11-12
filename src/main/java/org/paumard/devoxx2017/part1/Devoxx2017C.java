package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Devoxx2017C {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        Map<Author, Long> numberOfArticlesPerAuthor =
                articles.stream()
                        .flatMap(article -> article.getAuthors().stream())
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                )
                        );
        System.out.println("numberOfArticlesPerAuthor.size() = " + numberOfArticlesPerAuthor.size());

        Map.Entry<Author, Long> authorWithTheMostArticles =
                numberOfArticlesPerAuthor
                        .entrySet().stream()
                        .max(Comparator.comparing(entry -> entry.getValue()))
                        .get();
        System.out.println("authorWithTheMostArticles = " + authorWithTheMostArticles);

    }
}
