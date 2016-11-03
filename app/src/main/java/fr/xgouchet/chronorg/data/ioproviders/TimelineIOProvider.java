package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.queriers.PortalContentQuerier;
import fr.xgouchet.chronorg.data.queriers.TimelineContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.TimelineCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.TimelineContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class TimelineIOProvider implements IOProvider<Timeline> {

    @NonNull private final PortalContentQuerier portalContentQuerier;

    public TimelineIOProvider(@NonNull PortalContentQuerier portalContentQuerier) {
        this.portalContentQuerier = portalContentQuerier;
    }

    @NonNull @Override public BaseCursorReader<Timeline> provideReader(@NonNull Cursor cursor) {
        return new TimelineCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Timeline> provideWriter() {
        return new TimelineContentValuesWriter();
    }

    @NonNull @Override public ContentQuerier<Timeline> provideQuerier() {
        return new TimelineContentQuerier(this, portalContentQuerier);
    }

}
