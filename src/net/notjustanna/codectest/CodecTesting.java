package net.notjustanna.codectest;

import net.notjustanna.codectest.codecs.DeflaterCodec;
import net.notjustanna.codectest.codecs.HafuCodec;
import net.notjustanna.codectest.codecs.Codec;
import net.notjustanna.codectest.internal.Constants;
import net.notjustanna.codectest.internal.GroupedResult;
import net.notjustanna.codectest.internal.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.UnaryOperator.identity;
import static net.notjustanna.codectest.internal.Constants.randomScreen;
import static net.notjustanna.codectest.internal.Constants.screenOf;

public class CodecTesting implements Constants {
    public static void main(String[] args) {
        System.out.println("Heating up JVM...");
        heatUp();
        System.out.println("Starting...");
        doBenchmark();
    }

    private static void doBenchmark() {
        System.out.println("\n");
        for (Codec codec : codecs) {
            runForCodec(codec);
        }
    }

    private static void runForCodec(Codec codec) {
        System.gc();
        System.out.println("| " + codec + " |");
        System.out.print("Monocolor screen:\n\t");
        System.out.println(
            GroupedResult.of(heavyColorStream().map(color -> Result.measure(codec, screenOf(w, h, color))))
        );
        System.gc();
        System.out.print("Random screen:\n\t");
        System.out.println(
            GroupedResult.of(IntStream.range(0, 2048 * 32).mapToObj(__ -> Result.measure(codec, randomScreen(w, h))))
        );
        System.out.println();
    }

    private static Stream<Color> heavyColorStream() {
        return IntStream.range(0, 128 * 32).mapToObj(__ -> Arrays.stream(colors)).flatMap(identity());
    }

    private static final List<Codec> codecs = List.of(
        HafuCodec.INSTANCE,
        new DeflaterCodec(HafuCodec.INSTANCE, 1),
        new DeflaterCodec(HafuCodec.INSTANCE, 9)
    );
    private static final Color[] colors = Color.values();

    private static int w = Constants.computerWidth;
    private static int h = Constants.computerHeight;

    private static void heatUp() {
        long time = -System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            for (Color value : colors) {
                var expected = screenOf(w, h, value);
                for (Codec codec : codecs) {
                    Result.measure(codec, expected);
                }
            }
            for (Codec codec : codecs) {
                var expected = randomScreen(w, h);
                Result.measure(codec, expected);
            }
        }
        time += System.currentTimeMillis();
        System.gc();
        System.out.println("Heating up took " + time + "ms");
    }
}
