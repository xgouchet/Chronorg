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

    @NonNull private final DateTimePickerContract.View view;
    @Nullable private String date, time, timezone;

    public DateTimePickerPresenter(@NonNull DateTimePickerContract.View view,
                                   @Nullable String date,
                                   @Nullable String time,
                                   @Nullable String timezone) {

        this.view = view;

        // TODO check values match pattern
        this.date = date;
        this.time = time;
        this.timezone = timezone;

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
        // TODO check date → error
        this.date = date;
        handleNextStep();
    }

    @Override public void onTimeSelected(@NonNull String time) {
        // TODO check time → error
        this.time = time;
        handleNextStep();
    }

    @Override public void onTimezoneSelected(@NonNull String timezone) {
        // TODO check time → error
        this.timezone = timezone;
        handleNextStep();
    }

    private void handleNextStep() {
        if (date == null) { // TODO check date format
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
