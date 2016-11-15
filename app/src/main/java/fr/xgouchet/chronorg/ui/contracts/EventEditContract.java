package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.contracts.presenters.BasePresenter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface EventEditContract {

    interface Presenter extends BasePresenter<View, Event> {

        void saveEvent(@NonNull String inputNameText);

        void setName(@NonNull String name);

        void setInstant(@NonNull String dateTimeIso8601);

        void setColor(@ColorInt int color);
    }

    interface View extends BaseView<Presenter, Event> {

        void eventSaved();

        void eventSaveError(Throwable e);
        void setContent(@NonNull String name,
                        @NonNull ReadableInstant instant,
                        @ColorInt int color);

    }
}
