package fr.xgouchet.chronorg.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Locale;

import fr.xgouchet.chronorg.ui.contracts.DateTimePickerContract;
import fr.xgouchet.chronorg.ui.presenters.DateTimePickerPresenter;
import fr.xgouchet.chronorg.ui.validators.DateTimeRegexValidator;

/**
 * Activity to be used with startActivityForResult, to pick a date, time and timezone.
 * <p>
 * You can define some of those to only show ui for the missing information
 *
 * @author Xavier Gouchet
 */
@Trace
public class DateTimePickerActivity extends AppCompatActivity
        implements DateTimePickerContract.View, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener {


    /**
     * Sets the date "YYYY-MM-DD"
     */
    public static final String EXTRA_DATE = "date";

    /**
     * Sets the time "hh:mm:ss"
     */
    public static final String EXTRA_TIME = "time";

    /**
     * Sets the timezone "Z" / "+hh:mm" / "-hh:mm
     */
    public static final String EXTRA_TIMEZONE = "timezone";

    public static final String EXTRA_RESULT = "result";

    private DateTimePickerContract.Presenter presenter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO read intent
        DateTimeRegexValidator validator = new DateTimeRegexValidator();
        DateTimePickerPresenter presenter = new DateTimePickerPresenter(this, validator, null, null, null);
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

    @Override public void showDatePicker() {
        Date today = new Date();
        DatePickerDialog dialog = new DatePickerDialog(this,
                this,
                today.getYear() + 1900,
                today.getMonth(),
                today.getDate());
        dialog.setOnCancelListener(this);

        dialog.show();
    }

    @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = String.format(Locale.US, "%04d-%02d-%02d", year, (month + 1), dayOfMonth);
        presenter.onDateSelected(date);
    }


    @Override public void showTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(this,
                this,
                12,
                0,
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
