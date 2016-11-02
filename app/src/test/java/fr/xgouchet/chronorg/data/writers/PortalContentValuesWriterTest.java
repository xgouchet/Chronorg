package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.graphics.Color;

import org.joda.time.Interval;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class PortalContentValuesWriterTest {

    public static final int FAKE_PROJECT_ID = 815;
    public static final String FAKE_NAME = "Foo";
    public static final Interval FAKE_DELAY = new Interval("1968-12-06T12:00:00Z/2091-01-01T12:00:00Z");
    public static final int FAKE_DIRECTION = Portal.Direction.PAST;
    public static final int FAKE_COLOUR = Color.GREEN;

    @Mock ContentValues mockContentValues;
    @Mock Portal mockPortal;
    private PortalContentValuesWriter writer;

    @Before
    public void setUp() {
        initMocks(this);
        writer = new PortalContentValuesWriter();
    }

    @Test
    public void should_write_values() {
        // Given
        when(mockPortal.getProjectId()).thenReturn(FAKE_PROJECT_ID);
        when(mockPortal.getName()).thenReturn(FAKE_NAME);
        when(mockPortal.getDelay()).thenReturn(FAKE_DELAY);
        when(mockPortal.getDirection()).thenReturn(FAKE_DIRECTION);
        when(mockPortal.getColor()).thenReturn(FAKE_COLOUR);

        // When
        writer.fillContentValues(mockContentValues, mockPortal);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_PROJECT_ID, FAKE_PROJECT_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
        verify(mockContentValues).put(ChronorgSchema.COL_DELAY, FAKE_DELAY.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_DIRECTION, FAKE_DIRECTION);
        verify(mockContentValues).put(ChronorgSchema.COL_TIMELINE, 1); 
        verify(mockContentValues).put(ChronorgSchema.COL_COLOR, FAKE_COLOUR);
    }

}