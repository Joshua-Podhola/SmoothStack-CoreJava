package com.smoothstack.corejava.dayfive;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class A1_Lambdas {
    /**
     * Basic lambdas.
     * @param args Ignored
     */
    @SuppressWarnings({"SpellCheckingInspection", "ComparatorCombinators"})
    public static void main(String[] args) {
        // Some sample to work off of
        String[] lipsum = ("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque luctus dapibus velit. " +
                "Nulla et malesuada erat, quis bibendum mi. Aliquam sit amet dui ultricies, suscipit ligula ac, congue lectus. " +
                "Nam a erat nibh. Pellentesque eu maximus orci, nec tempor orci. Etiam egestas dictum vehicula. Sed et sollicitudin " +
                "tellus. Maecenas lacinia purus at neque condimentum, id commodo sapien interdum. Nam purus lectus, lacinia non " +
                "vulputate eu, aliquet ut ipsum. Vivamus volutpat velit non elit condimentum ultricies. Maecenas lobortis, " +
                "metus eget luctus semper, felis lorem scelerisque enim, sit amet pellentesque ipsum ipsum sit amet nisl. In " +
                "sollicitudin placerat urna in porta. Suspendisse.").split("[ \\t\\n\\x0B\\f\\r]"); // \s doesn't work for some reason?

        //My convention is "lhs" for "left-hand side" and "rhs" for "right-hand side"
        System.out.println("Sorted by length:");
        Stream<String> sortedLength = Arrays.stream(lipsum).sorted((lhs, rhs) -> lhs.length() - rhs.length());
        sortedLength.forEach(System.out::println);

        System.out.println("Sorted by length reversed:");
        Stream<String> sortedLengthReverse = Arrays.stream(lipsum).sorted((lhs, rhs) -> rhs.length() - lhs.length());
        sortedLengthReverse.forEach(System.out::println);

        System.out.println("Sorted alphabetically (first character only)");
        Stream<String> sortedAlph = Arrays.stream(lipsum).sorted((lhs, rhs) -> lhs.charAt(0) - rhs.charAt(0));
        sortedAlph.forEach(System.out::println);

        System.out.println("Sorted by \"e\" first:");
        Stream<String> sortedPrioE = Arrays.stream(lipsum).sorted((lhs, rhs) -> rhs.contains("e") ? 1 : lhs.contains("e") ? -1 : 0);
        sortedPrioE.forEach(System.out::println);

        System.out.println("Sorted by \"e\" first (static helper):");
        Stream<String> sortedPrioESM = Arrays.stream(lipsum).sorted(A1_Lambdas::SortEStatic);
        sortedPrioESM.forEach(System.out::println);
    }

    /**
     * Static helper method to sort by "e" priority
     * @param lhs First lambda argument
     * @param rhs Second lambda argument
     * @return 1 if rhs contains "e", else 0
     */
    public static int SortEStatic(String lhs, String rhs) {
        //I have plenty of room to do a less silly implementation, but I wanted the output to be exactly the same.
        return rhs.contains("e") ? 1 : lhs.contains("e") ? -1 : 0;
    }
}
