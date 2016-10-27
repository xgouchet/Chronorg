package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import org.joda.time.Duration;

import fr.xgouchet.chronorg.ui.fragments.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface DurationPickerContract {

    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {
        void setView(View view);

        void onCancel();

        void setDuration(@NonNull Duration duration);

        void onDurationSelected(int years, int months, int weeks, int days, int hours, int minutes, int seconds);
    }

    interface View extends BaseView<Presenter, Duration> {

        void dismissView();

        void onDurationSelected(Duration duration);
    }
}
