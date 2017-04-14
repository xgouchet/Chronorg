package fr.xgouchet.khronorg.provider

import android.content.UriMatcher
import android.net.Uri
import fr.xgouchet.khronorg.BuildConfig
import fr.xgouchet.khronorg.provider.db.*

/**
 * @author Xavier Gouchet
 */
class KhronorgSchema : SQLiteDescriptionProvider {

    override val description: SQLiteDescription
        get() {
            val description = SQLiteDescription(DB_NAME, DB_VERSION)

            description.addTable(buildProjectsTable())
            description.addTable(buildTravellersTable())
            description.addTable(buildPortalsTable())
            description.addTable(buildEventsTable())
            description.addTable(buildJumpsTable())
            description.addTable(buildTimelinesTable())

            return description
        }

    private fun buildProjectsTable(): TableDescription {
        val tableDescription = TableDescription(TABLE_PROJECTS)

        tableDescription.addColumn(ColumnDescription(COL_ID, ColumnType.TYPE_INTEGER, ColumnOption.PRIMARY_KEY, ColumnOption.AUTOINCREMENT))
        tableDescription.addColumn(ColumnDescription(COL_NAME, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL, ColumnOption.UNIQUE))
        tableDescription.addColumn(ColumnDescription(COL_RANGE_MIN_INSTANT, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_RANGE_MAX_INSTANT, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))

        return tableDescription
    }

    private fun buildTravellersTable(): TableDescription {
        val tableDescription = TableDescription(TABLE_TRAVELLERS)

        tableDescription.addColumn(ColumnDescription(COL_ID, ColumnType.TYPE_INTEGER, ColumnOption.PRIMARY_KEY, ColumnOption.AUTOINCREMENT))
        tableDescription.addColumn(ColumnDescription(COL_PROJECT_ID, ColumnType.TYPE_INTEGER, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_NAME, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_BIRTH_INSTANT, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_DEATH_INSTANT, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_COLOR, ColumnType.TYPE_INTEGER))

        return tableDescription
    }

    private fun buildPortalsTable(): TableDescription {
        val tableDescription = TableDescription(TABLE_PORTALS)

        tableDescription.addColumn(ColumnDescription(COL_ID, ColumnType.TYPE_INTEGER, ColumnOption.PRIMARY_KEY, ColumnOption.AUTOINCREMENT))
        tableDescription.addColumn(ColumnDescription(COL_PROJECT_ID, ColumnType.TYPE_INTEGER, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_NAME, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_DELAY, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_DIRECTION, ColumnType.TYPE_INTEGER))
        tableDescription.addColumn(ColumnDescription(COL_COLOR, ColumnType.TYPE_INTEGER))

        return tableDescription
    }

    private fun buildEventsTable(): TableDescription {
        val tableDescription = TableDescription(TABLE_EVENTS)

        tableDescription.addColumn(ColumnDescription(COL_ID, ColumnType.TYPE_INTEGER, ColumnOption.PRIMARY_KEY, ColumnOption.AUTOINCREMENT))
        tableDescription.addColumn(ColumnDescription(COL_PROJECT_ID, ColumnType.TYPE_INTEGER, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_NAME, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_INSTANT, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_COLOR, ColumnType.TYPE_INTEGER))

        return tableDescription
    }

    private fun buildJumpsTable(): TableDescription {
        val tableDescription = TableDescription(TABLE_JUMPS)

        tableDescription.addColumn(ColumnDescription(COL_ID, ColumnType.TYPE_INTEGER, ColumnOption.PRIMARY_KEY, ColumnOption.AUTOINCREMENT))
        tableDescription.addColumn(ColumnDescription(COL_TRAVELLER_ID, ColumnType.TYPE_INTEGER, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_FROM_INSTANT, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_DELAY, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_DIRECTION, ColumnType.TYPE_INTEGER))
        tableDescription.addColumn(ColumnDescription(COL_ORDER, ColumnType.TYPE_INTEGER))

        return tableDescription
    }

    private fun buildTimelinesTable(): TableDescription {
        val tableDescription = TableDescription(TABLE_TIMELINES)

        tableDescription.addColumn(ColumnDescription(COL_ID, ColumnType.TYPE_INTEGER, ColumnOption.PRIMARY_KEY, ColumnOption.AUTOINCREMENT))
        tableDescription.addColumn(ColumnDescription(COL_PROJECT_ID, ColumnType.TYPE_INTEGER, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_NAME, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_PARENT_ID, ColumnType.TYPE_INTEGER))
        tableDescription.addColumn(ColumnDescription(COL_DELAY, ColumnType.TYPE_TEXT, ColumnOption.NOT_NULL))
        tableDescription.addColumn(ColumnDescription(COL_DIRECTION, ColumnType.TYPE_INTEGER))

        return tableDescription
    }

