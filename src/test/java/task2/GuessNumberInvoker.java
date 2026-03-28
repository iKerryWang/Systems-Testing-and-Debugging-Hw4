package task2;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Calls {@code GuessNumber.guessingNumberGame(Random)} from the JAR. The class is in the default
 * package; {@code package task2} tests can’t reference it directly, so this uses reflection.
 */
final class GuessNumberInvoker {

    private static final Method GUESSING_GAME;

    static {
        try {
            Class<?> c = Class.forName("GuessNumber");
            GUESSING_GAME = c.getMethod("guessingNumberGame", Random.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private GuessNumberInvoker() {
    }

    static int guessingNumberGame(Random rnd) {
        try {
            return (int) GUESSING_GAME.invoke(null, rnd);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
