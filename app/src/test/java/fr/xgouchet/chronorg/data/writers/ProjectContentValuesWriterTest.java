package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ProjectContentValuesWriterTest {

    public static final String FAKE_NAME = "Foo";

    @Mock ContentValues mockContentValues;
    @Mock Project mockProject;
    private ProjectContentValuesWriter writer;

    @Before
    public void setUp() {
        initMocks(this);
        writer = new ProjectContentValuesWriter();
    }

    @Test
    public void should_write_values() {
        // Given
        when(mockProject.getName()).thenReturn(FAKE_NAME);

        // When
        writer.fillContentValues(mockContentValues, mockProject);

        // Then
        verify(mockContentValues).put(ChronorgSchema.COL_NAME, FAKE_NAME);
    }

}