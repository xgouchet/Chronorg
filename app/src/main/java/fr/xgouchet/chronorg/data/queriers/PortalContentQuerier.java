package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalContentQuerier extends BaseContentQuerier<Portal> {


    public PortalContentQuerier(@NonNull IOProvider<Portal> provider) {
        super(provider);
    }


    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<Portal> action,
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

    public void fillTimelinePortal(@NonNull ContentResolver contentResolver,
                                   @NonNull final Timeline timeline) {
        query(contentResolver,
                new Action1<Portal>() {
                    @Override public void call(Portal portal) {
                        timeline.setPortal(portal);
                    }
                },
                timeline.getPortalId());
    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.PORTALS_URI;
    }

    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    @Override protected String order() {
        return ChronorgSchema.COL_DELAY + " ASC";
    }

    @Override protected int getId(@NonNull Portal portal) {
        return portal.getId();
    }
}
