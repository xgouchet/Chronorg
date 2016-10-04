package fr.xgouchet.chronorg.provider.db;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Xavier Gouchet
 */
public class SQLiteDescription {

    static final char PAR_OPEN = '(';
    static final char PAR_CLOSE = ')';
    static final char BLANK = ' ';
    static final char COMMA = ',';
    static final char SEMICOLON = ';';

    @NonNull
    private final String name;
    @IntRange(from = 0)
    private final int version;

    @NonNull
    private final List<TableDescription> tables = new ArrayList<>();
    @NonNull
    private final Set<String> tableNames = new HashSet<>();


    public SQLiteDescription(@NonNull String name, @IntRange(from = 0) int version) {
        this.name = name;
        this.version = version;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public void createDatabase(@NonNull SQLiteDatabase db) {
        db.beginTransaction();

        try {
            createAllTables(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    public void upgradeDatabase(@NonNull SQLiteDatabase db,
                                @IntRange(from = 0) int oldVersion,
                                @IntRange(from = 0) int newVersion) {

        for (int version = oldVersion; version < newVersion; version++) {
            upgradeAllTables(db, version, version + 1);
        }
    }

    public void downgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException();
    }

    public void addTable(TableDescription table) {
        if (tableNames.contains(table.getName())) {
            if (!tables.contains(table)) {
                throw new IllegalArgumentException("A table with this name already exists");
            }
        } else {
            tables.add(table);
            tableNames.add(table.getName());
        }
    }

    private void createAllTables(@NonNull SQLiteDatabase db) {
        for (TableDescription table : tables) {
            if ((table.getSince() <= version)
                    && (version <= table.getUntil())) {
                final String createStatement = table.getCreateStatement(version);
                db.execSQL(createStatement);
            }
        }
    }

    private void upgradeAllTables(@NonNull SQLiteDatabase db, int fromVersion, int toVersion) {
        for (TableDescription table : tables) {
            if (table.getUntil() == fromVersion) {
                db.execSQL(table.getDropStatement());
            } else if (table.getSince() == toVersion) {
                db.execSQL(table.getCreateStatement(toVersion));
            } else {
                String upgradeStatement = table.getUpgradeStatement(fromVersion, toVersion);
                if (upgradeStatement != null) {
                    db.execSQL(upgradeStatement);
                }
            }
        }
    }
}
