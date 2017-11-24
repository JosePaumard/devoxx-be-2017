package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DevoxxH {

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

//        Map<Author, List<Entry<Article, Author>>> articlesPerAuthor =
        Function<Map<Article, List<Author>>, Map<Author, List<Article>>> function = invertMultiMap();

        Map<Author, List<Article>> articlesPerAuthor = function.apply(authorsPerArticles);
        System.out.println("articlesPerAuthor = " + articlesPerAuthor.size());
    }

    private static Function<Map<Article, List<Author>>, Map<Author, List<Article>>>
    invertMultiMap() {
        return map -> map.entrySet().stream()
                .collect(
                        Collectors.flatMapping(
                                entry -> entry.getValue().stream().map(author -> Map.entry(entry.getKey(), author)),
                                Collectors.groupingBy(
                                        Entry::getValue,
                                        Collectors.mapping(
                                                Entry::getKey,
                                                Collectors.toList()
                                        )

                                )
                        )
                );
    }
}
