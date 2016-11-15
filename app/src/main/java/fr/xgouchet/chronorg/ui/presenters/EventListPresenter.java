package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.EventRepository;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseListPresenter;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EventListPresenter extends BaseListPresenter<Event> {

    @NonNull private final EventRepository eventRepository;
    @Nullable private Project project;

    public EventListPresenter(@NonNull EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override protected Observable<Event> getItemsObservable() {
        return eventRepository.getEventsInProject(project.getId());
    }
}
