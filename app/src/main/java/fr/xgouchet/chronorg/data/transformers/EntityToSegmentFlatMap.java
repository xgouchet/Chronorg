package fr.xgouchet.chronorg.data.transformers;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Segment;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Xavier Gouchet
 */
public class EntityToSegmentFlatMap
        implements Func1<Entity, Observable<Segment>> {

    @Override public Observable<Segment> call(Entity entity) {
        return Observable.from(entity.timelineAsSegments());
    }
}
