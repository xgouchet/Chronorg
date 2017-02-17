package fr.xgouchet.khronorg.data.writers

import android.content.ContentValues

/**
 * @author Xavier F. Gouchet
 */
abstract class BaseWriter<T> : Writer<T> {

    override fun toContentValues(data: T): ContentValues {
        val cv = ContentValues()
        fillContentValues(cv, data)
        return cv
    }


}
