package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.cursorreaders.BaseCursorReader;
import fr.xgouchet.chronorg.provider.cvwriters.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.cvwriters.EntityContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class EntityIOProvider implements BaseIOProvider<Entity> {

    @Override public BaseCursorReader<Entity> provideReader(@NonNull Cursor cursor) {
        return null;
    }

    @Override public BaseContentValuesWriter<Entity> provideWriter() {
        return new EntityContentValuesWriter();
    }
}
