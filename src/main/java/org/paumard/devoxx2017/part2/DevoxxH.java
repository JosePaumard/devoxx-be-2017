package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

    }
}
