package task2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GuessNumberIOTest {

private final InputStream originalIn = System.in;
private final PrintStream originalOut = System.out;

@AfterEach
void restoreStreams() {
    System.setIn(originalIn);
    System.setOut(originalOut);
}

// helper: count how many times a substring appears
private static int countSubstrings(String text, String target) {
    int count = 0;
    int index = 0;
    while ((index = text.indexOf(target, index)) >= 0) {
        count++;
        index += target.length();
    }
    return count;
}

// helper: run game and capture output
private String runGame(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));

    GuessNumberInvoker.guessingNumberGame(new Random(0));

    return out.toString(StandardCharsets.UTF_8);
}

// helper: run game and return the secret number
private int runGameReturnSecret(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));

    return GuessNumberInvoker.guessingNumberGame(new Random(0));
}

@Test
void testGameEndsAfterFiveTries() {
    String output = runGame("1\n2\n3\n4\n5\n");
    assertTrue(output.contains("You have exhausted 5 trials."),
            "game should stop after 5 guesses");
}

@Test
void testCorrectGuess() {
    String output = runGame("61\n");
    assertTrue(output.contains("Congratulations"),
            "should print win message");
}

@Test
void testReturnValueMatchesSecret() {
    assertEquals(61, runGameReturnSecret("61\n"));
}

@Test
void testOpeningBanner() {
    String output = runGame("61\n");
    assertTrue(output.contains("A number is chosen between 1 to 100. Guess the number within 5 trials."));
}

@Test
void testGuessPromptShowsFiveTimes() {
    String output = runGame("1\n2\n3\n4\n5\n");
    assertEquals(5, countSubstrings(output, "Guess the number:"));
}

@Test
void testLosingShowsSecret() {
    String output = runGame("1\n2\n3\n4\n5\n");
    assertTrue(output.contains("The number was 61"));
}

@Test
void testTooLowHint() {
    String output = runGame("10\n20\n30\n40\n50\n");
    assertTrue(output.contains("greater than 10"));
}

@Test
void testTooHighHint() {
    String output = runGame("80\n70\n60\n50\n40\n");
    assertTrue(output.contains("less than 80"));
}

@Test
void testFifthGuessStillShowsHintWhenTooLow() {
    String output = runGame("70\n65\n63\n62\n60\n");
    assertTrue(output.contains("greater than 60"));
}

/**
 * this one breaks the program
 * if you type something that's not a number (like "abc"),
 * it just crashes instead of asking again
 *
 * a possible explaintion is because it's using Scanner.nextInt() with no 
 * try/catch block, so it crashes instead of asking again
 */
@Test
void testInvalidInputCrash() {
    System.setIn(new ByteArrayInputStream("abc\n".getBytes(StandardCharsets.UTF_8)));
    System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));

    assertThrows(Exception.class, () ->
            GuessNumberInvoker.guessingNumberGame(new Random(0)));
}

/**
 * this also doesn't behave as expected
 * it lets you enter numberslike 0, -1, or 1000 even though the range is 1–100
 *
 * it still gives hints instead of rejecting the input,
 * so there's probably no range checking at all
 */
@Test
void testOutOfRangeInput() {
    String output = runGame("0\n-1\n1000\n50\n60\n");

    assertTrue(output.contains("greater than -1") || output.contains("less than 1000"));
}

@Test
void testSameSeedGivesSameSecret() {
    assertEquals(61, runGameReturnSecret("61\n"));
    assertEquals(61, runGameReturnSecret("61\n"));
}

}