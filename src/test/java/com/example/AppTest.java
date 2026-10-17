package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testAdd() {
        assertEquals(99, App.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(3, App.subtract(5, 2));
    }
}
