package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import fr.xgouchet.chronorg.ui.fragments.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface DateTimePickerContract {

    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {
        void setView(View view);

        void onDateSelected(@NonNull String date);

        void onTimeSelected(@NonNull String time);

        void onTimezoneSelected(@NonNull String timezone);

        void onCancel();
    }

    interface View extends BaseView<Presenter, DateTime> {

        void showDatePicker();

        void showTimePicker();

        void showTimezonePicker();

        void dismiss();
    }
}
