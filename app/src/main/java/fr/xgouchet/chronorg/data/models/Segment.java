package fr.xgouchet.chronorg.data.models;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePeriod;

/**
 * @author Xavier Gouchet
 */
public class Segment implements Comparable<Segment> {

    @NonNull private final String legendFrom;
    @NonNull private final String legendTo;
    @NonNull private final ReadableInstant from;
    @NonNull private final ReadableInstant to;
    @ColorInt private final int color;

    public Segment(
            @NonNull ReadableInstant instant,
            @NonNull String legend,
            int color) {
        this.legendFrom = legend;
        this.legendTo = legend;
        this.from = instant;
        this.to = instant;
        this.color = color;
    }

    public Segment(
            @NonNull ReadableInstant from,
            @NonNull String legendFrom,
            @NonNull ReadableInstant to,
            @NonNull String legendTo,
            int color) {
        this.legendFrom = legendFrom;
        this.legendTo = legendTo;
        this.from = from;
        this.to = to;
        this.color = color;
    }

    @NonNull public String getLegendFrom() {
        return legendFrom;
    }

    @NonNull public String getLegendTo() {
        return legendTo;
    }

    @NonNull public ReadableInstant getFrom() {
        return from;
    }

    @NonNull public ReadableInstant getTo() {
        return to;
    }

    public int getColor() {
        return color;
    }

    public boolean contains(@NonNull ReadableInstant instant) {
        return from.isBefore(instant) && to.isAfter(instant);
    }

    @NonNull public ReadablePeriod period() {
        return new Period(from, to);
    }

    @NonNull public Duration duration() {
        return new Duration(from, to);
    }

    @Override public int compareTo(@NonNull Segment other) {

        return getFrom().compareTo(other.getFrom());
    }
}

