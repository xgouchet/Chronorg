package fr.xgouchet.khronorg.provider.db


import android.database.sqlite.SQLiteDatabase
import android.support.annotation.IntRange
import java.util.*

/**
 * @author Xavier Gouchet
 */
class SQLiteDescription(val name: String, val version: Int) {

    private val tables = ArrayList<TableDescription>()
    private val tableNames = HashSet<String>()

    fun createDatabase(db: SQLiteDatabase) {
        db.beginTransaction()

        try {
            createAllTables(db)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }


    fun upgradeDatabase(db: SQLiteDatabase,
                        @IntRange(from = 0) oldVersion: Int,
                        @IntRange(from = 0) newVersion: Int) {

        for (version in oldVersion..newVersion - 1) {
            upgradeAllTables(db, version, version + 1)
        }
    }

    fun downgradeDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO handle downgrade
        throw UnsupportedOperationException()
    }

    fun addTable(table: TableDescription) {
        if (tableNames.contains(table.name)) {
            if (!tables.contains(table)) {
                throw IllegalArgumentException("A table with this name already exists")
            }
        } else {
            tables.add(table)
            tableNames.add(table.name)
        }
    }

    private fun createAllTables(db: SQLiteDatabase) {
        for (table in tables) {
            if (table.since <= version && version <= table.until) {
                val createStatement = table.getCreateStatement(version)
                db.execSQL(createStatement)
            }
        }
    }

    private fun upgradeAllTables(db: SQLiteDatabase, fromVersion: Int, toVersion: Int) {
        for (table in tables) {
            if (table.until == fromVersion) {
                db.execSQL(table.dropStatement)
            } else if (table.since == toVersion) {
                db.execSQL(table.getCreateStatement(toVersion))
            } else {
                val upgradeStatement = table.getUpgradeStatement(fromVersion, toVersion)
                if (upgradeStatement != null) {
                    db.execSQL(upgradeStatement)
                }
            }
        }
    }

    companion object {

        internal val PAR_OPEN = '('
        internal val PAR_CLOSE = ')'
        internal val BLANK = ' '
        internal val COMMA = ','
        internal val SEMICOLON = ';'
    }
}
