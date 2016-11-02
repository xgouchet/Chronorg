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
import fr.xgouchet.chronorg.data.models.Portal;
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
public class PortalCursorReaderTest {

    public static final int IDX_ID = 42;
    public static final int IDX_PROJECT_ID = 43;
    public static final int IDX_NAME = 44;
    public static final int IDX_DESCRIPTION = 45;
    public static final int IDX_DELAY = 46;
    public static final int IDX_DIRECTION = 47;
    public static final int IDX_COLOUR = 48;

    @Mock Cursor mockCursor;
    private PortalCursorReader portalCursorReader;

    @Before
    public void setUp() {
        initMocks(this);

        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ID)).thenReturn(IDX_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_PROJECT_ID)).thenReturn(IDX_PROJECT_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_NAME)).thenReturn(IDX_NAME);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_DELAY)).thenReturn(IDX_DELAY);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_DIRECTION)).thenReturn(IDX_DIRECTION);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_COLOR)).thenReturn(IDX_COLOUR);

        portalCursorReader = new PortalCursorReader(mockCursor);
    }

    @Test
    public void should_instantiate_non_null() {
        assertThat(portalCursorReader.instantiate()).isNotNull();
    }

    @Test
    public void should_fill_entity() {
        // Given
        Portal portal = mock(Portal.class);
        when(mockCursor.getInt(IDX_ID)).thenReturn(42);
        when(mockCursor.getInt(IDX_PROJECT_ID)).thenReturn(815);
        when(mockCursor.getString(IDX_NAME)).thenReturn("Foo");
        when(mockCursor.getString(IDX_DESCRIPTION)).thenReturn("Lorem ipsum");
        when(mockCursor.getString(IDX_DELAY)).thenReturn("1968-12-06T12:00:00Z/2091-01-01T12:00:00Z");
        when(mockCursor.getInt(IDX_DIRECTION)).thenReturn(Portal.Direction.FUTURE);
        when(mockCursor.getInt(IDX_COLOUR)).thenReturn(Color.RED);

        // When
        portalCursorReader.fill(portal);

        // Then
        verify(portal).setId(42);
        verify(portal).setProjectId(815);
        verify(portal).setName("Foo");
        verify(portal).setDelay("1968-12-06T12:00:00Z/2091-01-01T12:00:00Z");
        verify(portal).setDirection(Portal.Direction.FUTURE);
        verify(portal).setColor(Color.RED);
    }

}