package fr.xgouchet.chronorg.provider.db;


import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Xavier Gouchet
 */
public class ColumnDescription {


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_INTEGER, TYPE_TEXT, TYPE_NULL, TYPE_REAL, TYPE_BLOB})
    public @interface ColumnType {
    }

    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_NULL = "NULL";
    public static final String TYPE_REAL = "REAL";
    public static final String TYPE_BLOB = "BLOB";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PRIMARY_KEY, AUTOINCREMENT, NOT_NULL, UNIQUE})
    public @interface ColumnOption {
    }

    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String AUTOINCREMENT = "AUTOINCREMENT";
    public static final String NOT_NULL = "NOT NULL";
    public static final String UNIQUE = "UNIQUE";


    @NonNull
    private final String name;

    @ColumnType
    private final String type;

    private final boolean primaryKey, autoIncrement, notNull, unique;

    public ColumnDescription(@NonNull String name,
                             @ColumnType String type,
                             @ColumnOption String... options) {
        this.name = name;
        this.type = type;

        // read options
        boolean primaryKey = false, autoInc = false, notNull = false, unique = false;
        if (options != null) {
            for (String option : options) {
                if (PRIMARY_KEY.equals(option)) {
                    primaryKey = true;
                }
                if (AUTOINCREMENT.equals(option) && TYPE_INTEGER.equals(type)) {
                    autoInc = true;
                }
                if (NOT_NULL.equals(option) && !TYPE_NULL.equals(type)) {
                    notNull = true;
                }
                if (UNIQUE.equals(option)) {
                    unique = true;
                }
            }
        }

        // set fields
        this.primaryKey = primaryKey;
        autoIncrement = autoInc;
        this.notNull = notNull;
        this.unique = unique;
    }

    public String getDefinition() {
        StringBuilder builder = new StringBuilder();

        builder.append(name);
        builder.append(SQLiteDescription.BLANK);
        builder.append(type);

        if (primaryKey) {
            builder.append(SQLiteDescription.BLANK);
            builder.append(PRIMARY_KEY);
        }

        if (autoIncrement) {
            builder.append(SQLiteDescription.BLANK);
            builder.append(AUTOINCREMENT);
        }

        if (notNull) {
            builder.append(SQLiteDescription.BLANK);
            builder.append(NOT_NULL);
        }

        if (unique) {
            builder.append(SQLiteDescription.BLANK);
            builder.append(UNIQUE);
        }

        return builder.toString();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public boolean isUnique() {
        return unique;
    }

    @IntRange(from = 0)
    public int getSince() {
        return 0;
    }
}
