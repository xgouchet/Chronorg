package fr.xgouchet.chronorg.data.transformers;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.models.Segment;
import rx.functions.Func1;

/**
 * @author Xavier Gouchet
 */
public class EventToSegmentMap
        implements Func1<Event, Segment> {

    @Override public Segment call(Event event) {
        return new Segment(event.getInstant(), event.getName(), event.getColor());
    }
}
