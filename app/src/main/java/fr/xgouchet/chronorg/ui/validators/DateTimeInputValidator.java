package fr.xgouchet.chronorg.ui.validators;

import android.support.annotation.Nullable;

/**
 * @author Xavier Gouchet
 */
public interface DateTimeInputValidator {
    boolean isValidDate(@Nullable String date);

    boolean isValidTime(@Nullable String time);

    boolean isValidTimezone(@Nullable String timezone);
}
