package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.TimelineRepository;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class TimelineListPresenter extends BaseListPresenter<Timeline> {

    @NonNull private final TimelineRepository eventRepository;
    @Nullable private Project project;

    public TimelineListPresenter(@NonNull TimelineRepository portalRepository) {
        this.eventRepository = portalRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override protected Observable<Timeline> getItemsObservable() {
        return eventRepository.getFullTimelinesInProject(project.getId());
    }
}
