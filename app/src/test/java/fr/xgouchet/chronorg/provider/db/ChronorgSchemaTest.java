package fr.xgouchet.chronorg.provider.db;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ChronorgSchemaTest {

    @Mock SQLiteDatabase database;
    ChronorgSchema schema;

    @Before
    public void setUp() {
        initMocks(this);
        schema = new ChronorgSchema();
    }

    @Test
    public void shouldBuildDescription() {

        // When
        SQLiteDescription description = schema.getDescription();

        // Then
        assertThat(description.getName()).isEqualTo(ChronorgSchema.NAME);
        assertThat(description.getVersion()).isEqualTo(ChronorgSchema.CURRENT_VERSION);
    }

    @Test
    public void shouldCreateDatabase() {

        // When
        SQLiteDescription description = schema.getDescription();
        description.createDatabase(database);

        // Then
        verify(database).beginTransaction();
        verify(database).execSQL("CREATE TABLE IF NOT EXISTS projects (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL UNIQUE)");
        verify(database).execSQL("CREATE TABLE IF NOT EXISTS entities (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "project_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "birth TEXT NOT NULL," +
                "death TEXT," +
                "color INTEGER)");
        verify(database).execSQL("CREATE TABLE IF NOT EXISTS portals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "project_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "delay INTEGER NOT NULL," +
                "two_way INTEGER," +
                "timeline INTEGER," +
                "color INTEGER)");
        verify(database).execSQL("CREATE TABLE IF NOT EXISTS events (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "project_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "instant TEXT NOT NULL," +
                "color INTEGER)");
        verify(database).execSQL("CREATE TABLE IF NOT EXISTS jumps (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "entity_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "from_instant TEXT NOT NULL," +
                "to_instant TEXT NOT NULL," +
                "jump_order INTEGER)");
        verify(database).setTransactionSuccessful();
        verify(database).endTransaction();
        verifyNoMoreInteractions(database);
    }


    //  TODO   @Test
    public void shouldUpgradeDatabase() {

        // When
        SQLiteDescription description = schema.getDescription();
        description.upgradeDatabase(database, 1, 2);

        // Then
        verifyNoMoreInteractions(database);
    }

    //  TODO   @Test
    public void shouldDowngradeDatabase() {

        // When
        SQLiteDescription description = schema.getDescription();
        description.downgradeDatabase(database, 2, 1);

        // Then
        verifyNoMoreInteractions(database);
    }

    @Test
    public void shouldBuildUriMatcher() {
        // Given

        // When
        UriMatcher uriMatcher = schema.buildUriMatcher();

        // Then
        assertThat(uriMatcher.match(ChronorgSchema.PROJECTS_URI)).isEqualTo(ChronorgSchema.MATCH_PROJECTS);
        assertThat(uriMatcher.match(ChronorgSchema.ENTITIES_URI)).isEqualTo(ChronorgSchema.MATCH_ENTITIES);
        assertThat(uriMatcher.match(ChronorgSchema.JUMPS_URI)).isEqualTo(ChronorgSchema.MATCH_JUMPS);
        assertThat(uriMatcher.match(ChronorgSchema.EVENTS_URI)).isEqualTo(ChronorgSchema.MATCH_EVENTS);
    }

    @Test
    public void shouldBuildUris() {
        // Given
        int id = 45;

        // When
        Uri entity = schema.entityUri(id);
        Uri project = schema.projectUri(id);
        Uri jump = schema.jumpUri(id);
        Uri event = schema.eventUri(id);

        // Then
        assertThat(entity.toString()).isEqualTo("content://fr.xgouchet.chronorg.debug.provider/entities/45");
        assertThat(project.toString()).isEqualTo("content://fr.xgouchet.chronorg.debug.provider/projects/45");
        assertThat(jump.toString()).isEqualTo("content://fr.xgouchet.chronorg.debug.provider/jumps/45");
        assertThat(event.toString()).isEqualTo("content://fr.xgouchet.chronorg.debug.provider/events/45");
    }
}
