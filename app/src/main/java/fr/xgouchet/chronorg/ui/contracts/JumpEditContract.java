package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.contracts.presenters.BasePresenter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface JumpEditContract {


    interface View extends BaseView<Presenter, Jump> {

        void jumpSaved();

        void jumpSaveError(Throwable e);

        void setContent(@Nullable String name,
                        @NonNull ReadableInstant from,
                        @NonNull ReadableInstant to);
    }

    interface Presenter extends BasePresenter<View, Jump> {

        void saveJump(@NonNull String inputNameText);

        void setName(@NonNull String name);

        void setDescription(@Nullable String description);

        void setFrom(@NonNull String dateTimeIso8601);

        void setTo(@NonNull String dateTimeIso8601);
    }
}
