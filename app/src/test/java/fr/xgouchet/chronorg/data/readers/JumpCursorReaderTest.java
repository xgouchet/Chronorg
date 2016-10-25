package fr.xgouchet.chronorg.data.readers;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Jump;
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
public class JumpCursorReaderTest {

    public static final int IDX_ID = 42;
    public static final int IDX_ENTITY_ID = 43;
    public static final int IDX_NAME = 44;
    public static final int IDX_DESCRIPTION = 45;
    public static final int IDX_FROM = 46;
    public static final int IDX_TO = 47;
    public static final int IDX_ORDER = 48;

    @Mock Cursor mockCursor;
    private JumpCursorReader jumpCursorReader;

    @Before
    public void setUp() {
        initMocks(this);

        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ID)).thenReturn(IDX_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ENTITY_ID)).thenReturn(IDX_ENTITY_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_NAME)).thenReturn(IDX_NAME);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_FROM_INSTANT)).thenReturn(IDX_FROM);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_TO_INSTANT)).thenReturn(IDX_TO);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ORDER)).thenReturn(IDX_ORDER);

        jumpCursorReader = new JumpCursorReader(mockCursor);
    }

    @Test
    public void shouldInstantiateNonNull() {
        assertThat(jumpCursorReader.instantiate()).isNotNull();
    }

    @Test
    public void shouldFillJump() {
        // Given
        Jump jump = mock(Jump.class);
        when(mockCursor.getInt(IDX_ID)).thenReturn(42);
        when(mockCursor.getInt(IDX_ENTITY_ID)).thenReturn(815);
        when(mockCursor.getString(IDX_NAME)).thenReturn("Foo");
        when(mockCursor.getString(IDX_DESCRIPTION)).thenReturn("Lorem ipsum");
        when(mockCursor.getString(IDX_FROM)).thenReturn("1968-12-06T12:00:00Z");
        when(mockCursor.getString(IDX_TO)).thenReturn("2091-04-08T12:00:00Z");
        when(mockCursor.getInt(IDX_ORDER)).thenReturn(66);

        // When
        jumpCursorReader.fill(jump);

        // Then
        verify(jump).setId(42);
        verify(jump).setEntityId(815);
        verify(jump).setName("Foo");
        verify(jump).setFrom("1968-12-06T12:00:00Z");
        verify(jump).setTo("2091-04-08T12:00:00Z");
        verify(jump).setOrder(66);
    }


}