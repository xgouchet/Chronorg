package fr.xgouchet.chronorg.data.formatters;

import android.support.annotation.NonNull;

import org.joda.time.ReadablePeriod;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * @author Xavier Gouchet
 */
public class ReadablePeriodFormatter implements Formatter<ReadablePeriod> {

    PeriodFormatter pf = new PeriodFormatterBuilder()
            .appendYears().appendSuffix("y").appendSeparatorIfFieldsBefore(", ")
            .appendMonths().appendSuffix("m").appendSeparatorIfFieldsBefore(", ")
            .appendDays().appendSuffix("d").appendSeparatorIfFieldsBefore(" / ")
            .appendHours().appendSuffix(":")
            .appendMinutes().appendSuffix("'")
            .appendSeconds().appendSuffix("\"")
            .toFormatter();

    @Override public String format(@NonNull ReadablePeriod readablePeriod) {
        return pf.print(readablePeriod);
    }
}
