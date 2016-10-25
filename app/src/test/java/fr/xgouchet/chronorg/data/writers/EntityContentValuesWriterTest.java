package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.graphics.Color;

import org.joda.time.DateTime;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EntityContentValuesWriterTest {

    public static final int FAKE_PROJECT_ID = 815;
    public static final String FAKE_NAME = "Foo";
    public static final DateTime FAKE_BIRTH = new DateTime("1968-12-06T12:00:00Z");
    public static final DateTime FAKE_DEATH = new DateTime("2091-01-01T12:00:00Z");
    public static final int FAKE_COLOUR = Color.GREEN;

    @Mock ContentValues mockContentValues;
    @Mock Entity mockEntity;
    private EntityContentValuesWriter writer;

    @Before
    public void setUp() {
        initMocks(this);
        writer = new EntityContentValuesWriter();
    }

    @Test
    public void should_write_values() {
        // Given
        when(mockEntity.getProjectId()).thenReturn(FAKE_PROJECT_ID);
        when(mockEntity.getName()).thenReturn(FAKE_NAME);
        when(mockEntity.getBirth()).thenReturn(FAKE_BIRTH);
        when(mockEntity.getDeath()).thenReturn(FAKE_DEATH);
        when(mockEntity.getColor()).thenReturn(FAKE_COLOUR);

        // When
        writer.fillContentValues(mockContentValues, mockEntity);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_PROJECT_ID, FAKE_PROJECT_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
        verify(mockContentValues).put(ChronorgSchema.COL_BIRTH_INSTANT, FAKE_BIRTH.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_DEATH_INSTANT, FAKE_DEATH.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_COLOR, FAKE_COLOUR);
    }

    @Test
    public void should_write_values_nullables() {
        // Given
        when(mockEntity.getProjectId()).thenReturn(FAKE_PROJECT_ID);
        when(mockEntity.getName()).thenReturn(FAKE_NAME);
        when(mockEntity.getBirth()).thenReturn(FAKE_BIRTH);
        when(mockEntity.getDeath()).thenReturn(null);
        when(mockEntity.getColor()).thenReturn(FAKE_COLOUR);

        // When
        writer.fillContentValues(mockContentValues, mockEntity);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_PROJECT_ID, FAKE_PROJECT_ID);
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
        verify(mockContentValues).put(ChronorgSchema.COL_BIRTH_INSTANT, FAKE_BIRTH.toString());
        verify(mockContentValues).put(ChronorgSchema.COL_DEATH_INSTANT, (String) null);
        verify(mockContentValues).put(ChronorgSchema.COL_COLOR, FAKE_COLOUR);
    }
}