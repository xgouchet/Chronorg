package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.fragments.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface JumpEditContract {

    public static final int EMPTY = 1;

    interface View extends BaseView<Presenter, Jump> {

        void jumpSaved();

        void jumpSaveError(Throwable e);

        void invalidFrom(int reason);

        void invalidTo(int reason);

        void setContent(@Nullable String name,
                        @Nullable String description,
                        @NonNull ReadableInstant from,
                        @NonNull ReadableInstant to);
    }

    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {

        void setView(View view);

        void saveJump(@NonNull String inputNameText,
                      @NonNull String inputDescText);

        void setName(@NonNull String name);

        void setDescription(@Nullable String description);

        void setFrom(@NonNull String dateTimeIso8601);

        void setTo(@NonNull String dateTimeIso8601);
    }
}
