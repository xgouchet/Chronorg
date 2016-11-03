package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public class TimelineContentQuerier extends BaseContentQuerier<Timeline> {

    @NonNull private final PortalContentQuerier portalContentQuerier;

    public TimelineContentQuerier(@NonNull IOProvider<Timeline> provider,
                                  @NonNull PortalContentQuerier portalContentQuerier) {
        super(provider);
        this.portalContentQuerier = portalContentQuerier;
    }


    public void queryFull(@NonNull ContentResolver contentResolver,
                          @NonNull Action1<Timeline> action,
                          int id) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectById(),
                    new String[]{Integer.toString(id)},
                    order());

            readRows(contentResolver, action, cursor, true);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<Timeline> action,
                               int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    order());

            readRows(contentResolver, action, cursor, false);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryFullInProject(@NonNull ContentResolver contentResolver,
                                   @NonNull Action1<Timeline> action,
                                   int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    order());

            readRows(contentResolver, action, cursor, true);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    protected void readRows(@NonNull ContentResolver contentResolver,
                            @NonNull Action1<Timeline> action,
                            @Nullable Cursor cursor,
                            boolean full) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<Timeline> reader = ioProvider.provideReader(cursor);
            while (cursor.moveToNext()) {
                Timeline timeline = reader.instantiateAndFill();
                if (full) {
                    portalContentQuerier.fillTimelinePortal(contentResolver, timeline);
                    fillTimelineParent(contentResolver, timeline);
                }
                action.call(timeline);

            }
        }
    }

    private void fillTimelineParent(@NonNull ContentResolver contentResolver,
                                    @NonNull final Timeline timeline) {

        query(contentResolver,
                new Action1<Timeline>() {
                    @Override public void call(Timeline parent) {
                        timeline.setParent(parent);
                    }
                },
                timeline.getParentId());
    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.TIMELINES_URI;
    }

    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    @Override protected String order() {
        return ChronorgSchema.COL_ID + " ASC";
    }

    @Override protected int getId(@NonNull Timeline portal) {
        return portal.getId();
    }
}
