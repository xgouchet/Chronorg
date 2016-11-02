package fr.xgouchet.chronorg.data.queriers;

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
import fr.xgouchet.chronorg.data.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.readers.EntityCursorReader;
import fr.xgouchet.chronorg.data.writers.EntityContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doNothing;
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
    @Mock Action1<Entity> action;
    @Mock EntityCursorReader reader;
    @Mock EntityContentValuesWriter writer;
    @Mock Cursor cursor;
    @Mock ContentValues contentValues;

    @Mock JumpContentQuerier jumpContentQuerier;
    private EntityContentQuerier querier;

    @Before
    public void setUp() {
        initMocks(this);
        when(provider.provideReader(any(Cursor.class)))
                .thenReturn(reader);

        when(provider.provideWriter())
                .thenReturn(writer);
        querier = new EntityContentQuerier(provider, jumpContentQuerier);
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
        querier.queryInProject(contentResolver, action, projectId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("project_id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(cursor).close();
        verifyNoMoreInteractions(action);
        verifyZeroInteractions(jumpContentQuerier);

    }

    @Test
    public void shouldQueryInProjectWithException() {
        // Given
        int projectId = 42;
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryInProject(contentResolver, action, projectId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("project_id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action, jumpContentQuerier);
    }


    @Test
    public void shouldQueryFullInProject() {
        // Given
        int projectId = 42;
        Entity mock1 = mock(Entity.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Entity) null);
        doNothing().when(jumpContentQuerier).fillEntity(any(ContentResolver.class), any(Entity.class));

        // When
        querier.queryFullInProject(contentResolver, action, projectId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("project_id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(cursor).close();
        verify(jumpContentQuerier).fillEntity(same(contentResolver), same(mock1));
        verifyNoMoreInteractions(action);
    }

    @Test
    public void shouldQueryFullInProjectWithException() {
        // Given
        int projectId = 42;
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryFullInProject(contentResolver, action, projectId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.ENTITIES_URI), isNull(String[].class), eq("project_id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action, jumpContentQuerier);
    }


    @Test
    public void shouldGetEntityId() {
        // Given
        Entity entity = mock(Entity.class);
        when(entity.getId()).thenReturn(42);

        // When
        int id = querier.getId(entity);

        // Then
        assertThat(id).isEqualTo(42);
    }

}