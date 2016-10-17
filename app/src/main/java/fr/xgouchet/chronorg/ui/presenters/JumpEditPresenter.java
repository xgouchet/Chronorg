package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.ui.contracts.JumpEditContract;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
public class JumpEditPresenter implements JumpEditContract.Presenter {

    @Nullable private Jump jump;

    @Nullable /*package*/ JumpEditContract.View view;

    @NonNull private final JumpRepository jumpRepository;

    @Nullable private String name;
    @Nullable private String description;
    @NonNull private ReadableInstant from = new DateTime("1970-01-01T00:00:00Z");
    @NonNull private ReadableInstant to = new DateTime("1970-01-01T00:00:00Z");

    public JumpEditPresenter(@NonNull JumpRepository jumpRepository) {
        this.jumpRepository = jumpRepository;
    }

    public void setJump(@NonNull Jump jump) {
        this.jump = jump;
        name = jump.getName();
        description = jump.getDescription();
        from = jump.getFrom();
        to = jump.getTo();
    }

    @Override public void setView(@NonNull JumpEditContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;

        view.setContent(name, description, from, to);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override public void setFrom(@NonNull String dateTimeIso8601) {
        from = new DateTime(dateTimeIso8601);
    }

    @Override public void setTo(@NonNull String dateTimeIso8601) {
        to = new DateTime(dateTimeIso8601);
    }

    @Override
    public void saveJump(@NonNull String inputNameText, @NonNull String inputDescText) {
        if (view == null) return;
        if (jump == null) return;

        jump.setName(inputNameText);
        jump.setDescription(inputDescText);
        jump.setFrom(from);
        jump.setTo(to);
        jumpRepository.saveJump(jump)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override public void onCompleted() {
                        view.jumpSaved();
                    }

                    @Override public void onError(Throwable e) {
                        view.jumpSaveError(e);
                    }

                    @Override public void onNext(Void nothing) {
                        // Ignore
                    }
                });
    }
}