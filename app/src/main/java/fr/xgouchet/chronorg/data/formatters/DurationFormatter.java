package fr.xgouchet.chronorg.data.formatters;

import android.support.annotation.NonNull;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * @author Xavier Gouchet
 */
public class DurationFormatter implements Formatter<Duration> {

    PeriodFormatter pf = new PeriodFormatterBuilder()
            .appendYears().appendSuffix("y").appendSeparatorIfFieldsBefore(", ")
            .appendMonths().appendSuffix("m").appendSeparatorIfFieldsBefore(", ")
            .appendDays().appendSuffix("d").appendSeparatorIfFieldsBefore(" / ")
            .appendHours().appendSuffix(":")
            .appendMinutes().appendSuffix("'")
            .appendSeconds().appendSuffix("\"")
            .toFormatter();

    @Override public String format(@NonNull Duration duration) {
        return pf.print(duration.toPeriod());
    }
}
