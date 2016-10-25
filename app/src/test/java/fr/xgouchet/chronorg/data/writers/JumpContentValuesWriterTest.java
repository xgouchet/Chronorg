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
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class JumpContentValuesWriterTest {


    public static final int FAKE_ENTITY_ID = 815;
    public static final String FAKE_NAME = "Foo";
    public static final DateTime FAKE_FROM = new DateTime("1968-12-06T12:00:00Z");
    public static final DateTime FAKE_TO = new DateTime("2091-01-01T12:00:00Z");
    public static final int FAKE_ORDER = 66;

    @Mock ContentValues mockContentValues;
    @Mock Jump mockJump;
    private JumpContentValuesWriter writer;

    @Before
    public void setUp() {
        initMocks(this);
        writer = new JumpContentValuesWriter();
    }

    @Test
    public void shouldWriteValues() {
        // Given
        when(mockJump.getEntityId()).thenReturn(FAKE_ENTITY_ID);
        when(mockJump.getName()).thenReturn(FAKE_NAME);
        when(mockJump.getFrom()).thenReturn(FAKE_FROM);
        when(mockJump.getTo()).thenReturn(FAKE_TO);
        when(mockJump.getOrder()).thenReturn(FAKE_ORDER);

        // When
        writer.fillContentValues(mockContentValues, mockJump);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_ENTITY_ID, FAKE_ENTITY_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
        verify(mockContentValues).put(ChronorgSchema.COL_FROM_INSTANT, FAKE_FROM.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_TO_INSTANT, FAKE_TO.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_ORDER, FAKE_ORDER);
    }

    @Test
    public void shouldWriteValuesNullables() {
        // Given
        when(mockJump.getEntityId()).thenReturn(FAKE_ENTITY_ID);
        when(mockJump.getName()).thenReturn(null);
        when(mockJump.getFrom()).thenReturn(FAKE_FROM);
        when(mockJump.getTo()).thenReturn(FAKE_TO);
        when(mockJump.getOrder()).thenReturn(FAKE_ORDER);

        // When
        writer.fillContentValues(mockContentValues, mockJump);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_ENTITY_ID, FAKE_ENTITY_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, (String) null);
        verify(mockContentValues).put(ChronorgSchema.COL_FROM_INSTANT, FAKE_FROM.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_TO_INSTANT, FAKE_TO.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_ORDER, FAKE_ORDER);
    }
}