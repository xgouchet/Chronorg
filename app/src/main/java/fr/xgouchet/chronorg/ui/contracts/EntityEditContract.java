package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.contracts.presenters.BasePresenter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface EntityEditContract {

    interface View extends BaseView<Presenter, Entity> {

        void entitySaved();

        void entitySaveError(Throwable e);


        void setContent(@NonNull String name,
                        @Nullable String description,
                        @NonNull ReadableInstant birth,
                        @NonNull ReadableInstant death,
                        @ColorInt int color);
    }

    interface Presenter extends BasePresenter<View, Entity> {

        void setView(View view);

        void saveEntity(@NonNull String inputNameText);

        void setName(@NonNull String name);

        void setDescription(@Nullable String description);

        void setBirth(@NonNull String dateTimeIso8601);

        void setDeath(@NonNull String dateTimeIso8601);

        void setColor(@ColorInt int color);
    }
}
