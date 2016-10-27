package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import org.joda.time.Interval;
import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.ui.fragments.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface PortalEditContract {


    interface View extends BaseView<Presenter, Portal> {

        void portalSaved();

        void portalSaveError(Throwable e);

        void invalidName(int reason);

        void invalidDelay(int reason);


        void setContent(@NonNull String name,
                        @NonNull Interval delay,
                        @Portal.Direction int direction,
                        boolean timeline,
                        @ColorInt int color);
    }

    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {

        void setView(View view);

        void savePortal(@NonNull String inputNameText);

        void setName(@NonNull String name);

        void setDirection(@Portal.Direction int direction);

        void setTimeline(boolean timeline);

        void setColor(@ColorInt int color);

        Formatter<ReadableInstant> getReadableInstantFormatter();

        void setDelayStart(@NonNull String start);

        void setDelayEnd(@NonNull String end);
    }
}
