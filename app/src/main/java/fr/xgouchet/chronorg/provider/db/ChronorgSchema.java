package fr.xgouchet.chronorg.provider.db;

import android.content.UriMatcher;
import android.net.Uri;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.BuildConfig;

import static fr.xgouchet.chronorg.provider.db.ColumnDescription.AUTOINCREMENT;
import static fr.xgouchet.chronorg.provider.db.ColumnDescription.NOT_NULL;
import static fr.xgouchet.chronorg.provider.db.ColumnDescription.PRIMARY_KEY;
import static fr.xgouchet.chronorg.provider.db.ColumnDescription.TYPE_INTEGER;
import static fr.xgouchet.chronorg.provider.db.ColumnDescription.TYPE_TEXT;
import static fr.xgouchet.chronorg.provider.db.ColumnDescription.UNIQUE;


/**
 * @author Xavier Gouchet
 */
public class ChronorgSchema implements SQLiteDescriptionProvider {


    public static final int VERSION_BASE = 1;

    public static final int CURRENT_VERSION = VERSION_BASE;

    public static final String NAME = "chronorg";

    public static final String TABLE_PROJECTS = "projects";
    public static final String TABLE_ENTITIES = "entities";
    public static final String TABLE_PORTALS = "portals";
    public static final String TABLE_TIMELINES = "timelines";
    public static final String TABLE_JUMPS = "jumps";
    public static final String TABLE_EVENTS = "events";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";

    public static final String COL_FROM_INSTANT = "from_instant";
    public static final String COL_TO_INSTANT = "to_instant";
    public static final String COL_BIRTH_INSTANT = "birth";
    public static final String COL_DEATH_INSTANT = "death";
    public static final String COL_INSTANT = "instant";
    public static final String COL_DIRECTION = "direction";
    public static final String COL_COLOR = "color";
    public static final String COL_DELAY = "delay";
    public static final String COL_ORDER = "jump_order";

    public static final String COL_PARENT_ID = "parent_id";
    public static final String COL_PROJECT_ID = "project_id";
    public static final String COL_ENTITY_ID = "entity_id";
    public static final String COL_PORTAL_ID = "portal_id";

    @NonNull
    public SQLiteDescription getDescription() {
        SQLiteDescription description = new SQLiteDescription(NAME, CURRENT_VERSION);

        description.addTable(buildProjectsTable());
        description.addTable(buildEntitiesTable());
        description.addTable(buildPortalsTable());
        description.addTable(buildEventsTable());
        description.addTable(buildJumpsTable());
        description.addTable(buildTimelinesTable());

        return description;
    }

    private TableDescription buildProjectsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_PROJECTS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL, UNIQUE));

        return tableDescription;
    }

    private TableDescription buildEntitiesTable() {
        TableDescription tableDescription = new TableDescription(TABLE_ENTITIES);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_BIRTH_INSTANT, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_DEATH_INSTANT, TYPE_TEXT));
        tableDescription.addColumn(new ColumnDescription(COL_COLOR, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildPortalsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_PORTALS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_DELAY, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_DIRECTION, TYPE_INTEGER));
        tableDescription.addColumn(new ColumnDescription(COL_COLOR, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildEventsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_EVENTS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_INSTANT, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_COLOR, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildJumpsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_JUMPS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_ENTITY_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_FROM_INSTANT, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_TO_INSTANT, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_ORDER, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildTimelinesTable() {
        TableDescription tableDescription = new TableDescription(TABLE_TIMELINES);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_PARENT_ID, TYPE_INTEGER));
        tableDescription.addColumn(new ColumnDescription(COL_PORTAL_ID, TYPE_INTEGER));
        tableDescription.addColumn(new ColumnDescription(COL_DIRECTION, TYPE_INTEGER));

        return tableDescription;
    }

    // URI Matcher
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";


    private static final String PATH_PROJECTS = "projects";
    private static final String PATH_ENTITIES = "entities";
    private static final String PATH_JUMPS = "jumps";
    private static final String PATH_EVENTS = "events";
    private static final String PATH_PORTALS = "portals";
    private static final String PATH_TIMELINES = "timelines";

    public static final int MATCH_PROJECTS = 10;
    public static final int MATCH_ENTITIES = 20;
    public static final int MATCH_JUMPS = 30;
    public static final int MATCH_EVENTS = 40;
    public static final int MATCH_PORTALS = 50;
    public static final int MATCH_TIMELINES = 60;

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri PROJECTS_URI = BASE_URI.buildUpon().appendPath(PATH_PROJECTS).build();
    public static final Uri ENTITIES_URI = BASE_URI.buildUpon().appendPath(PATH_ENTITIES).build();
    public static final Uri JUMPS_URI = BASE_URI.buildUpon().appendPath(PATH_JUMPS).build();
    public static final Uri EVENTS_URI = BASE_URI.buildUpon().appendPath(PATH_EVENTS).build();
    public static final Uri PORTALS_URI = BASE_URI.buildUpon().appendPath(PATH_PORTALS).build();
    public static final Uri TIMELINES_URI = BASE_URI.buildUpon().appendPath(PATH_TIMELINES).build();

    public UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PATH_PROJECTS, MATCH_PROJECTS);
        uriMatcher.addURI(AUTHORITY, PATH_ENTITIES, MATCH_ENTITIES);
        uriMatcher.addURI(AUTHORITY, PATH_JUMPS, MATCH_JUMPS);
        uriMatcher.addURI(AUTHORITY, PATH_EVENTS, MATCH_EVENTS);
        uriMatcher.addURI(AUTHORITY, PATH_PORTALS, MATCH_PORTALS);
        uriMatcher.addURI(AUTHORITY, PATH_TIMELINES, MATCH_TIMELINES);

        return uriMatcher;
    }

    public Uri projectUri(long projectId) {
        return buildUriWithId(PROJECTS_URI, projectId);
    }

    public Uri entityUri(long entityId) {
        return buildUriWithId(ENTITIES_URI, entityId);
    }

    public Uri jumpUri(long jumpId) {
        return buildUriWithId(JUMPS_URI, jumpId);
    }


    public Uri eventUri(long eventId) {
        return buildUriWithId(EVENTS_URI, eventId);
    }


    public Uri portalUri(long portalId) {
        return buildUriWithId(PORTALS_URI, portalId);
    }

    public Uri timelineUri(long timelineId) {
        return buildUriWithId(TIMELINES_URI, timelineId);
    }

    private Uri buildUriWithId(@NonNull Uri baseUri, long projectId) {
        return baseUri.buildUpon().appendEncodedPath(Long.toString(projectId)).build();
    }
}
