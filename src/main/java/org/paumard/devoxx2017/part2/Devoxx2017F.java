package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.util.CollectorsUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Collector<Article, ?, Article> optionalCollector =
                Collectors.filtering(
                        article -> article.getInceptionYear() > 1900,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        Comparator.comparing(article -> article.getAuthors().size())
                                ),
                                Optional::get
                        )
                );

        Collector<Article, ?, Stream<Article>> articleWithTheMostAuthor =
                Collectors.filtering(
                        article -> article.getInceptionYear() > 1900,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        Comparator.comparing(article -> article.getAuthors().size())
                                ),
                                Optional::stream
                        )
                );

        // Map.Entry<Integer, Stream<Article>> -> Stream<Map.Entry<Integer, Article>>

//        Stream<Map.Entry<Integer, Stream<Article>>> stream =
        Function<Entry<Integer, Stream<Article>>, Stream<Entry<Integer, Article>>> emptyStreamsRemover =
                entry -> entry.getValue().map(value -> Map.entry(entry.getKey(), value));

//        Stream<Map.Entry<Integer, Article>> stream =
        Map<Integer, Stream<Article>> streamMap = articles.stream()
                .collect(
                        Collectors.groupingBy(
                                Article::getInceptionYear,
                                articleWithTheMostAuthor
                        )
                );

        Function<Map<Integer, Stream<Article>>, Map<Integer, Article>> emptyStreamValuesRemover =
                map ->
                        map.entrySet().stream()
                                .collect(
                                        Collectors.flatMapping(emptyStreamsRemover, CollectorsUtils.toNaturalMap())
                                );

        Collector<Article, ?, Map<Integer, Article>> finalCollector =
                Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Article::getInceptionYear,
                                articleWithTheMostAuthor
                        ),
                        emptyStreamValuesRemover
                );

        Map<Integer, Article> map =
                articles.stream()
                        .collect(
                                finalCollector
                        );

        System.out.println("map.size() = " + map.size());
        map.forEach(
                (year, article) -> System.out.println(year + ": " + article.getAuthors().size() + " authors for " + article.getTitle())
        );
    }

}
