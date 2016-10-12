package fr.xgouchet.chronorg.ui.validators;

import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.ui.presenters.DateTimePickerPresenter;

/**
 * @author Xavier Gouchet
 */
public class DateTimeRegexValidator  implements DateTimePickerPresenter.DateTimeInputValidator{
    @Override public boolean isValidDate(@Nullable String date) {
        return false;
    }

    @Override public boolean isValidTime(@Nullable String time) {
        return false;
    }

    @Override public boolean isValidTimezone(@Nullable String timezone) {
        return false;
    }
}
