package fr.xgouchet.chronorg.data.readers;

import android.database.Cursor;
import android.graphics.Color;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EntityCursorReaderTest {

    public static final int IDX_ID = 42;
    public static final int IDX_PROJECT_ID = 43;
    public static final int IDX_NAME = 44;
    public static final int IDX_DESCRIPTION = 45;
    public static final int IDX_BIRTH = 46;
    public static final int IDX_DEATH = 47;
    public static final int IDX_COLOUR = 48;

    @Mock Cursor mockCursor;
    private EntityCursorReader entityCursorReader;

    @Before
    public void setUp() {
        initMocks(this);

        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ID)).thenReturn(IDX_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_PROJECT_ID)).thenReturn(IDX_PROJECT_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_NAME)).thenReturn(IDX_NAME);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_DESCRIPTION)).thenReturn(IDX_DESCRIPTION);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_BIRTH)).thenReturn(IDX_BIRTH);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_DEATH)).thenReturn(IDX_DEATH);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_COLOUR)).thenReturn(IDX_COLOUR);

        entityCursorReader = new EntityCursorReader(mockCursor);
    }

    @Test
    public void should_instantiate_non_null() {
        assertThat(entityCursorReader.instantiate()).isNotNull();
    }

    @Test
    public void should_fill_entity() {
        // Given
        Entity entity = mock(Entity.class);
        when(mockCursor.getInt(IDX_ID)).thenReturn(42);
        when(mockCursor.getInt(IDX_PROJECT_ID)).thenReturn(815);
        when(mockCursor.getString(IDX_NAME)).thenReturn("Foo");
        when(mockCursor.getString(IDX_DESCRIPTION)).thenReturn("Lorem ipsum");
        when(mockCursor.getString(IDX_BIRTH)).thenReturn("1968-12-06T12:00:00Z");
        when(mockCursor.getString(IDX_DEATH)).thenReturn("2091-04-08T12:00:00Z");
        when(mockCursor.getInt(IDX_COLOUR)).thenReturn(Color.RED);

        // When
        entityCursorReader.fill(entity);

        // Then
        verify(entity).setId(42);
        verify(entity).setProjectId(815);
        verify(entity).setName("Foo");
        verify(entity).setDescription("Lorem ipsum");
        verify(entity).setBirth("1968-12-06T12:00:00Z");
        verify(entity).setDeath("2091-04-08T12:00:00Z");
        verify(entity).setColour(Color.RED);
    }


}