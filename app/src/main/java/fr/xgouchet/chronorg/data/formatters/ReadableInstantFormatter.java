package fr.xgouchet.chronorg.data.formatters;

import android.support.annotation.NonNull;

import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author Xavier Gouchet
 */
public class ReadableInstantFormatter implements Formatter<ReadableInstant> {

    final DateTimeFormatter dtf = DateTimeFormat.forStyle("MS").withZoneUTC();

    @Override public String format(@NonNull ReadableInstant readableInstant) {
        return dtf.print(readableInstant);
    }
}
