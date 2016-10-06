package fr.xgouchet.chronorg.data.models;

import android.support.annotation.NonNull;

import org.joda.time.ReadableInstant;

/**
 * @author Xavier Gouchet
 */
public class Jump {
    @NonNull final ReadableInstant from;
    @NonNull final ReadableInstant to;

    Jump(@NonNull ReadableInstant from, @NonNull ReadableInstant to) {
        this.from = from;
        this.to = to;
    }
}
