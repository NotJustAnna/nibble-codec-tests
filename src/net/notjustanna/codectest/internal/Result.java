package net.notjustanna.codectest.internal;

import net.notjustanna.codectest.codecs.Codec;

import java.util.Arrays;

import static net.notjustanna.codectest.internal.Constants.uncheck;

public class Result {
    public final double ratio;
    public final double time;

    public Result(double ratio, double time) {
        this.ratio = ratio;
        this.time = time;
    }

    public static Result measure(Codec codec, byte[] expected) {
        try {
            long start = System.nanoTime();
            var bin = codec.codec(expected);
            var actual = codec.uncodec(bin);
            long time = System.nanoTime() - start;
            if (!Arrays.equals(expected, actual)) {
                throw new RuntimeException("Codec " + codec + " failed.");
            }
            return Result.of(expected, bin, time);
        } catch (Exception e) {
            throw uncheck(e);
        }
    }

    public static Result of(byte[] expected, byte[] bin, long time) {
        return new Result(bin.length / (double) expected.length, time / 1e6);
    }

    @Override
    public String toString() {
        return String.format("%.3fms/%.4f%%", time, ratio * 100);
    }
}
