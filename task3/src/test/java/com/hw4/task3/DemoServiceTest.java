package com.hw4.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoServiceTest {

    private final DemoService svc = new DemoService();

    @Test
    void max_firstLarger() {
        assertEquals(5, svc.max(5, 2));
    }

    @Test
    void max_secondLarger() {
        assertEquals(7, svc.max(3, 7));
    }

    @Test
    void max_equal() {
        assertEquals(4, svc.max(4, 4));
    }

    @Test
    void sign_negative() {
        assertEquals(-1, svc.sign(-9));
    }

    @Test
    void sign_positive() {
        assertEquals(1, svc.sign(2));
    }

    @Test
    void sign_zero() {
        assertEquals(0, svc.sign(0));
    }
}
