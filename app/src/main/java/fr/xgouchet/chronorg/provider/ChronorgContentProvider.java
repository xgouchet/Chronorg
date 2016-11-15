package fr.xgouchet.chronorg.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.data.writers.TimelineContentValuesWriter;
import fr.xgouchet.chronorg.provider.dao.BaseDao;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import fr.xgouchet.chronorg.provider.db.SQLiteDescriptionHelper;

/**
 * @author Xavier Gouchet
 */
public class  ChronorgContentProvider extends ContentProvider {

    public static final String SELECT_BY_PROJECT_ID = ChronorgSchema.COL_PROJECT_ID + "=?";
    public static final String SELECT_BY_ENTITY_ID = ChronorgSchema.COL_ENTITY_ID + "=?";

    @NonNull private final ChronorgSchema chronorgSchema;
    @NonNull private final UriMatcher uriMatcher;

    private BaseDao projectDao;
    private BaseDao entityDao;
    private BaseDao jumpDao;
    private BaseDao eventDao;
    private BaseDao portalDao;
    private BaseDao timelineDao;

    private TimelineContentValuesWriter timelineContentValuesWriter;


    @SuppressWarnings("unused")
    public ChronorgContentProvider() {
        this(new ChronorgSchema());
        // TODO inject in onCreate
        timelineContentValuesWriter = new TimelineContentValuesWriter();
    }

    public ChronorgContentProvider(@NonNull ChronorgSchema chronorgSchema) {
        this.chronorgSchema = chronorgSchema;
        uriMatcher = chronorgSchema.buildUriMatcher();
    }

    @VisibleForTesting
    /*package*/ ChronorgContentProvider(@NonNull ChronorgSchema chronorgSchema,
                                        @NonNull BaseDao projectDao,
                                        @NonNull BaseDao entityDao,
                                        @NonNull BaseDao jumpDao,
                                        @NonNull BaseDao eventDao,
                                        @NonNull BaseDao portalDao) {
        this.chronorgSchema = chronorgSchema;
        uriMatcher = chronorgSchema.buildUriMatcher();
        this.projectDao = projectDao;
        this.entityDao = entityDao;
        this.jumpDao = jumpDao;
        this.eventDao = eventDao;
        this.portalDao = portalDao;
    }

    @Override public boolean onCreate() {
        final Context context = getContext();
        if (context == null) {
            return false;
        }

        SQLiteDescriptionHelper databaseHelper = new SQLiteDescriptionHelper(context, chronorgSchema, null);
        projectDao = new BaseDao(databaseHelper, ChronorgSchema.TABLE_PROJECTS);
        entityDao = new BaseDao(databaseHelper, ChronorgSchema.TABLE_ENTITIES);
        jumpDao = new BaseDao(databaseHelper, ChronorgSchema.TABLE_JUMPS);
        eventDao = new BaseDao(databaseHelper, ChronorgSchema.TABLE_EVENTS);
        portalDao = new BaseDao(databaseHelper, ChronorgSchema.TABLE_PORTALS);
        timelineDao = new BaseDao(databaseHelper, ChronorgSchema.TABLE_TIMELINES);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor result = null;
        BaseDao dao = getDao(uri);

        if (dao != null) {
            result = dao.query(projection, selection, selectionArgs, sortOrder);
        }

        return result;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = uriMatcher.match(uri);
        Uri result = null;

        switch (match) {
            case ChronorgSchema.MATCH_PROJECTS:
                long projectId = projectDao.insert(values);
                createFirstTimeline(projectId);
                result = chronorgSchema.projectUri(projectId);
                break;
            case ChronorgSchema.MATCH_ENTITIES:
                long entityId = entityDao.insert(values);
                result = chronorgSchema.entityUri(entityId);
                break;
            case ChronorgSchema.MATCH_JUMPS:
                long jumpId = jumpDao.insert(values);
                result = chronorgSchema.jumpUri(jumpId);
                break;
            case ChronorgSchema.MATCH_EVENTS:
                long eventId = eventDao.insert(values);
                result = chronorgSchema.eventUri(eventId);
                break;
            case ChronorgSchema.MATCH_PORTALS:
                long portalId = portalDao.insert(values);
                result = chronorgSchema.portalUri(portalId);
                break;
        }

        return result;
    }

    private void createFirstTimeline(long projectId) {
        Timeline timeline = new Timeline();
        timeline.setProjectId(projectId);
        timeline.setParentId(0);
        timeline.setPortalId(0);
        //noinspection ConstantConditions
        timeline.setName(getContext().getString(R.string.default_timeline_name));

        timelineDao.insert(timelineContentValuesWriter.toContentValues(timeline));
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        int deleted = 0;
        int match = uriMatcher.match(uri);


        switch (match) {
            case ChronorgSchema.MATCH_PROJECTS:
                deleted = deleteProtectsRecursive(selection, selectionArgs);
                break;
            case ChronorgSchema.MATCH_ENTITIES:
                deleted = deleteEntitiesRecursive(selection, selectionArgs);
                break;
            default:
                BaseDao dao = getDao(uri);
                if (dao != null) {
                    deleted = dao.delete(selection, selectionArgs);
                }
                break;
        }

        return deleted;
    }

    private int deleteProtectsRecursive(@Nullable String selection, @Nullable String[] selectionArgs) {
        final String[] selectProjectArgs = new String[1];
        Cursor cursor = projectDao.query(new String[]{ChronorgSchema.COL_ID}, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            selectProjectArgs[0] = Integer.toString(id);
            deleteEntitiesRecursive(SELECT_BY_PROJECT_ID, selectProjectArgs);
            eventDao.delete(SELECT_BY_PROJECT_ID, selectProjectArgs);
            portalDao.delete(SELECT_BY_PROJECT_ID, selectProjectArgs);
            timelineDao.delete(SELECT_BY_PROJECT_ID, selectProjectArgs);
        }
        cursor.close();

        return projectDao.delete(selection, selectionArgs);
    }


    private int deleteEntitiesRecursive(@Nullable String selection, @Nullable String[] selectionArgs) {
        Cursor cursor = entityDao.query(new String[]{ChronorgSchema.COL_ID}, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            jumpDao.delete(SELECT_BY_ENTITY_ID, new String[]{Integer.toString(id)});
        }
        cursor.close();

        return entityDao.delete(selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int updated = 0;
        BaseDao dao = getDao(uri);

        if (dao != null) {
            updated = dao.update(values, selection, selectionArgs);
        }

        return updated;
    }


    @Nullable
    private BaseDao getDao(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case ChronorgSchema.MATCH_PROJECTS:
                return projectDao;
            case ChronorgSchema.MATCH_ENTITIES:
                return entityDao;
            case ChronorgSchema.MATCH_JUMPS:
                return jumpDao;
            case ChronorgSchema.MATCH_EVENTS:
                return eventDao;
            case ChronorgSchema.MATCH_PORTALS:
                return portalDao;
            case ChronorgSchema.MATCH_TIMELINES:
                return timelineDao;
            default:
                return null;
        }
    }


}
