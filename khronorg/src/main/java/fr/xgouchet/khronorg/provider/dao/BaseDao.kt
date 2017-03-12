package fr.xgouchet.khronorg.provider.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author Xavier Gouchet
 */
class BaseDao(private val openHelper: SQLiteOpenHelper,
              private val tableName: String) {


    fun insert(cv: ContentValues): Long {
        val db = openHelper.writableDatabase

        var insertedId: Long

        db.beginTransaction()
        try {
            insertedId = db.insert(tableName, null, cv)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        return insertedId
    }

    @SuppressLint("Recycle")
    fun query(projection: Array<String>?,
              selection: String?,
              selectionArgs: Array<String>?,
              sortOrder: String?): Cursor {
        val db = openHelper.readableDatabase

        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder)
    }


    fun delete(selection: String?,
               selectionArgs: Array<String>?): Int {
        val db = openHelper.writableDatabase

        var deleted: Int

        db.beginTransaction()
        try {
            deleted = db.delete(tableName, selection, selectionArgs)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        return deleted
    }

    fun update(values: ContentValues?,
               selection: String?,
               selectionArgs: Array<String>?): Int {
        if (values == null) return 0
        var updated: Int

        val db = openHelper.writableDatabase
        db.beginTransaction()
        try {
            updated = db.update(tableName, values, selection, selectionArgs)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        return updated
    }
}
