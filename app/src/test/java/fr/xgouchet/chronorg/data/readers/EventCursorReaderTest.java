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
import fr.xgouchet.chronorg.data.models.Event;
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
public class EventCursorReaderTest {

    public static final int IDX_ID = 42;
    public static final int IDX_PROJECT_ID = 43;
    public static final int IDX_NAME = 44;
    public static final int IDX_DESCRIPTION = 45;
    public static final int IDX_INSTANT = 46;
    public static final int IDX_COLOR = 47;

    @Mock Cursor mockCursor;
    private EventCursorReader eventCursorReader;

    @Before
    public void setUp() {
        initMocks(this);

        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ID)).thenReturn(IDX_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_PROJECT_ID)).thenReturn(IDX_PROJECT_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_NAME)).thenReturn(IDX_NAME);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_INSTANT)).thenReturn(IDX_INSTANT);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_COLOR)).thenReturn(IDX_COLOR);

        eventCursorReader = new EventCursorReader(mockCursor);
    }

    @Test
    public void shouldInstantiateNonNull() {
        assertThat(eventCursorReader.instantiate()).isNotNull();
    }

    @Test
    public void shouldFillEvent() {
        // Given
        Event event = mock(Event.class);
        when(mockCursor.getInt(IDX_ID)).thenReturn(42);
        when(mockCursor.getInt(IDX_PROJECT_ID)).thenReturn(815);
        when(mockCursor.getString(IDX_NAME)).thenReturn("Foo");
        when(mockCursor.getString(IDX_DESCRIPTION)).thenReturn("Lorem ipsum");
        when(mockCursor.getString(IDX_INSTANT)).thenReturn("1968-12-06T12:00:00Z");
        when(mockCursor.getInt(IDX_COLOR)).thenReturn(0x0080FF);

        // When
        eventCursorReader.fill(event);

        // Then
        verify(event).setId(42);
        verify(event).setProjectId(815);
        verify(event).setName("Foo");
        verify(event).setInstant("1968-12-06T12:00:00Z");
        verify(event).setColor(0x0080FF);
    }
}