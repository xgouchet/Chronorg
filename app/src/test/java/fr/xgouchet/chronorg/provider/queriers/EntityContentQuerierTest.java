package fr.xgouchet.chronorg.provider.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import fr.xgouchet.chronorg.provider.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.provider.readers.EntityCursorReader;
import fr.xgouchet.chronorg.provider.writers.EntityContentValuesWriter;
import rx.Subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EntityContentQuerierTest {

    @Mock ContentResolver contentResolver;
    @Mock EntityIOProvider provider;
    @Mock Subscriber<Entity> subscriber;
    @Mock EntityCursorReader reader;
    @Mock EntityContentValuesWriter writer;
    @Mock Cursor cursor;
    @Mock ContentValues contentValues;

    private EntityContentQuerier querier;

    @Before
    public void setUp() {
        initMocks(this);
        when(provider.provideReader(any(Cursor.class)))
                .thenReturn(reader);

        when(provider.provideWriter())
                .thenReturn(writer);
        querier = new EntityContentQuerier(provider);
    }


    @Test
    public void shouldQueryAll() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        final Entity mock1 = mock(Entity.class);
        final Entity mock2 = mock(Entity.class);
        when(reader.instantiateAndFill()).thenReturn(mock1, mock2, null);


        // When
        querier.queryAll(contentResolver, subscriber);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), isNull(String.class), isNull(String[].class), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(subscriber).onNext(mock1);
        verify(subscriber).onNext(mock2);
        verify(cursor).close();
        verifyNoMoreInteractions(subscriber);
    }

    @Test
    public void shouldQueryAllWithException() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryAll(contentResolver, subscriber);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), isNull(String.class), isNull(String[].class), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(subscriber);
    }

    @Test
    public void shouldQuery() {
        // Given
        int entityId = 42;
        Entity mock1 = mock(Entity.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Entity) null);


        // When
        querier.query(contentResolver, subscriber, entityId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(subscriber).onNext(mock1);
        verify(cursor).close();
        verifyNoMoreInteractions(subscriber);
    }

    @Test
    public void shouldQueryWithException() {
        // Given
        int entityId = 42;
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.query(contentResolver, subscriber, entityId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(subscriber);
    }


    @Test
    public void shouldQueryInProject() {
        // Given
        int projectId = 42;
        Entity mock1 = mock(Entity.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Entity) null);


        // When
        querier.queryInProject(contentResolver, subscriber, projectId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("project_id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(subscriber).onNext(mock1);
        verify(cursor).close();
        verifyNoMoreInteractions(subscriber);
    }

    @Test
    public void shouldQueryInProjectWithException() {
        // Given
        int projectId = 42;
        Entity mock1 = mock(Entity.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryInProject(contentResolver, subscriber, projectId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("project_id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(subscriber);
    }

    @Test
    public void shouldSaveNewEntity() {
        // Given
        Uri projectUri = mock(Uri.class);
        Entity project = mock(Entity.class);
        when(project.getId()).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(projectUri);
        when(writer.toContentValues(any(Entity.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).insert(eq(ChronorgSchema.ENTITIES_URI), same(contentValues));
    }

    @Test
    public void shouldSaveNewEntityFail() {
        // Given
        Entity project = mock(Entity.class);
        when(project.getId()).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(null);
        when(writer.toContentValues(any(Entity.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).insert(eq(ChronorgSchema.ENTITIES_URI), same(contentValues));
    }

    @Test
    public void shouldSaveExistingEntity() {
        // Given
        Entity project = mock(Entity.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(1);
        when(writer.toContentValues(any(Entity.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).update(eq(ChronorgSchema.ENTITIES_URI), same(contentValues), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldSaveExistingEntityFail() {
        // Given
        Entity project = mock(Entity.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(0);
        when(writer.toContentValues(any(Entity.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).update(eq(ChronorgSchema.ENTITIES_URI), same(contentValues), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldDeleteEntity() {
        // Given
        Entity project = mock(Entity.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        boolean success = querier.delete(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).delete(eq(ChronorgSchema.ENTITIES_URI), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldDeleteExistingEntityFail() {
        // Given
        Entity project = mock(Entity.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(0);

        // When
        boolean success = querier.delete(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).delete(eq(ChronorgSchema.ENTITIES_URI), eq("id=?"), eq(new String[]{"42"}));
    }

}