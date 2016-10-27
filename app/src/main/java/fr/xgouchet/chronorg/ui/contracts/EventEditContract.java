package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.fragments.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface EventEditContract {

    public static final int EMPTY = 1;

    interface View extends BaseView<Presenter, Event> {

        void eventSaved();

        void eventSaveError(Throwable e);

        void invalidName(int reason);

        void invalidInstant(int reason);


        void setContent(@NonNull String name,
                        @NonNull ReadableInstant instant,
                        @ColorInt int color);
    }

    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {

        void setView(View view);

        void saveEvent(@NonNull String inputNameText);

        void setName(@NonNull String name);

        void setInstant(@NonNull String dateTimeIso8601);

        void setColor(@ColorInt int color);
    }
}
