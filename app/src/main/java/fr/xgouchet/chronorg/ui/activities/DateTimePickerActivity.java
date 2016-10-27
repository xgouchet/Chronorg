package fr.xgouchet.chronorg.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import java.util.Date;
import java.util.Locale;

import fr.xgouchet.chronorg.ui.contracts.DateTimePickerContract;
import fr.xgouchet.chronorg.ui.presenters.DateTimePickerPresenter;

import static java.util.concurrent.TimeUnit.DAYS;

/**
 * Activity to be used with startActivityForResult, to pick a date, time and timezone.
 * <p>
 * You can define some of those to only show ui for the missing information
 *
 * @author Xavier Gouchet
 */
@Trace
public class DateTimePickerActivity extends BaseActivity
        implements DateTimePickerContract.View,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        DialogInterface.OnCancelListener {

    public static final String EXTRA_DEFAULT_VALUE = "default_value";

    public static final String EXTRA_RESULT = "result";


    public static Intent createDateTimePicker(@NonNull Context context,
                                              @NonNull ReadableInstant defaultValue) {
        Intent intent = new Intent(context, DateTimePickerActivity.class);
        intent.putExtra(EXTRA_DEFAULT_VALUE, defaultValue.toString());
        return intent;
    }


    private DateTimePickerContract.Presenter presenter;

    @Nullable private DateTime defaultValue;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DEFAULT_VALUE)) {
            defaultValue = new DateTime(intent.getStringExtra(EXTRA_DEFAULT_VALUE));
        }

        DateTimePickerPresenter presenter = getActivityComponent().getDateTimePickerPresenter();
        presenter.setView(this);
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override public void setPresenter(@NonNull DateTimePickerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setError(@Nullable Throwable throwable) {
        Toast.makeText(this, throwable == null ? "‽" : throwable.getMessage(), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override public void setContent(@NonNull DateTime content) {
        Intent result = new Intent();
        result.putExtra(EXTRA_RESULT, content.toString());
        setResult(RESULT_OK, result);
        finish();
    }

    @SuppressWarnings("deprecation")
    @Override public void showDatePicker() {
        int year;
        int month;
        int dayOfMonth;

        if (defaultValue == null) {
            Date today = new Date();
            year = today.getYear() + 1900;
            month = today.getMonth();
            dayOfMonth = today.getDate();
        } else {
            year = defaultValue.getYear();
            month = defaultValue.getMonthOfYear() - 1;
            dayOfMonth = defaultValue.getDayOfMonth();
        }

        DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, dayOfMonth);
        DatePicker picker = dialog.getDatePicker();
        // Around the birth of JC, because date picker doesn't handle negative
        picker.setMinDate(DAYS.toMillis(-718_000));
        // +2739 years (around 41000 AD)
        picker.setMaxDate(DAYS.toMillis(1_000_000));
        dialog.setOnCancelListener(this);

        dialog.show();
    }

    @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = String.format(Locale.US, "%04d-%02d-%02d", year, (month + 1), dayOfMonth);
        presenter.onDateSelected(date);
    }


    @Override public void showTimePicker() {
        int hourOfDay;
        int minute;
        if (defaultValue == null) {
            hourOfDay = 12;
            minute = 0;
        } else {
            hourOfDay = defaultValue.getHourOfDay();
            minute = defaultValue.getMinuteOfHour();
        }
        TimePickerDialog dialog = new TimePickerDialog(this,
                this,
                hourOfDay,
                minute,
                true);
        dialog.setOnCancelListener(this);
        dialog.show();
    }

    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
        presenter.onTimeSelected(time);
    }


    @Override public void showTimezonePicker() {
        presenter.onTimezoneSelected("Z");
    }

    @Override public void onCancel(DialogInterface dialog) {
        presenter.onCancel();
    }

    @Override public void dismiss() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
