package com.smoothstack.corejava.dayfive;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class _LambdasTest {

    @Test
    void sortEStatic() {
        String[] arr = {"d", "e"};
        String[] ans = Arrays.stream(arr).sorted(A1_Lambdas::SortEStatic).toArray(String[]::new);
        String[] sol = {"e", "d"};
        for(int i = 0; i != 2; i++) assertEquals(ans[i], sol[i]);
    }
}