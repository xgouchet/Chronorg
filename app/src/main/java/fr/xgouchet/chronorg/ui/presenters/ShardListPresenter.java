package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.models.Segment;
import fr.xgouchet.chronorg.data.models.Shard;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.EventRepository;
import fr.xgouchet.chronorg.data.transformers.EntityToSegmentFlatMap;
import fr.xgouchet.chronorg.data.transformers.EventToSegmentMap;
import fr.xgouchet.chronorg.data.transformers.SegmentToShardFlatMap;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseListPresenter;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ShardListPresenter extends BaseListPresenter<Shard> {

    @NonNull private final EntityRepository entityRepository;
    @NonNull private final EventRepository eventRepository;
    @Nullable private Project project;

    public ShardListPresenter(@NonNull EntityRepository entityRepository,
                              @NonNull EventRepository eventRepository) {
        this.entityRepository = entityRepository;
        this.eventRepository = eventRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override protected Observable<Shard> getItemsObservable() {
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
