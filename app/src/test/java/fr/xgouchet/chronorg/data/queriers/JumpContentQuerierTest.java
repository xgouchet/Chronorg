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
import fr.xgouchet.chronorg.data.ioproviders.JumpIOProvider;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.readers.JumpCursorReader;
import fr.xgouchet.chronorg.data.writers.JumpContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

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
public class JumpContentQuerierTest {

    @Mock ContentResolver contentResolver;
    @Mock JumpIOProvider provider;
    @Mock Action1<Jump> action;
    @Mock JumpCursorReader reader;
    @Mock JumpContentValuesWriter writer;
    @Mock Cursor cursor;
    @Mock ContentValues contentValues;

    private JumpContentQuerier querier;

    @Before
    public void setUp() {
        initMocks(this);
        when(provider.provideReader(any(Cursor.class)))
                .thenReturn(reader);

        when(provider.provideWriter())
                .thenReturn(writer);
        querier = new JumpContentQuerier(provider);
    }


    @Test
    public void shouldQueryAll() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        final Jump mock1 = mock(Jump.class);
        final Jump mock2 = mock(Jump.class);
        when(reader.instantiateAndFill()).thenReturn(mock1, mock2, null);


        // When
        querier.queryAll(contentResolver, action);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), isNull(String.class), isNull(String[].class), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(action).call(mock2);
        verify(cursor).close();
        verifyNoMoreInteractions(action);
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
            querier.queryAll(contentResolver, action);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), isNull(String.class), isNull(String[].class), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action);
    }

    @Test
    public void shouldQuery() {
        // Given
        int jumpId = 42;
        Jump mock1 = mock(Jump.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Jump) null);


        // When
        querier.query(contentResolver, action, jumpId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), eq("id=?"), eq(new String[]{"42"}), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(cursor).close();
        verifyNoMoreInteractions(action);
    }

    @Test
    public void shouldQueryWithException() {
        // Given
        int jumpId = 42;
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.query(contentResolver, action, jumpId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), eq("id=?"), eq(new String[]{"42"}), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action);
    }


    @Test
    public void shouldQueryInEntity() {
        // Given
        int entityId = 42;
        Jump mock1 = mock(Jump.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Jump) null);


        // When
        querier.queryInEntity(contentResolver, action, entityId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), eq("entity_id=?"), eq(new String[]{"42"}), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(cursor).close();
        verifyNoMoreInteractions(action);
    }

    @Test
    public void shouldQueryInEntityWithException() {
        // Given
        int entityId = 42;
        Jump mock1 = mock(Jump.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryInEntity(contentResolver, action, entityId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Thenœ
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), eq("entity_id=?"), eq(new String[]{"42"}), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action);
    }

    @Test
    public void shouldSaveNewJump() {
        // Given
        Uri entityUri = mock(Uri.class);
        Jump entity = mock(Jump.class);
        when(entity.getId()).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(entityUri);
        when(writer.toContentValues(any(Jump.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, entity);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).insert(eq(ChronorgSchema.JUMPS_URI), same(contentValues));
    }

    @Test
    public void shouldSaveNewJumpFail() {
        // Given
        Jump entity = mock(Jump.class);
        when(entity.getId()).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(null);
        when(writer.toContentValues(any(Jump.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, entity);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).insert(eq(ChronorgSchema.JUMPS_URI), same(contentValues));
    }

    @Test
    public void shouldSaveExistingJump() {
        // Given
        Jump entity = mock(Jump.class);
        when(entity.getId()).thenReturn(42);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(1);
        when(writer.toContentValues(any(Jump.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, entity);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).update(eq(ChronorgSchema.JUMPS_URI), same(contentValues), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldSaveExistingJumpFail() {
        // Given
        Jump entity = mock(Jump.class);
        when(entity.getId()).thenReturn(42);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(0);
        when(writer.toContentValues(any(Jump.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, entity);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).update(eq(ChronorgSchema.JUMPS_URI), same(contentValues), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldDeleteJump() {
        // Given
        Jump entity = mock(Jump.class);
        when(entity.getId()).thenReturn(42);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        boolean success = querier.delete(contentResolver, entity);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).delete(eq(ChronorgSchema.JUMPS_URI), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldDeleteExistingJumpFail() {
        // Given
        Jump entity = mock(Jump.class);
        when(entity.getId()).thenReturn(42);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(0);

        // When
        boolean success = querier.delete(contentResolver, entity);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).delete(eq(ChronorgSchema.JUMPS_URI), eq("id=?"), eq(new String[]{"42"}));
    }
}