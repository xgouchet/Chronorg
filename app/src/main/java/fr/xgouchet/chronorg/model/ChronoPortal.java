package fr.xgouchet.chronorg.model;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.ReadableInstant;

/**
 * @author Xavier Gouchet
 */

public class ChronoPortal {

    @NonNull
    private final Duration gap;


    public ChronoPortal(@NonNull Duration gap) {
        this.gap = gap;
    }

    public ChronoPortal(@NonNull ReadableInstant from, @NonNull ReadableInstant to) {
        this.gap = new Duration(from, to);
    }
}
