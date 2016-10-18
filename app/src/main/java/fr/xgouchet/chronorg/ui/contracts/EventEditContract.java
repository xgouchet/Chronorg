package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
                        @Nullable String description,
                        @NonNull ReadableInstant instant,
                        @ColorInt int color);
    }

    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {

        void setView(View view);

        void saveEvent(@NonNull String inputNameText,
                       @NonNull String inputDescText);

        void setName(@NonNull String name);

        void setDescription(@Nullable String description);

        void setInstant(@NonNull String dateTimeIso8601);

        void setColor(@ColorInt int color);
    }
}