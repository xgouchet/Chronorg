package fr.xgouchet.chronorg.provider.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A SQLiteOpenHelper implementation based on a description of the database
 */
public class SQLiteDescriptionHelper extends SQLiteOpenHelper {

    @NonNull
    private final SQLiteDescription description;

    /**
     * Create a helper object to create, open, and/or manage a database. This method always returns
     * very quickly. The database is not actually created or opened until one of getWritableDatabase()
     * or getReadableDatabase() is called.
     *
     * @param context             to use to open or create the database
     * @param descriptionProvider a provider for the description of the database
     * @param factory             to use for creating cursor objects, or null for the default
     */
    public SQLiteDescriptionHelper(@NonNull Context context,
                                   @NonNull SQLiteDescriptionProvider descriptionProvider,
                                   @Nullable SQLiteDatabase.CursorFactory factory) {
        this(context, descriptionProvider.getDescription(), factory);
    }

    /**
     * Create a helper object to create, open, and/or manage a database. This method always returns
     * very quickly. The database is not actually created or opened until one of getWritableDatabase()
     * or getReadableDatabase() is called.
     *
     * @param context     to use to open or create the database
     * @param description the description of the database
     * @param factory     to use for creating cursor objects, or null for the default
     */
    public SQLiteDescriptionHelper(@NonNull Context context,
                                   @NonNull SQLiteDescription description,
                                   @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, description.getName(), factory, description.getVersion());
        this.description = description;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        description.createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        description.upgradeDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        description.downgradeDatabase(db, oldVersion, newVersion);
    }
}