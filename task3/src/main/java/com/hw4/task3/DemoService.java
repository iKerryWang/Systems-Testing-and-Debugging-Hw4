package com.hw4.task3;

/**
 * Small helper with branches so JaCoCo reports meaningful instruction and branch coverage.
 */
public class DemoService {

    public int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    public int sign(int x) {
        if (x < 0) {
            return -1;
        }
        if (x > 0) {
            return 1;
        }
        return 0;
    }
}
