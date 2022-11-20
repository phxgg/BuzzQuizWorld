package bqz.utility;

import javafx.application.Platform;

import java.io.File;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * The Helper class keeps instances and has methods that we will use in any class (e.g. Scanner, Random, returning a random integer)
 *
 * @author      phxgg
 * @version     1.0
 * @since       13/11/2020
 */
public class Helper {
    private static final Random random = new Random();

    /**
     * This method is used to return a random integer between a min and a max number.
     * @param min   The minimum integer to be returned
     * @param max   The maximum integer to be returned
     * @return  int Random integer between min and max
     */
    public static int getRandomInt(int min, int max) {
        return random.ints(min, max).findFirst().getAsInt();
    }

    /**
     * This method is used to return a random string.
     * @param length    Number of characters
     * @return  String  Randomly generated string
     */
    public static String getRandomString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /**
     * @return  Random  Instance of Random
     */
    public static Random getRandomInstance() {
        return random;
    }

    // Credits https://news.kynosarges.org/2014/05/01/simulating-platform-runandwait/
    /**
     * Runs the specified {@link Runnable} on the
     * JavaFX application thread and waits for completion.
     *
     * @param action the {@link Runnable} to run
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public static void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            // ignore exception
        }
    }

    /**
     * This method is used to return the executable path.
     * @return  String  Executable path.
     */
    public static String jarPath() {
        String jarPath = null;
        try {
            jarPath = new File(Helper.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jarPath.replace("Buzz.jar", "");
    }
}
