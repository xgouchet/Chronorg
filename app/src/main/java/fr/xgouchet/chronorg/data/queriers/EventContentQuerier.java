package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
public class EventContentQuerier extends BaseContentQuerier<Event> {


    public EventContentQuerier(@NonNull IOProvider<Event> provider) {
        super(provider);
    }


    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<Event> action,
                               int entityId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(entityId)},
                    defaultOrder());

            readRows(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.EVENTS_URI;
    }

    @Override protected String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    @Override protected String defaultOrder() {
        return ChronorgSchema.COL_INSTANT + " ASC";
    }

    @Override protected int getId(@NonNull Event event) {
        return event.getId();
    }
}
