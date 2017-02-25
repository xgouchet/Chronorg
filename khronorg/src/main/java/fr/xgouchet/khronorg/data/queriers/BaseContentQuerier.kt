package fr.xgouchet.khronorg.data.queriers

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import com.deezer.android.counsel.annotations.Trace
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.query.QueryAlteration
import fr.xgouchet.khronorg.provider.KhronorgSchema
import io.reactivex.functions.Consumer

/**
 * @author Xavier Gouchet
 */
@Trace
abstract class BaseContentQuerier<T> protected constructor(protected val ioProvider: IOProvider<T>) : ContentQuerier<T> {

    abstract val uri: Uri

    override fun queryAll(contentResolver: ContentResolver,
                          action: Consumer<T>) {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(uri,
                    null,
                    null,
                    null,
                    null)

            readRows(action, cursor)
        } finally {
            if (cursor != null) cursor.close()
        }
    }

    override fun queryWhere(contentResolver: ContentResolver, alter: QueryAlteration, action: Consumer<T>) {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(uri,
                    null,
                    alter.select(),
                    alter.args(),
                    alter.order())

            readRows(action, cursor)
        } finally {
            if (cursor != null) cursor.close()
        }
    }

    fun query(contentResolver: ContentResolver, action: Consumer<T>, id: Int) {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(uri,
                    null,
                    selectById(),
                    arrayOf(Integer.toString(id)),
                    null)

            readRows(action, cursor)
        } finally {
            if (cursor != null) cursor.close()
        }
    }

    override fun save(contentResolver: ContentResolver, item: T): Boolean {
        val cv = ioProvider.writer.toContentValues(item)

        val id = getId(item)
        if (id <= 0) {
            val result = contentResolver.insert(uri, cv)
            return result != null
        } else {
            val updated = contentResolver.update(uri,
                    cv,
                    selectById(),
                    arrayOf(Integer.toString(id)))
            return updated > 0
        }
    }

    override fun delete(contentResolver: ContentResolver, item: T): Boolean {
        val deleted = contentResolver.delete(
                uri,
                selectById(),
                arrayOf(Integer.toString(getId(item))))
        return deleted != 0
    }

    private fun readRows(action: Consumer<T>,
                         cursor: Cursor?) {
        if (cursor != null && cursor.count > 0) {
            val reader = ioProvider.provideReader(cursor)
            while (cursor.moveToNext()) {
                action.accept(reader.instantiateAndFill())
            }
        }
    }

    private fun selectById(): String? {
        return KhronorgSchema.COL_ID + "=?"
    }


    abstract fun getId(item: T): Int
}
