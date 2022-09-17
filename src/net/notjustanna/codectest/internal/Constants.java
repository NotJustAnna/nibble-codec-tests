package net.notjustanna.codectest.internal;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public interface Constants {
    int computerWidth = 51 * 6 + 4;
    int computerHeight = 19 * 9 + 4;
    int turtleWidth = 39 * 6 + 4;
    int turtleHeight = 13 * 9 + 4;
    int pocketComputerWidth = 26 * 6 + 4;
    int pocketComputerHeight = 20 * 9 + 4;

    enum Color {
        white, orange, magenta, lightBlue, yellow, lime, pink, gray,
        lightGray, cyan, purple, blue, green, brown, red, black
    }

    static byte[] screenOf(int w, int h, Color value) {
        byte[] buffer = new byte[w * h];
        Arrays.fill(buffer, (byte) value.ordinal());
        return buffer;
    }

    static byte[] randomScreen(int w, int h) {
        byte[] buffer = new byte[w * h];
        Random r = ThreadLocalRandom.current();
        int length = Color.values().length;
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) r.nextInt(length);
        }
        return buffer;
    }

    @SuppressWarnings("unchecked")
    static <T extends Throwable> Error uncheck(Throwable error) throws T {
        throw (T) error;
    }
}
