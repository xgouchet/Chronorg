package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;

import fr.xgouchet.chronorg.ui.contracts.DateTimePickerContract;

/**
 * @author Xavier Gouchet
 */
@Trace
public class DateTimePickerPresenter implements DateTimePickerContract.Presenter {

    public static interface DateTimeInputValidator {
        boolean isValidDate(@Nullable String date);

        boolean isValidTime(@Nullable String time);

        boolean isValidTimezone(@Nullable String timezone);
    }

//    private static final String DATE_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
//    private static final String TIME_REGEX = "[0-2][0-9]:[0-5][0-9](:[0-5][0-9](\\.[0-9]+)?)?";
//    private static final String TIMEZONE_REGEX = "Z|([+-][0-2][0-9]:[0-5][0-9])";

    @NonNull private final DateTimePickerContract.View view;
    @Nullable private String date, time, timezone;
    @NonNull private final DateTimeInputValidator validator;

    public DateTimePickerPresenter(@NonNull DateTimePickerContract.View view,
                                   @NonNull DateTimeInputValidator validator,
                                   @Nullable String date,
                                   @Nullable String time,
                                   @Nullable String timezone) {

        this.view = view;
        this.validator = validator;

        this.date = validator.isValidDate(date) ? date : null;
        this.time = validator.isValidTime(time) ? time : null;
        this.timezone = validator.isValidTimezone(timezone) ? timezone : null;

        this.view.setPresenter(this);
    }

    @Override public void subscribe() {
        handleNextStep();
    }

    @Override public void unsubscribe() {
    }

    @Override public void load(boolean force) {
    }

    @Override public void onDateSelected(@NonNull String date) {
        if (!validator.isValidDate(date)) {
            view.setError(new IllegalArgumentException("Invalid date format \"" + date + "\""));
            return;
        }

        this.date = date;
        handleNextStep();
    }

    @Override public void onTimeSelected(@NonNull String time) {
        if (!validator.isValidTime(time)) {
            view.setError(new IllegalArgumentException("Invalid time format \"" + time + "\""));
            return;
        }
        this.time = time;
        handleNextStep();
    }

    @Override public void onTimezoneSelected(@NonNull String timezone) {
        if (!validator.isValidTimezone(timezone)) {
            view.setError(new IllegalArgumentException("Invalid time format \"" + time + "\""));
            return;
        }
        this.timezone = timezone;
        handleNextStep();
    }

    private void handleNextStep() {
        if (date == null) {
            view.showDatePicker();
        } else if (time == null) {
            view.showTimePicker();
        } else if (timezone == null) {
            view.showTimezonePicker();
        } else {
            String dateIso8601 = date + "T" + time + timezone;
            DateTime dateTime = new DateTime(dateIso8601);
            view.setContent(dateTime);
        }

    }


    @Override public void onCancel() {
        view.dismiss();
    }
}
