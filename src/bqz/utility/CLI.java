package bqz.utility;

import java.util.Scanner;

/**
 * The CLI class
 *
 * @author      phxgg
 * @version     1.0
 * @since       25/11/2020
 * @deprecated The CLI class is only used for Part 1 of the project.
 */
@Deprecated
public class CLI {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * @return  scanner Instance of Scanner
     */
    @Deprecated
    public static Scanner getScannerInstance() {
        return scanner;
    }
}
