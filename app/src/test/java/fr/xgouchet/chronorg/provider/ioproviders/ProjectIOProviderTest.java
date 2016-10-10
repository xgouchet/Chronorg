package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.queriers.BaseContentQuerier;
import fr.xgouchet.chronorg.provider.queriers.ProjectContentQuerier;
import fr.xgouchet.chronorg.provider.readers.BaseCursorReader;
import fr.xgouchet.chronorg.provider.readers.ProjectCursorReader;
import fr.xgouchet.chronorg.provider.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.writers.ProjectContentValuesWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ProjectIOProviderTest {

    ProjectIOProvider provider;

    @Before
    public void setUp() {
        initMocks(this);
        provider = new ProjectIOProvider();
    }

    @Test
    public void shouldProvideWriter() {
        // When
        BaseContentValuesWriter<Project> writer = provider.provideWriter();

        // Then
        assertThat(writer).isInstanceOf(ProjectContentValuesWriter.class);
    }

    @Test
    public void shouldProvideReader() {
        // Given
        Cursor mockCursor = mock(Cursor.class);

        // When
        BaseCursorReader<Project> reader = provider.provideReader(mockCursor);

        // Then
        assertThat(reader).isExactlyInstanceOf(ProjectCursorReader.class);
        assertThat(reader.getCursor()).isSameAs(mockCursor);
    }

    @Test
    public void shouldProvideQuerier(){
        // When
        BaseContentQuerier<Project> reader = provider.provideQuerier();

        // Then
        assertThat(reader).isExactlyInstanceOf(ProjectContentQuerier.class);
    }
}
