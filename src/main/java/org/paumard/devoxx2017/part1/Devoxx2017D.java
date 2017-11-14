package org.paumard.devoxx2017.part1;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.util.CollectorsUtils;

import java.util.Map.Entry;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static org.paumard.devoxx2017.util.CollectorsUtils.groupingByAndCounting;
import static org.paumard.devoxx2017.util.CollectorsUtils.maxByValue;

public class Devoxx2017D {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAll();

        // # articles per year
        Entry<Integer, Long> maxNumberOFArticlesPerYear =
                articles.stream()
                        .collect(collectingAndThen(
                                groupingByAndCounting(Article::getInceptionYear), maxByValue()
                        ));
        System.out.println("maxNumberOFArticlesPerYear = " + maxNumberOFArticlesPerYear);
    }
}
