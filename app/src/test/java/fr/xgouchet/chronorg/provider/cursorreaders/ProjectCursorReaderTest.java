package fr.xgouchet.chronorg.provider.cursorreaders;

import android.database.Cursor;

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
public class ProjectCursorReaderTest {

    public static final int IDX_ID = 42;
    public static final int IDX_NAME = 43;
    public static final int IDX_DESCRIPTION = 44;

    @Mock Cursor mockCursor;
    private ProjectCursorReader projectCursorReader;

    @Before
    public void setUp() {
        initMocks(this);

        when(mockCursor.getColumnIndex(ChronorgSchema.COL_ID)).thenReturn(IDX_ID);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_NAME)).thenReturn(IDX_NAME);
        when(mockCursor.getColumnIndex(ChronorgSchema.COL_DESCRIPTION)).thenReturn(IDX_DESCRIPTION);

        projectCursorReader = new ProjectCursorReader(mockCursor);
    }

    @Test
    public void should_instantiate_non_null() {
        assertThat(projectCursorReader.instantiate()).isNotNull();
    }

    @Test
    public void should_fill_entity() {
        // Given
        Project entity = mock(Project.class);
        when(mockCursor.getInt(IDX_ID)).thenReturn(42);
        when(mockCursor.getString(IDX_NAME)).thenReturn("Foo");
        when(mockCursor.getString(IDX_DESCRIPTION)).thenReturn("Lorem ipsum");

        // When
        projectCursorReader.fill(entity);

        // Then
        verify(entity).setId(42);
        verify(entity).setName("Foo");
        verify(entity).setDescription("Lorem ipsum");
    }

}