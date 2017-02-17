package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.data.repositories.TimelineRepository;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseEditPresenter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseEditView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier F. Gouchet
 */
public class TimelineEditPresenter implements BaseEditPresenter<Timeline> {

    @Nullable
    private Timeline timeline;

    @Nullable /*package*/ BaseEditView<Timeline> view;

    @NonNull
    private final TimelineRepository timelineRepository;

    public TimelineEditPresenter(@NonNull TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    public void setTimeline(@NonNull Timeline timeline) {
        this.timeline = timeline;
    }

    @Override public void setView(@NonNull BaseEditView<Timeline> view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;
        if (timeline == null) return;

        Timeline viewTimeline = new Timeline();
        viewTimeline.setId(timeline.getId());
        viewTimeline.setProjectId(timeline.getProjectId());
        viewTimeline.setName(timeline.getName());
//        viewTimeline.setDelay(timeline.getDelay().toString());
//        viewTimeline.setColor(timeline.getColor());
        viewTimeline.setDirection(timeline.getDirection());

        view.setContent(viewTimeline);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override public void setItemValue(@NonNull Timeline viewTimeline) {
        if (timeline == null) return;

        timeline.setName(viewTimeline.getName());
//        timeline.setDelay(viewTimeline.getDelay().toString());
        timeline.setDirection(viewTimeline.getDirection());
//        timeline.setColor(viewTimeline.getColor());
    }

    @Override public void saveItem() {
        if (view == null) return;
        if (timeline == null) return;

        timelineRepository.save(timeline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EditActionSubscriber(view));
    }

    @Override public void deleteItem() {
        if (view == null) return;
        if (timeline == null) return;

        timelineRepository.delete(timeline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EditActionSubscriber(view));
    }
}
