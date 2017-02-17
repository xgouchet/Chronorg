package fr.xgouchet.chronorg.ui.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseEditPresenter;

/**
 * @author Xavier F. Gouchet
 */
public class TimelineEditFragment extends BaseEditFragment<Timeline> {

    private Formatter<ReadableInstant> formatter;
    private BaseEditPresenter<Timeline> presenter;

    @Override
    protected boolean isDeletable() {
        return false;
    }

    @Override
    protected void onDeleteItemClicked() {

    }

    @Override
    protected void onSaveItemClicked() {

    }

    @Override
    public void onColorSelected(int color) {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onError(@Nullable Throwable t) {

    }

    @Override
    public void setPresenter(@NonNull BaseEditPresenter<Timeline> presenter) {

    }

    @Override
    public void setError(@Nullable Throwable throwable) {

    }

    @Override
    public void setContent(@NonNull Timeline content) {

    }
}
