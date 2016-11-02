package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EventContentQuerier extends BaseContentQuerier<Event> {


    public EventContentQuerier(@NonNull IOProvider<Event> provider) {
        super(provider);
    }


    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<Event> action,
                               int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    order());

            readRows(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.EVENTS_URI;
    }


    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    @Override protected String order() {
        return ChronorgSchema.COL_INSTANT + " ASC";
    }

    @Override protected int getId(@NonNull Event event) {
        return event.getId();
    }
}
