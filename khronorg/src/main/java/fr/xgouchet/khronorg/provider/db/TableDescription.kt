package fr.xgouchet.khronorg.provider.db


import android.support.annotation.IntRange

import java.util.ArrayList
import java.util.HashSet

/**
 * TODO handle removal of columns

 * @author Xavier Gouchet
 */
class TableDescription @JvmOverloads constructor(val name: String,
                                                 val since: Int = 0,
                                                 val until: Int = Integer.MAX_VALUE) {

    private val columns = ArrayList<ColumnDescription>()
    private val columnNames = HashSet<String>()


    fun addColumn(column: ColumnDescription) {

        if (columnNames.contains(column.name)) {
            if (!columns.contains(column)) {
                throw IllegalArgumentException("A column with this name already exists")
            }
        } else {
            columns.add(column)
            columnNames.add(column.name)
        }
    }

    fun getCreateStatement(@IntRange(from = 0) version: Int): String {
        val builder = StringBuilder()

        builder
                .append(CREATE_TABLE)
                .append(SQLiteDescription.BLANK)
                .append(name)
                .append(SQLiteDescription.BLANK)
                .append(SQLiteDescription.PAR_OPEN)

        // columns descriptions
        if (columns.size > 0) {
            for (column in columns) {
                if (column.since <= version) {
                    builder.append(column.definition)
                    builder.append(SQLiteDescription.COMMA)
                }
            }

            builder.setLength(builder.length - 1)
        }

        builder.append(SQLiteDescription.PAR_CLOSE)

        return builder.toString()
    }

    val dropStatement: String
        get() {
            val builder = StringBuilder()

            builder
                    .append(DROP_TABLE)
                    .append(SQLiteDescription.BLANK)
                    .append(name)

            return builder.toString()
        }


    fun getUpgradeStatement(fromVersion: Int, toVersion: Int): String? {

        // TODO use fromVersion  ?

        val builder = StringBuilder()
        var alteredColumns = 0

        if (columns.size > 0) {
            for (column in columns) {
                if (column.since == toVersion) {
                    builder.append(ALTER_TABLE)
                            .append(SQLiteDescription.BLANK)
                            .append(name)
                            .append(SQLiteDescription.BLANK)
                            .append(ADD_COLUMN)
                            .append(SQLiteDescription.BLANK)
                            .append(column.definition)
                            .append(SQLiteDescription.SEMICOLON)
                    alteredColumns++
                }
            }
        }

        if (alteredColumns == 0) {
            return null
        } else {
            return builder.toString()
        }
    }

    companion object {

        private val DROP_TABLE = "DROP TABLE IF EXISTS"
        private val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS"
        private val ALTER_TABLE = "ALTER TABLE"
        private val ADD_COLUMN = "ADD COLUMN"
    }
}

