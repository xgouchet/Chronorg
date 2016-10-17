package fr.xgouchet.chronorg.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
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
import fr.xgouchet.chronorg.provider.dao.BaseDao;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ChronorgContentProviderTest {

    public static final int UNKNOWN_MATCH = 666;
    @Mock ChronorgSchema schema;
    @Mock BaseDao projectDao;
    @Mock BaseDao entityDao;
    @Mock BaseDao jumpDao;
    @Mock BaseDao eventDao;

    @Mock UriMatcher uriMatcher;
    @Mock Cursor cursor;
    @Mock ContentValues values;

    ChronorgContentProvider provider;

    @Before
    public void setUp() {
        initMocks(this);
        when(schema.buildUriMatcher()).thenReturn(uriMatcher);

        provider = new ChronorgContentProvider(schema, projectDao, entityDao, jumpDao, eventDao);
    }

    @Test
    public void shouldQueryProjects() {
        // Given
        Uri uri = mock(Uri.class);
        String[] projection = new String[]{};
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        String order = "bar";
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_PROJECTS);
        when(projectDao.query(any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);

        // When
        Cursor result = provider.query(uri, projection, selection, selectionArgs, order);

        // Then
        assertThat(result).isSameAs(cursor);
        verify(uriMatcher).match(uri);
        verify(projectDao).query(same(projection), same(selection), same(selectionArgs), same(order));
        verifyZeroInteractions(entityDao, jumpDao, eventDao);

    }


    @Test
    public void shouldQueryEntities() {
        // Given
        Uri uri = mock(Uri.class);
        String[] projection = new String[]{};
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        String order = "bar";
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_ENTITIES);
        when(entityDao.query(any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);

        // When
        Cursor result = provider.query(uri, projection, selection, selectionArgs, order);

        // Then
        assertThat(result).isSameAs(cursor);
        verify(uriMatcher).match(uri);
        verify(entityDao).query(same(projection), same(selection), same(selectionArgs), same(order));
        verifyZeroInteractions(projectDao, jumpDao, eventDao);
    }

    @Test
    public void shouldQueryJumps() {
        // Given
        Uri uri = mock(Uri.class);
        String[] projection = new String[]{};
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        String order = "bar";
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_JUMPS);
        when(jumpDao.query(any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);

        // When
        Cursor result = provider.query(uri, projection, selection, selectionArgs, order);

        // Then
        assertThat(result).isSameAs(cursor);
        verify(uriMatcher).match(uri);
        verify(jumpDao).query(same(projection), same(selection), same(selectionArgs), same(order));
        verifyZeroInteractions(projectDao, entityDao, eventDao);
    }

    @Test
    public void shouldQueryEvents() {
        // Given
        Uri uri = mock(Uri.class);
        String[] projection = new String[]{};
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        String order = "bar";
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_EVENTS);
        when(eventDao.query(any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);

        // When
        Cursor result = provider.query(uri, projection, selection, selectionArgs, order);

        // Then
        assertThat(result).isSameAs(cursor);
        verify(uriMatcher).match(uri);
        verify(eventDao).query(same(projection), same(selection), same(selectionArgs), same(order));
        verifyZeroInteractions(projectDao, entityDao, jumpDao);
    }

    @Test
    public void shouldQueryUnknown() {
        // Given
        Uri uri = mock(Uri.class);
        String[] projection = new String[]{};
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        String order = "bar";
        when(uriMatcher.match(any(Uri.class))).thenReturn(UNKNOWN_MATCH);

        // When
        Cursor result = provider.query(uri, projection, selection, selectionArgs, order);

        // Then
        assertThat(result).isNull();
        verify(uriMatcher).match(uri);
        verifyZeroInteractions(projectDao, entityDao, jumpDao, eventDao);
    }

    @Test
    public void shouldInsertProject() {
        // Given
        long insertedId = 42L;
        Uri uri = mock(Uri.class);
        Uri resultUri = mock(Uri.class);
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_PROJECTS);
        when(projectDao.insert(any(ContentValues.class)))
                .thenReturn(insertedId);
        when(schema.projectUri(anyLong())).thenReturn(resultUri);

        // When
        Uri result = provider.insert(uri, values);

        // Then
        assertThat(result).isSameAs(resultUri);
        verify(uriMatcher).match(uri);
        verify(projectDao).insert(same(values));
        verify(schema).projectUri(insertedId);
        verifyZeroInteractions(entityDao, jumpDao, eventDao);
    }

    @Test
    public void shouldInsertEntity() {
        // Given
        long insertedId = 42L;
        Uri uri = mock(Uri.class);
        Uri resultUri = mock(Uri.class);
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_ENTITIES);
        when(entityDao.insert(any(ContentValues.class)))
                .thenReturn(insertedId);
        when(schema.entityUri(anyLong())).thenReturn(resultUri);

        // When
        Uri result = provider.insert(uri, values);

        // Then
        assertThat(result).isSameAs(resultUri);
        verify(uriMatcher).match(uri);
        verify(entityDao).insert(same(values));
        verify(schema).entityUri(insertedId);
        verifyZeroInteractions(projectDao, jumpDao, eventDao);
    }

    @Test
    public void shouldInsertJump() {
        // Given
        long insertedId = 42L;
        Uri uri = mock(Uri.class);
        Uri resultUri = mock(Uri.class);
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_JUMPS);
        when(jumpDao.insert(any(ContentValues.class)))
                .thenReturn(insertedId);
        when(schema.jumpUri(anyLong())).thenReturn(resultUri);

        // When
        Uri result = provider.insert(uri, values);

        // Then
        assertThat(result).isSameAs(resultUri);
        verify(uriMatcher).match(uri);
        verify(jumpDao).insert(same(values));
        verify(schema).jumpUri(insertedId);
        verifyZeroInteractions(projectDao, entityDao, eventDao);
    }

    @Test
    public void shouldInsertEvent() {
        // Given
        long insertedId = 42L;
        Uri uri = mock(Uri.class);
        Uri resultUri = mock(Uri.class);
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_EVENTS);
        when(eventDao.insert(any(ContentValues.class)))
                .thenReturn(insertedId);
        when(schema.eventUri(anyLong())).thenReturn(resultUri);

        // When
        Uri result = provider.insert(uri, values);

        // Then
        assertThat(result).isSameAs(resultUri);
        verify(uriMatcher).match(uri);
        verify(eventDao).insert(same(values));
        verify(schema).eventUri(insertedId);
        verifyZeroInteractions(projectDao, entityDao, jumpDao);
    }

    @Test
    public void shouldInsertUnknown() {
        // Given
        Uri uri = mock(Uri.class);
        when(uriMatcher.match(any(Uri.class))).thenReturn(UNKNOWN_MATCH);

        // When
        Uri result = provider.insert(uri, values);

        // Then
        assertThat(result).isNull();
        verify(uriMatcher).match(uri);
        verifyZeroInteractions(projectDao, entityDao, jumpDao, eventDao);
    }

    @Test
    public void shouldUpdateProject() {
        // Given
        int updated = 42;
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_PROJECTS);
        when(projectDao.update(any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(updated);

        // When
        int result = provider.update(uri, values, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(updated);
        verify(uriMatcher).match(uri);
        verify(projectDao).update(same(values), same(selection), same(selectionArgs));
        verifyZeroInteractions(entityDao, jumpDao, eventDao);
    }


    @Test
    public void shouldUpdateEntity() {
        // Given
        int updated = 42;
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_ENTITIES);
        when(entityDao.update(any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(updated);

        // When
        int result = provider.update(uri, values, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(updated);
        verify(uriMatcher).match(uri);
        verify(entityDao).update(same(values), same(selection), same(selectionArgs));
        verifyZeroInteractions(projectDao, jumpDao, eventDao);
    }

    @Test
    public void shouldUpdateJump() {
        // Given
        int updated = 42;
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_JUMPS);
        when(jumpDao.update(any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(updated);

        // When
        int result = provider.update(uri, values, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(updated);
        verify(uriMatcher).match(uri);
        verify(jumpDao).update(same(values), same(selection), same(selectionArgs));
        verifyZeroInteractions(projectDao, entityDao, eventDao);
    }

    @Test
    public void shouldUpdateEvent() {
        // Given
        int updated = 42;
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_EVENTS);
        when(eventDao.update(any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(updated);

        // When
        int result = provider.update(uri, values, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(updated);
        verify(uriMatcher).match(uri);
        verify(eventDao).update(same(values), same(selection), same(selectionArgs));
        verifyZeroInteractions(projectDao, entityDao, jumpDao);
    }

    @Test
    public void shouldUpdateUnknown() {
        // Given
        int updated = 42;
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(UNKNOWN_MATCH);
        when(entityDao.update(any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(updated);

        // When
        int result = provider.update(uri, values, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(0);
        verify(uriMatcher).match(uri);
        verifyZeroInteractions(projectDao, entityDao, jumpDao, eventDao);
    }

    @Test
    public void shouldDeleteProject() {
        // Given
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_PROJECTS);
        when(projectDao.delete(anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        int result = provider.delete(uri, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(1);
        verify(uriMatcher).match(uri);
        verify(projectDao).delete(same(selection), same(selectionArgs));
        verifyZeroInteractions(entityDao, jumpDao, eventDao);
    }

    @Test
    public void shouldDeleteEntity() {
        // Given
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_ENTITIES);
        when(entityDao.delete(anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        int result = provider.delete(uri, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(1);
        verify(uriMatcher).match(uri);
        verify(entityDao).delete(same(selection), same(selectionArgs));
        verifyZeroInteractions(projectDao, jumpDao, eventDao);
    }


    @Test
    public void shouldDeleteJump() {
        // Given
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_JUMPS);
        when(jumpDao.delete(anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        int result = provider.delete(uri, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(1);
        verify(uriMatcher).match(uri);
        verify(jumpDao).delete(same(selection), same(selectionArgs));
        verifyZeroInteractions(projectDao, entityDao, eventDao);
    }


    @Test
    public void shouldDeleteEvent() {
        // Given
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(ChronorgSchema.MATCH_EVENTS);
        when(eventDao.delete(anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        int result = provider.delete(uri, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(1);
        verify(uriMatcher).match(uri);
        verify(eventDao).delete(same(selection), same(selectionArgs));
        verifyZeroInteractions(projectDao, entityDao, jumpDao);
    }




    @Test
    public void shouldDeleteUnknown() {
        // Given
        Uri uri = mock(Uri.class);
        String selection = "Foo";
        String[] selectionArgs = new String[]{};
        when(uriMatcher.match(any(Uri.class))).thenReturn(UNKNOWN_MATCH);
        when(entityDao.delete(anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        int result = provider.delete(uri, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(0);
        verify(uriMatcher).match(uri);
        verifyZeroInteractions(projectDao, entityDao, jumpDao, eventDao);
    }


}