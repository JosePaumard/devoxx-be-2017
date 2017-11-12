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
    }
}
