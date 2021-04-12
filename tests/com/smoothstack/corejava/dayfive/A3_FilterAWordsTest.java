package com.smoothstack.corejava.dayfive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class A3_FilterAWordsTest {

    @Test
    void filterAWordsTest() {
        String[] set1 = {"a", "aa", "aaa", "aaaa", "b", "bb", "bbb", "bbbb"};
        String[] set2 = {"Aaa", "aAa", "aaA"};
        assertArrayEquals(A3_FilterAWords.FilterAWords(set1), new String[]{"aaa"});
        assertArrayEquals(A3_FilterAWords.FilterAWords(set2), new String[]{"aAa", "aaA"});
    }
}