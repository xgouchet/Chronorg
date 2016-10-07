package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.cursorreaders.BaseCursorReader;
import fr.xgouchet.chronorg.provider.cursorreaders.EntityCursorReader;
import fr.xgouchet.chronorg.provider.cvwriters.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.cvwriters.EntityContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EntityIOProvider implements BaseIOProvider<Entity> {

    @Override public BaseCursorReader<Entity> provideReader(@NonNull Cursor cursor) {
        return new EntityCursorReader(cursor);
    }

    @Override public BaseContentValuesWriter<Entity> provideWriter() {
        return new EntityContentValuesWriter();
    }

    public String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }
}
