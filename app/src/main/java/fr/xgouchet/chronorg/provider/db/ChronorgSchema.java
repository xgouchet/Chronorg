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
    public static final String TABLE_JUMPS = "jumps";
    public static final String TABLE_EVENTS = "events";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "desc";

    public static final String COL_FROM_INSTANT = "from_instant";
    public static final String COL_TO_INSTANT = "to_instant";
    public static final String COL_COLOUR = "colour";
    public static final String COL_BIRTH = "birth";
    public static final String COL_DEATH = "death";
    private static final String COL_DELAY = "delay";
    public static final String COL_INSTANT = "instant";

    public static final String COL_PROJECT_ID = "project_id";
    public static final String COL_ENTITY_ID = "entity_id";


    @NonNull
    public SQLiteDescription getDescription() {
        SQLiteDescription description = new SQLiteDescription(NAME, CURRENT_VERSION);

        description.addTable(buildProjectsTable());
        description.addTable(buildEntitiesTable());
        description.addTable(buildPortalsTable());
        description.addTable(buildEventsTable());
        description.addTable(buildJumpsTable());

        return description;
    }

    private TableDescription buildProjectsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_PROJECTS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL, UNIQUE));
        tableDescription.addColumn(new ColumnDescription(COL_DESCRIPTION, TYPE_TEXT));

        return tableDescription;
    }

    private TableDescription buildEntitiesTable() {
        TableDescription tableDescription = new TableDescription(TABLE_ENTITIES);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL, UNIQUE));
        tableDescription.addColumn(new ColumnDescription(COL_DESCRIPTION, TYPE_TEXT));
        tableDescription.addColumn(new ColumnDescription(COL_BIRTH, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_DEATH, TYPE_TEXT));
        tableDescription.addColumn(new ColumnDescription(COL_COLOUR, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildPortalsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_PORTALS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL, UNIQUE));
        tableDescription.addColumn(new ColumnDescription(COL_DESCRIPTION, TYPE_TEXT));
        tableDescription.addColumn(new ColumnDescription(COL_DELAY, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_COLOUR, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildEventsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_EVENTS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_PROJECT_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL, UNIQUE));
        tableDescription.addColumn(new ColumnDescription(COL_DESCRIPTION, TYPE_TEXT));
        tableDescription.addColumn(new ColumnDescription(COL_INSTANT, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_COLOUR, TYPE_INTEGER));

        return tableDescription;
    }

    private TableDescription buildJumpsTable() {
        TableDescription tableDescription = new TableDescription(TABLE_JUMPS);

        tableDescription.addColumn(new ColumnDescription(COL_ID, TYPE_INTEGER, PRIMARY_KEY, AUTOINCREMENT));
        tableDescription.addColumn(new ColumnDescription(COL_ENTITY_ID, TYPE_INTEGER, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_NAME, TYPE_TEXT, NOT_NULL, UNIQUE));
        tableDescription.addColumn(new ColumnDescription(COL_DESCRIPTION, TYPE_TEXT));
        tableDescription.addColumn(new ColumnDescription(COL_FROM_INSTANT, TYPE_TEXT, NOT_NULL));
        tableDescription.addColumn(new ColumnDescription(COL_TO_INSTANT, TYPE_TEXT, NOT_NULL));

        return tableDescription;
    }


    // URI Matcher
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    private static final String PATH_PROJECTS = "projects";
    private static final String PATH_ENTITIES = "entities";

    public static final int MATCH_PROJECTS = 100;

    public static final int MATCH_ENTITIES = 200;

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri PROJECTS_URI = BASE_URI.buildUpon().appendPath(PATH_PROJECTS).build();
    public static final Uri ENTITIES_URI = BASE_URI.buildUpon().appendPath(PATH_ENTITIES).build();

    public UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PATH_PROJECTS, MATCH_PROJECTS);

        uriMatcher.addURI(AUTHORITY, PATH_ENTITIES, MATCH_ENTITIES);

        return uriMatcher;
    }

    public Uri projectUri(long projectId) {
        return PROJECTS_URI.buildUpon().appendEncodedPath(Long.toString(projectId)).build();
    }


    public Uri entityUri(long entityId) {
        return ENTITIES_URI.buildUpon().appendEncodedPath(Long.toString(entityId)).build();
    }
}
