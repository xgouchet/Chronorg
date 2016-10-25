package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;

import org.joda.time.DateTime;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EventContentValuesWriterTest {


    public static final int FAKE_PROJECT_ID = 815;
    public static final String FAKE_NAME = "Foo";
    public static final DateTime FAKE_INSTANT = new DateTime("1968-12-06T12:00:00Z");
    public static final int FAKE_COLOR = 0x0080FF;

    @Mock ContentValues mockContentValues;
    @Mock Event mockEvent;
    private EventContentValuesWriter writer;

    @Before
    public void setUp() {
        initMocks(this);
        writer = new EventContentValuesWriter();
    }

    @Test
    public void shouldWriteValues() {
        // Given
        when(mockEvent.getProjectId()).thenReturn(FAKE_PROJECT_ID);
        when(mockEvent.getName()).thenReturn(FAKE_NAME);
        when(mockEvent.getInstant()).thenReturn(FAKE_INSTANT);
        when(mockEvent.getColor()).thenReturn(FAKE_COLOR);

        // When
        writer.fillContentValues(mockContentValues, mockEvent);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_PROJECT_ID, FAKE_PROJECT_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
        verify(mockContentValues).put(ChronorgSchema.COL_INSTANT, FAKE_INSTANT.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_COLOR, FAKE_COLOR);
    }

    @Test
    public void shouldWriteValuesNullables() {
        // Given
        when(mockEvent.getProjectId()).thenReturn(FAKE_PROJECT_ID);
        when(mockEvent.getName()).thenReturn(FAKE_NAME);
        when(mockEvent.getInstant()).thenReturn(FAKE_INSTANT);
        when(mockEvent.getColor()).thenReturn(FAKE_COLOR);

        // When
        writer.fillContentValues(mockContentValues, mockEvent);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_PROJECT_ID, FAKE_PROJECT_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
        verify(mockContentValues).put(ChronorgSchema.COL_INSTANT, FAKE_INSTANT.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_COLOR, FAKE_COLOR);
    }
}