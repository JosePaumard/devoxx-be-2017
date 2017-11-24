package org.paumard.devoxx2017.part2;

import org.paumard.devoxx2017.model.Article;
import org.paumard.devoxx2017.model.Author;
import org.paumard.devoxx2017.util.CollectorsUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Devoxx2017I {

    public static void main(String[] args) {

        List<String> strings = Arrays.asList("one", "two", "three", "four", "five", "six", "seven");

        List<Integer> lengths1 =
        strings.stream()
                .map(String::length)
                .filter(length -> length > 3)
                .collect(Collectors.toList());
        System.out.println("lengths1 = " + lengths1);

        List<Integer> lengths2 =
        strings.stream()
                .collect(
                        Collectors.mapping(
                                String::length,
                                Collectors.filtering(
                                        length -> length > 3,
                                        Collectors.toList()
                                )
                        )
                );
        System.out.println("lengths2 = " + lengths2);
    }
}
