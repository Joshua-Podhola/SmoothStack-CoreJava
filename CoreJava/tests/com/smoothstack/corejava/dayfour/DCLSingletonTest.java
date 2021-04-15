package com.smoothstack.corejava.dayfour;

import com.smoothstack.corejava.dayfour.DCLSingleton;

import static org.junit.jupiter.api.Assertions.*;

class DCLSingletonTest {

    @org.junit.jupiter.api.Test
    void getInstance() {
        DCLSingleton test = DCLSingleton.getInstance();
        assertNotNull(test);
    }
}