    fun buildUriMatcher(): UriMatcher {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        uriMatcher.addURI(AUTHORITY, PATH_PROJECTS, MATCH_PROJECTS)
        uriMatcher.addURI(AUTHORITY, PATH_TRAVELLERS, MATCH_TRAVELLERS)
        uriMatcher.addURI(AUTHORITY, PATH_JUMPS, MATCH_JUMPS)
        uriMatcher.addURI(AUTHORITY, PATH_EVENTS, MATCH_EVENTS)
        uriMatcher.addURI(AUTHORITY, PATH_PORTALS, MATCH_PORTALS)
        uriMatcher.addURI(AUTHORITY, PATH_TIMELINES, MATCH_TIMELINES)

        return uriMatcher
    }

    fun projectUri(projectId: Long): Uri {
        return buildUriWithId(PROJECTS_URI, projectId)
    }

    fun entityUri(entityId: Long): Uri {
        return buildUriWithId(TRAVELLERS_URI, entityId)
    }

    fun jumpUri(jumpId: Long): Uri {
        return buildUriWithId(JUMPS_URI, jumpId)
    }


    fun eventUri(eventId: Long): Uri {
        return buildUriWithId(EVENTS_URI, eventId)
    }


    fun portalUri(portalId: Long): Uri {
        return buildUriWithId(PORTALS_URI, portalId)
    }

    fun timelineUri(timelineId: Long): Uri {
        return buildUriWithId(TIMELINES_URI, timelineId)
    }

    private fun buildUriWithId(baseUri: Uri, projectId: Long): Uri {
        return baseUri.buildUpon().appendEncodedPath(java.lang.Long.toString(projectId)).build()
    }

    companion object {


        val VERSION_BASE = 1

        val DB_VERSION = VERSION_BASE
        val DB_NAME = "khronorg"

        val TABLE_PROJECTS = "projects"
        val TABLE_TRAVELLERS = "travellers"
        val TABLE_PORTALS = "portals"
        val TABLE_TIMELINES = "timelines"
        val TABLE_JUMPS = "jumps"
        val TABLE_EVENTS = "events"

        val COL_ID = "id"
        val COL_NAME = "name"

        val COL_FROM_INSTANT = "from_instant"
        val COL_RANGE_MIN_INSTANT = "range_min"
        val COL_RANGE_MAX_INSTANT = "range_max"
        val COL_BIRTH_INSTANT = "birth"
        val COL_DEATH_INSTANT = "death"
        val COL_INSTANT = "instant"
        val COL_DIRECTION = "direction"
        val COL_COLOR = "color"
        val COL_DELAY = "delay"
        val COL_ORDER = "jump_order"

        val COL_PARENT_ID = "parent_id"
        val COL_PROJECT_ID = "project_id"
        val COL_TRAVELLER_ID = "traveller_id"

        // URI Matcher
        val AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"


        private val PATH_PROJECTS = "projects"
        private val PATH_TRAVELLERS = "travellers"
        private val PATH_JUMPS = "jumps"
        private val PATH_EVENTS = "events"
        private val PATH_PORTALS = "portals"
        private val PATH_TIMELINES = "timelines"

        val MATCH_PROJECTS = 10
        val MATCH_TRAVELLERS = 20
        val MATCH_JUMPS = 30
        val MATCH_EVENTS = 40
        val MATCH_PORTALS = 50
        val MATCH_TIMELINES = 60

        val BASE_URI: Uri = Uri.parse("content://" + AUTHORITY)
        val PROJECTS_URI: Uri = BASE_URI.buildUpon().appendPath(PATH_PROJECTS).build()
        val TRAVELLERS_URI: Uri = BASE_URI.buildUpon().appendPath(PATH_TRAVELLERS).build()
        val JUMPS_URI: Uri = BASE_URI.buildUpon().appendPath(PATH_JUMPS).build()
        val EVENTS_URI: Uri = BASE_URI.buildUpon().appendPath(PATH_EVENTS).build()
        val PORTALS_URI: Uri = BASE_URI.buildUpon().appendPath(PATH_PORTALS).build()
        val TIMELINES_URI: Uri = BASE_URI.buildUpon().appendPath(PATH_TIMELINES).build()
    }
}
