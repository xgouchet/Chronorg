package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public class JumpContentQuerier extends BaseContentQuerier<Jump> {


    public JumpContentQuerier(@NonNull IOProvider<Jump> provider) {
        super(provider);
    }

    public void fillEntity(@NonNull ContentResolver contentResolver,
                           @NonNull final Entity entity) {
        queryInEntity(contentResolver,
                new Action1<Jump>() {
                    @Override public void call(Jump jump) {
                        entity.jump(jump);
                    }
                },
                entity.getId());
    }


    public void queryInEntity(@NonNull ContentResolver contentResolver,
                              @NonNull Action1<Jump> action,
                              int entityId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectByEntityId(),
                    new String[]{Integer.toString(entityId)},
                    order());

            readRows(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }


    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.JUMPS_URI;
    }

    @Override protected int getId(@NonNull Jump jump) {
        return jump.getId();
    }

    private String selectByEntityId() {
        return ChronorgSchema.COL_ENTITY_ID + "=?";
    }

    @Override protected String order() {
        return ChronorgSchema.COL_ORDER + " ASC";
    }
}
