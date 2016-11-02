package fr.xgouchet.chronorg.data.queriers;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ProjectContentQuerierTest {

    @Mock IOProvider<Project> provider;
    private ProjectContentQuerier querier;

    @Before
    public void setUp() {
        initMocks(this);
        querier = new ProjectContentQuerier(provider);
    }

    @Test
    public void shouldGetProjectId() {
        // Given
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(34);

        // When
        int id = querier.getId(project);

        // Then
        assertThat(id).isEqualTo(34);
    }

    @Test
    public void shouldGetProjectUri() {
        // When
        Uri uri = querier.getUri();

        // Then
        assertThat(uri).isSameAs(ChronorgSchema.PROJECTS_URI);
    }

    @Test
    public void shouldOrderByName() {
        // When
        String order = querier.order();

        // Then
        assertThat(order).isEqualTo("name ASC");
    }
}