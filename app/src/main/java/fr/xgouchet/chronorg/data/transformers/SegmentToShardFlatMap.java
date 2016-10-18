package fr.xgouchet.chronorg.data.transformers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.xgouchet.chronorg.data.models.Segment;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Xavier Gouchet
 */
public class SegmentToShardFlatMap
        implements Func1<List<Segment>, Observable<TimelineShard>> {


    @Override public Observable<TimelineShard> call(List<Segment> segments) {
        List<Segment> openSegments = new ArrayList<>();
        List<TimelineShard> shards = new LinkedList<>();

        int index = 0;

        Segment nextToClose = null;

        while ((index < segments.size()) || (nextToClose != null)) {
            Segment nextToStart;
            if (index < segments.size())
                nextToStart = segments.get(index);
            else
                nextToStart = null;

            if ((nextToClose != null)
                    && ((nextToStart == null)
                    || (nextToClose.getTo().isBefore(nextToStart.getFrom())))) {
                TimelineShard.Builder shardBuilder =
                        new TimelineShard.Builder(
                                TimelineShard.TYPE_END,
                                nextToClose.getColor(),
                                nextToClose.getTo())
                                .withLegend(nextToClose.getLegendTo());
                int atPosition = -1;
                for (int i = 0; i < openSegments.size(); i++) {
                    Segment ongoing = openSegments.get(i);
                    if (ongoing == nextToClose) {
                        atPosition = i;
                        shardBuilder.withPosition(i);
                    }
                    shardBuilder.withOngoingSegment(ongoing);
                }
                shards.add(shardBuilder.build());

                // new next to close
                nextToClose = null;
                openSegments.set(atPosition, null);
                for (Segment openSegment : openSegments) {
                    if (openSegment == null) continue;
                    if (nextToClose == null
                            || nextToClose.getTo().isAfter(openSegment.getTo()))
                        nextToClose = openSegment;
                }

            } else if (nextToStart != null) {
                boolean isEvent = nextToStart.getFrom() == nextToStart.getTo();

                TimelineShard.Builder shardBuilder =
                        new TimelineShard.Builder(
                                isEvent ? TimelineShard.TYPE_EVENT : TimelineShard.TYPE_START,
                                nextToStart.getColor(),
                                nextToStart.getFrom())
                                .withLegend(nextToStart.getLegendFrom());

                boolean added = false;
                for (int i = 0; i < openSegments.size(); i++) {
                    Segment ongoing = openSegments.get(i);
                    if ((ongoing == null) && !added) {
                        ongoing = nextToStart;
                        shardBuilder.withPosition(i);
                        if (!isEvent) openSegments.set(i, nextToStart);
                        added = true;
                    }
                    shardBuilder.withOngoingSegment(ongoing);
                }
                if (!added) {
                    shardBuilder.withPosition(openSegments.size());
                    if (!isEvent) openSegments.add(nextToStart);
                    shardBuilder.withOngoingSegment(nextToStart);
                }
                shards.add(shardBuilder.build());

                // update nextToClose
                if (!isEvent)
                    if ((nextToClose == null) || (nextToClose.getTo().isAfter(nextToStart.getTo())))
                        nextToClose = nextToStart;

                index++;
            }
        }

        return Observable.from(shards);
    }
}
