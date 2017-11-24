package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;
import org.paumard.devoxx2017.util.CollectorsUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Devoxx2017H {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        // Inverting a map

        Map<Article, List<Author>> authorsPerArticles =
                articles.stream()
                        .collect(
                                Collectors.toMap(
                                        article -> article,
                                        article -> article.getAuthors()
                                )
                        );
        System.out.println("authorsPerArticles = " + authorsPerArticles.size());

        // Map<Author, List<Article>>

//        Map<Author, List<Entry<Article, V>>> articlesPerAuthor =
        Function<Map<Article, List<Author>>, Map<Author, List<Article>>> invertMultiMap = CollectorsUtils.invertMultiMap();

        Map<Author, List<Article>> articlesPerAuthor = invertMultiMap.apply(authorsPerArticles);
        System.out.println("articlesPerAuthor = " + articlesPerAuthor.size());
    }
}
