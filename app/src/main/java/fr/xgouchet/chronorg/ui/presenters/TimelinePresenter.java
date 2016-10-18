package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.models.Segment;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.EventRepository;
import fr.xgouchet.chronorg.data.transformers.EntityToSegmentFlatMap;
import fr.xgouchet.chronorg.data.transformers.EventToSegmentMap;
import fr.xgouchet.chronorg.data.transformers.SegmentToShardFlatMap;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
public class TimelinePresenter extends BaseListPresenter<TimelineShard> {

    @NonNull private final EntityRepository entityRepository;
    @NonNull private final EventRepository eventRepository;
    @Nullable private Project project;

    public TimelinePresenter(@NonNull EntityRepository entityRepository,
                             @NonNull EventRepository eventRepository) {
        this.entityRepository = entityRepository;
        this.eventRepository = eventRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override protected Observable<TimelineShard> getItemsObservable() {
        final Observable<Segment> entitySegments = entityRepository
                .getFullEntitiesInProject(project.getId())
                .flatMap(new EntityToSegmentFlatMap());

        final Observable<Segment> eventsSegments = eventRepository
                .getEventsInProject(project.getId())
                .map(new EventToSegmentMap());


        return Observable.merge(entitySegments, eventsSegments)
                .toSortedList()
                .flatMap(new SegmentToShardFlatMap());
    }
}
