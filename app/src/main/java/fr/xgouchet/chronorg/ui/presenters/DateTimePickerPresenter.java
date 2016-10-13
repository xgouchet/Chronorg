package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;

import fr.xgouchet.chronorg.ui.contracts.DateTimePickerContract;
import fr.xgouchet.chronorg.ui.validators.DateTimeInputValidator;

/**
 * @author Xavier Gouchet
 */
@Trace
public class DateTimePickerPresenter implements DateTimePickerContract.Presenter {


    @Nullable private DateTimePickerContract.View view;
    @Nullable private String date, time, timezone;
    @NonNull private final DateTimeInputValidator validator;

    public DateTimePickerPresenter(@NonNull DateTimeInputValidator validator) {
        this(validator, null, null, null);
    }

    public DateTimePickerPresenter(@NonNull DateTimeInputValidator validator,
                                   @Nullable String date,
                                   @Nullable String time,
                                   @Nullable String timezone) {

        this.validator = validator;

        this.date = validator.isValidDate(date) ? date : null;
        this.time = validator.isValidTime(time) ? time : null;
        this.timezone = validator.isValidTimezone(timezone) ? timezone : null;
    }

    @Override public void setView(@NonNull DateTimePickerContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        handleNextStep();
    }

    @Override public void unsubscribe() {
    }

    @Override public void load(boolean force) {
    }

    @Override public void onDateSelected(@NonNull String date) {
        if (view == null) return;
        if (!validator.isValidDate(date)) {
            view.setError(new IllegalArgumentException("Invalid date format \"" + date + "\""));
            return;
        }

        this.date = date;
        handleNextStep();
    }

    @Override public void onTimeSelected(@NonNull String time) {
        if (view == null) return;
        if (!validator.isValidTime(time)) {
            view.setError(new IllegalArgumentException("Invalid time format \"" + time + "\""));
            return;
        }
        this.time = time;
        handleNextStep();
    }

    @Override public void onTimezoneSelected(@NonNull String timezone) {
        if (view == null) return;
        if (!validator.isValidTimezone(timezone)) {
            view.setError(new IllegalArgumentException("Invalid time format \"" + time + "\""));
            return;
        }
        this.timezone = timezone;
        handleNextStep();
    }

    private void handleNextStep() {
        if (view == null) return;
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
        if (view == null) return;
        view.dismiss();
    }
}
