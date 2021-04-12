package com.smoothstack.corejava.weekone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalTest {

    @Test
    void rightDigitTest() {
        int[] input = {11, 12, 13, 1234, 364536};
        assertArrayEquals(Functional.rightDigit(input), new int[]{1, 2, 3, 4, 6});
    }

    @Test
    void doublingTest() {
        int[] input = {1, 2, 3, 4};
        assertArrayEquals(Functional.doubling(input), new int[]{2, 4, 6, 8});
    }

    @Test
    void noXTest() {
        String[] input = {"rtdxfgIOP[xDHGRHIAOxDF[hx;ilafd", "ljksxdrhgklxahjfxdgxhljxkafdg", "KLSJxNFGkxladjnxfg"};
        assertArrayEquals(Functional.noX(input), new String[]{"rtdfgIOP[DHGRHIAODF[h;ilafd", "ljksdrhgklahjfdghljkafdg", "KLSJNFGkladjnfg"});
    }
}