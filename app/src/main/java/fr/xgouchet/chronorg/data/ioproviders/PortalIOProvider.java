package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.queriers.PortalContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.PortalCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.PortalContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class PortalIOProvider implements IOProvider<Portal> {

    @NonNull @Override public BaseCursorReader<Portal> provideReader(@NonNull Cursor cursor) {
        return new PortalCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Portal> provideWriter() {
        return new PortalContentValuesWriter();
    }

    @NonNull @Override public ContentQuerier<Portal> provideQuerier() {
        return new PortalContentQuerier(this);
    }

}
