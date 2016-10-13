package fr.xgouchet.chronorg.ui.validators;

import android.support.annotation.Nullable;

/**
 * @author Xavier Gouchet
 */
public class DateTimeRegexValidator implements DateTimeInputValidator {


    private static final String DATE_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
    private static final String TIME_REGEX = "[0-2][0-9]:[0-5][0-9](:[0-5][0-9](\\.[0-9]+)?)?";
    private static final String TIMEZONE_REGEX = "Z|([+-][0-2][0-9]:[0-5][0-9])";


    @Override public boolean isValidDate(@Nullable String date) {
        return (date != null) && date.matches(DATE_REGEX);
    }

    @Override public boolean isValidTime(@Nullable String time) {
        return (time != null) && time.matches(TIME_REGEX);
    }

    @Override public boolean isValidTimezone(@Nullable String timezone) {
        return (timezone != null) && timezone.matches(TIMEZONE_REGEX);
    }
}
