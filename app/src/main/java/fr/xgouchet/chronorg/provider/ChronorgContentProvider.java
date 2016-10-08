package fr.xgouchet.chronorg.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.provider.db.ChronorgDatabaseHelper;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class ChronorgContentProvider extends ContentProvider {


    @NonNull private final ChronorgSchema chronorgSchema;
    @NonNull private final UriMatcher uriMatcher;
    private ChronorgDatabaseHelper chronorgDatabaseHelper;

    public ChronorgContentProvider() {
        this(new ChronorgSchema());
    }

    public ChronorgContentProvider(@NonNull ChronorgSchema chronorgSchema) {
        this.chronorgSchema = chronorgSchema;
        uriMatcher = chronorgSchema.buildUriMatcher();
    }

    @Override public boolean onCreate() {
        final Context context = getContext();
        if (context == null) {
            return false;
        }

        chronorgDatabaseHelper = new ChronorgDatabaseHelper(context, chronorgSchema, null);
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
        int match = uriMatcher.match(uri);

        Cursor result;

        switch (match) {
            case ChronorgSchema.MATCH_PROJECTS:
                result = chronorgDatabaseHelper.getProjectDao().query(projection, selection, selectionArgs, sortOrder);
                break;
            case ChronorgSchema.MATCH_PROJECT_ENTITIES:
                result = chronorgDatabaseHelper.getEntityDao().query(projection, selection, selectionArgs, sortOrder);
                break;
            default:
                result = null;
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
                long projectId = chronorgDatabaseHelper.getProjectDao().insert(values);
                result = chronorgSchema.projectUri(projectId);
                break;
            case ChronorgSchema.MATCH_PROJECT_ENTITIES:
                long entityId = chronorgDatabaseHelper.getEntityDao().insert(values);
                result = chronorgSchema.entityUri(entityId);
                break;
        }

        return result;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        int deleted;

        switch (match) {
            case ChronorgSchema.MATCH_PROJECTS:
                deleted = chronorgDatabaseHelper.getProjectDao().delete(selection, selectionArgs);
                break;
            default:
                deleted = 0;
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        int updated;

        switch (match) {
            case ChronorgSchema.MATCH_PROJECTS:
                updated = chronorgDatabaseHelper.getProjectDao().update(values, selection, selectionArgs);
                break;
            default:
                updated = 0;
        }
        return updated;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method,
                       @Nullable String arg,
                       @Nullable Bundle extras) {
        return null;
    }
}
