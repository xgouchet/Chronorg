package fr.xgouchet.khronorg.commons.readers

import android.database.Cursor

/**
 * @author Xavier Gouchet
 */
abstract class BaseReader<T> (val cursor: Cursor) : Reader<T> {

    abstract override fun instantiate(): T

    abstract override fun fill(data: T)

    override fun instantiateAndFill(): T {
        val data = instantiate()
        fill(data)
        return data
    }

    protected fun getIndex(columnName: String): Int {
        return cursor.getColumnIndex(columnName)
    }

    protected fun readInt(columnIndex: Int): Int {
        return cursor.getInt(columnIndex)
    }

    protected fun readString(columnIndex: Int): String {
        return cursor.getString(columnIndex)
    }
}
