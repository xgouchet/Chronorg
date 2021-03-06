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
import fr.xgouchet.chronorg.data.models.Entity;
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

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.JUMPS_URI), isNull(String[].class), eq("entity_id=?"), eq(new String[]{"42"}), eq("jump_order ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action);
    }

    @Test
    public void shouldGetJumpId(){
        // Given
        Jump jump = mock(Jump.class);
        when(jump.getId()).thenReturn(42);

        // When
        int id = querier.getId(jump);

        // Then
        assertThat(id).isEqualTo(42);
    }

    @Test
    public void shouldFillEntityJumps(){
        // Given
        int entityId = 42;
        Entity entity = mock(Entity.class);
        when(entity.getId()).thenReturn(entityId);
        Jump jump = mock(Jump.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(jump, (Jump) null);

        // When
        querier.fillEntity(contentResolver, entity);

        // Then
        verify(entity).jump(same(jump));

    }
}