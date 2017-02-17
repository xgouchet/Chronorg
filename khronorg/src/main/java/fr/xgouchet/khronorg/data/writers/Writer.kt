package fr.xgouchet.khronorg.data.writers

import android.content.ContentValues

/**
 * @author Xavier F. Gouchet
 */
interface Writer<in T> {

    fun toContentValues(data: T): ContentValues

    fun fillContentValues(cv: ContentValues, data: T)
}