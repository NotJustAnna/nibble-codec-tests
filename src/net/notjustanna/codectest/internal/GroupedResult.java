package net.notjustanna.codectest.internal;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Stream;

public class GroupedResult {
    public final long count;
    public final Result average;
    public final Result best;
    public final Result worst;

    public GroupedResult(DoubleSummaryStatistics ratio, DoubleSummaryStatistics time) {
        this.count = ratio.getCount();
        average = new Result(ratio.getAverage(), time.getAverage());
        best = new Result(ratio.getMin(), time.getMin());
        worst = new Result(ratio.getMax(), time.getMax());
    }

    public static GroupedResult of(Iterable<Result> results) {
        var ratio = new DoubleSummaryStatistics();
        var time = new DoubleSummaryStatistics();
        for (Result result : results) {
            ratio.accept(result.ratio);
            time.accept(result.time);
        }
        return new GroupedResult(ratio, time);
    }

    public static GroupedResult of(Stream<Result> results) {
        var ratio = new DoubleSummaryStatistics();
        var time = new DoubleSummaryStatistics();
        results.forEach(result -> {
            ratio.accept(result.ratio);
            time.accept(result.time);
        });
        return new GroupedResult(ratio, time);
    }

    @Override
    public String toString() {
        return "of " + count + " runs: average=" + average + ", best=" + best + ", worst=" + worst;
    }
}
