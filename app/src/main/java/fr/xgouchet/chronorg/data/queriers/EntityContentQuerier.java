package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityContentQuerier extends BaseContentQuerier<Entity> {


    @NonNull private final JumpContentQuerier jumpContentQuerier;

    public EntityContentQuerier(@NonNull IOProvider<Entity> provider,
                                @NonNull JumpContentQuerier jumpContentQuerier) {
        super(provider);
        this.jumpContentQuerier = jumpContentQuerier;
    }


    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<Entity> action,
                               int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    defaultOrder());

            readEntities(contentResolver, action, cursor, false);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryFullInProject(@NonNull ContentResolver contentResolver,
                                   @NonNull Action1<Entity> action,
                                   int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    defaultOrder());

            readEntities(contentResolver, action, cursor, true);
        } finally {
            if (cursor != null) cursor.close();
        }
    }


    private void readEntities(@NonNull ContentResolver contentResolver,
                              @NonNull Action1<Entity> subscriber,
                              @Nullable Cursor cursor, boolean full) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<Entity> reader = ioProvider.provideReader(cursor);
            while (cursor.moveToNext()) {
                final Entity entity = reader.instantiateAndFill();
                if (full) {
                    readEntityJumps(contentResolver, entity);
                }
                subscriber.call(entity);
            }
        }
    }

    private void readEntityJumps(@NonNull ContentResolver contentResolver,
                                 @NonNull final Entity entity) {
        jumpContentQuerier.queryInEntity(contentResolver,
                new Action1<Jump>() {
                    @Override public void call(Jump jump) {
                        entity.jump(jump);
                    }
                },
                entity.getId());

    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.ENTITIES_URI;
    }

    @Override
    protected String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    @Nullable @Override protected String defaultOrder() {
        return ChronorgSchema.COL_NAME + " ASC";
    }

    @Override protected int getId(@NonNull Entity entity) {
        return entity.getId();
    }
}
