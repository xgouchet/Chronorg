package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Portal;
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
                    defaultOrder());

            readRows(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.PORTALS_URI;
    }

    @Override protected String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    @Override protected String defaultOrder() {
        return ChronorgSchema.COL_DELAY + " ASC";
    }

    @Override protected int getId(@NonNull Portal portal) {
        return portal.getId();
    }
}
