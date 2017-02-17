package fr.xgouchet.khronorg.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.support.annotation.VisibleForTesting
import fr.xgouchet.khronorg.provider.dao.BaseDao
import fr.xgouchet.khronorg.provider.db.SQLiteDescriptionHelper
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier Gouchet
 */
class KhronorgContentProvider(val khronorgSchema: KhronorgSchema = KhronorgSchema()) : ContentProvider() {

    private val uriMatcher: UriMatcher = khronorgSchema.buildUriMatcher()

    private var projectDao: BaseDao by notNull<BaseDao>()
    private var entityDao: BaseDao by notNull<BaseDao>()
    private var jumpDao: BaseDao by notNull<BaseDao>()
    private var eventDao: BaseDao by notNull<BaseDao>()
    private var portalDao: BaseDao by notNull<BaseDao>()
    private var timelineDao: BaseDao by notNull<BaseDao>()


    @VisibleForTesting
    internal constructor(khronorgSchema: KhronorgSchema,
                         projectDao: BaseDao,
                         entityDao: BaseDao,
                         jumpDao: BaseDao,
                         eventDao: BaseDao,
                         portalDao: BaseDao) : this(khronorgSchema) {
        this.projectDao = projectDao
        this.entityDao = entityDao
        this.jumpDao = jumpDao
        this.eventDao = eventDao
        this.portalDao = portalDao
    }

    override fun onCreate(): Boolean {
        val context = context ?: return false

        val databaseHelper = SQLiteDescriptionHelper(context, khronorgSchema, null)

        projectDao = BaseDao(databaseHelper, KhronorgSchema.TABLE_PROJECTS)
        entityDao = BaseDao(databaseHelper, KhronorgSchema.TABLE_TRAVELLERS)
        jumpDao = BaseDao(databaseHelper, KhronorgSchema.TABLE_JUMPS)
        eventDao = BaseDao(databaseHelper, KhronorgSchema.TABLE_EVENTS)
        portalDao = BaseDao(databaseHelper, KhronorgSchema.TABLE_PORTALS)
        timelineDao = BaseDao(databaseHelper, KhronorgSchema.TABLE_TIMELINES)

        return true
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun query(uri: Uri,
                       projection: Array<String>?,
                       selection: String?,
                       selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? {
        var result: Cursor? = null
        val dao = getDao(uri)

        if (dao != null) {
            result = dao.query(projection, selection, selectionArgs, sortOrder)
        }

        return result
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val match = uriMatcher.match(uri)
        var result: Uri? = null

        if (values != null) {
            when (match) {
                KhronorgSchema.MATCH_PROJECTS -> {
                    val projectId = projectDao.insert(values)
                    result = khronorgSchema.projectUri(projectId)
                }
                KhronorgSchema.MATCH_TRAVELLERS -> {
                    val entityId = entityDao.insert(values)
                    result = khronorgSchema.entityUri(entityId)
                }
                KhronorgSchema.MATCH_JUMPS -> {
                    val jumpId = jumpDao.insert(values)
                    result = khronorgSchema.jumpUri(jumpId)
                }
                KhronorgSchema.MATCH_EVENTS -> {
                    val eventId = eventDao.insert(values)
                    result = khronorgSchema.eventUri(eventId)
                }
                KhronorgSchema.MATCH_PORTALS -> {
                    val portalId = portalDao.insert(values)
                    result = khronorgSchema.portalUri(portalId)
                }
            }
        }

        return result
    }

    override fun delete(uri: Uri,
                        selection: String?,
                        selectionArgs: Array<String>?): Int {

        var deleted = 0
        val match = uriMatcher.match(uri)


        when (match) {
            KhronorgSchema.MATCH_PROJECTS -> deleted = deleteProtectsRecursive(selection, selectionArgs)
            KhronorgSchema.MATCH_TRAVELLERS -> deleted = deleteEntitiesRecursive(selection, selectionArgs)
            else -> {
                val dao = getDao(uri)
                if (dao != null) {
                    deleted = dao.delete(selection, selectionArgs)
                }
            }
        }

        return deleted
    }

    private fun deleteProtectsRecursive(selection: String?, selectionArgs: Array<String>?): Int {
        val selectProjectArgs = arrayOf("")
        val cursor = projectDao.query(arrayOf(KhronorgSchema.COL_ID), selection, selectionArgs, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            selectProjectArgs[0] = Integer.toString(id)
            deleteEntitiesRecursive(SELECT_BY_PROJECT_ID, selectProjectArgs)
            eventDao.delete(SELECT_BY_PROJECT_ID, selectProjectArgs)
            portalDao.delete(SELECT_BY_PROJECT_ID, selectProjectArgs)
            timelineDao.delete(SELECT_BY_PROJECT_ID, selectProjectArgs)
        }
        cursor.close()

        return projectDao.delete(selection, selectionArgs)
    }


    private fun deleteEntitiesRecursive(selection: String?, selectionArgs: Array<String>?): Int {
        val cursor = entityDao.query(arrayOf(KhronorgSchema.COL_ID), selection, selectionArgs, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            jumpDao.delete(SELECT_BY_ENTITY_ID, arrayOf(Integer.toString(id)))
        }
        cursor.close()

        return entityDao.delete(selection, selectionArgs)
    }

    override fun update(uri: Uri,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<String>?): Int {
        var updated = 0
        val dao = getDao(uri)

        if (dao != null) {
            updated = dao.update(values, selection, selectionArgs)
        }

        return updated
    }


    private fun getDao(uri: Uri): BaseDao? {
        val match = uriMatcher.match(uri)

        when (match) {
            KhronorgSchema.MATCH_PROJECTS -> return projectDao
            KhronorgSchema.MATCH_TRAVELLERS -> return entityDao
            KhronorgSchema.MATCH_JUMPS -> return jumpDao
            KhronorgSchema.MATCH_EVENTS -> return eventDao
            KhronorgSchema.MATCH_PORTALS -> return portalDao
            KhronorgSchema.MATCH_TIMELINES -> return timelineDao
            else -> return null
        }
    }

    companion object {

        val SELECT_BY_PROJECT_ID = KhronorgSchema.COL_PROJECT_ID + "=?"
        val SELECT_BY_ENTITY_ID = KhronorgSchema.COL_ENTITY_ID + "=?"
    }


}
