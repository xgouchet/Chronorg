package fr.xgouchet.chronorg.provider.db;


import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO handle creation / removal of columns
 *
 * @author Xavier Gouchet
 */
public class TableDescription {

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";
    private static final String ALTER_TABLE = "ALTER TABLE";
    private static final String ADD_COLUMN = "ADD COLUMN";

    @NonNull
    private final List<ColumnDescription> columns = new ArrayList<>();
    @NonNull
    private final Set<String> columnNames = new HashSet<>();
    @NonNull
    private final String name;
    @IntRange(from = 0)
    private final int since, until;

    public TableDescription(@NonNull String name) {
        this(name, 0, Integer.MAX_VALUE);
    }

    public TableDescription(@NonNull String name,
                            @IntRange(from = 0) int since,
                            @IntRange(from = 0) int until) {
        this.name = name;
        this.since = since;
        this.until = until;
    }


    @NonNull
    public String getName() {
        return name;
    }

    @IntRange(from = 0)
    public int getSince() {
        return since;
    }

    @IntRange(from = 0)
    public int getUntil() {
        return until;
    }


    public void addColumn(@NonNull ColumnDescription column) {

        if (columnNames.contains(column.getName())) {
            if (!columns.contains(column)) {
                throw new IllegalArgumentException("A column with this name already exists");
            }
        } else {
            columns.add(column);
            columnNames.add(column.getName());
        }
    }

    @NonNull
    public String getCreateStatement(@IntRange(from = 0) int version) {
        StringBuilder builder = new StringBuilder();

        builder
                .append(CREATE_TABLE)
                .append(SQLiteDescription.BLANK)
                .append(name)
                .append(SQLiteDescription.BLANK)
                .append(SQLiteDescription.PAR_OPEN);

        // columns descriptions
        if (columns.size() > 0) {
            for (ColumnDescription column : columns) {
                if (column.getSince() <= version) {
                    builder.append(column.getDefinition());
                    builder.append(SQLiteDescription.COMMA);
                }
            }

            builder.setLength(builder.length() - 1);
        }

        builder.append(SQLiteDescription.PAR_CLOSE);

        return builder.toString();
    }

    @NonNull
    public String getDropStatement() {
        StringBuilder builder = new StringBuilder();

        builder
                .append(DROP_TABLE)
                .append(SQLiteDescription.BLANK)
                .append(name);

        return builder.toString();
    }


    @Nullable
    public String getUpgradeStatement(int fromVersion, int toVersion) {

        StringBuilder builder = new StringBuilder();
        int alteredColumns = 0;

        if (columns.size() > 0) {
            for (ColumnDescription column : columns) {
                if (column.getSince() == toVersion) {
                    builder.append(ALTER_TABLE)
                            .append(SQLiteDescription.BLANK)
                            .append(name)
                            .append(SQLiteDescription.BLANK)
                            .append(ADD_COLUMN)
                            .append(SQLiteDescription.BLANK)
                            .append(column.getDefinition())
                            .append(SQLiteDescription.SEMICOLON);
                    alteredColumns++;
                }
            }
        }

        if (alteredColumns == 0) {
            return null;
        } else {
            return builder.toString();
        }
    }
}

