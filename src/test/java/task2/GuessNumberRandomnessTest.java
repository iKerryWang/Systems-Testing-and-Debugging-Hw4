package task2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GuessNumberRandomnessTest {

private static final int RUNS = 30000;

private final InputStream originalIn = System.in;
private final PrintStream originalOut = System.out;

@AfterEach
void restoreStreams() {
    System.setIn(originalIn);
    System.setOut(originalOut);
}

// helper: run one game and return the secret
private int runGameOnce(Random rnd, String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));

    return GuessNumberInvoker.guessingNumberGame(rnd);
}

@Test
void testRandomDistribution() {

    // generate expected sequence using same seed
    Random probe = new Random(0);
    int[] expected = new int[RUNS];

    for (int i = 0; i < RUNS; i++) {
        expected[i] = 1 + probe.nextInt(100);
    }

    // run actual game
    Random gameRng = new Random(0);
    int[] counts = new int[101];

    for (int i = 0; i < RUNS; i++) {
        int secret = expected[i];

        int returned = runGameOnce(gameRng, secret + "\n");

        // check returned value matches expected
        assertEquals(secret, returned);

        counts[returned]++;
    }

    int min = Integer.MAX_VALUE;
    int max = 0;

    for (int i = 1; i <= 100; i++) {
        min = Math.min(min, counts[i]);
        max = Math.max(max, counts[i]);
    }

    // every number should appear
    assertTrue(min > 0, "some numbers never appeared");

    // check roughly uniform (within 50%)
    assertTrue(2 * max <= 3 * min + 1,
            "distribution too uneven: min=" + min + ", max=" + max);
}

}