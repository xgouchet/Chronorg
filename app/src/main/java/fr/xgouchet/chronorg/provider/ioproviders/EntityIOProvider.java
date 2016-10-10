package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.queriers.BaseContentQuerier;
import fr.xgouchet.chronorg.provider.queriers.EntityContentQuerier;
import fr.xgouchet.chronorg.provider.readers.BaseCursorReader;
import fr.xgouchet.chronorg.provider.readers.EntityCursorReader;
import fr.xgouchet.chronorg.provider.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.writers.EntityContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EntityIOProvider implements BaseIOProvider<Entity> {

    @NonNull @Override public BaseCursorReader<Entity> provideReader(@NonNull Cursor cursor) {
        return new EntityCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Entity> provideWriter() {
        return new EntityContentValuesWriter();
    }

    @NonNull @Override
    public BaseContentQuerier<Entity> provideQuerier() {
        return new EntityContentQuerier(this);
    }

}
