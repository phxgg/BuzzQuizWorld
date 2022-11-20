package bqz.model.enums;

import bqz.utility.Helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The RoundType enum
 *
 * @author      phxgg
 * @version     1.0
 * @since       12/11/2020
 */
public enum RoundType {
    CorrectAnswer,
    Bet,
    StopClock,
    QuickAnswer,
    Thermometer;

    /**
     * Caching the values list and its size
     */
    private static final List<RoundType> VALUES = Collections.unmodifiableList(Arrays.asList(values())); // or List.of(values())
    private static final int SIZE = VALUES.size();

    /**
     * This method is used to return a random round type.
     * @param size  If 2, it'll select the first two elements (used for players == 1). If 0 it'll select everything.
     * @return  RoundType   Random round type
     */
    public static RoundType getRandomType(int size) {
        if (size == 0) size = SIZE;
        return VALUES.get(Helper.getRandomInstance().nextInt(size));
    }
}
