package fr.xgouchet.chronorg.provider.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

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
import fr.xgouchet.chronorg.provider.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.provider.readers.ProjectCursorReader;
import fr.xgouchet.chronorg.provider.writers.ProjectContentValuesWriter;
import rx.Subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ProjectContentQuerierTest {

    @Mock ContentResolver contentResolver;
    @Mock ProjectIOProvider provider;
    @Mock Subscriber<Project> subscriber;
    @Mock ProjectCursorReader reader;
    @Mock ProjectContentValuesWriter writer;
    @Mock Cursor cursor;
    @Mock ContentValues contentValues;

    private ProjectContentQuerier querier;

    @Before
    public void setUp() {
        initMocks(this);

        when(provider.provideReader(any(Cursor.class)))
                .thenReturn(reader);

        when(provider.provideWriter())
                .thenReturn(writer);

        querier = new ProjectContentQuerier(provider);
    }

    @Test
    public void shouldQueryAll() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        final Project mock1 = mock(Project.class);
        final Project mock2 = mock(Project.class);
        when(reader.instantiateAndFill()).thenReturn(mock1, mock2, null);


        // When
        querier.queryAll(contentResolver, subscriber);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.PROJECTS_URI), isNull(String[].class), isNull(String.class), isNull(String[].class), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(subscriber).onNext(mock1);
        verify(subscriber).onNext(mock2);
        verifyNoMoreInteractions(subscriber);
    }

    @Test
    public void shouldQueryAllWithExceptions() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryAll(contentResolver, subscriber);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.PROJECTS_URI), isNull(String[].class), isNull(String.class), isNull(String[].class), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verifyZeroInteractions(subscriber);
    }

    @Test
    public void shouldQuery() {
        // Given
        int projectId = 42;
        Project mock1 = mock(Project.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Project) null);


        // When
        querier.query(contentResolver, subscriber, projectId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.PROJECTS_URI), isNull(String[].class), eq("id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verify(subscriber).onNext(mock1);
        verifyNoMoreInteractions(subscriber);
    }

    @Test
    public void shouldQueryWithException() {
        // Given
        int projectId = 42;
        Project mock1 = mock(Project.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());

        // When
        try {
            querier.query(contentResolver, subscriber, projectId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.PROJECTS_URI), isNull(String[].class), eq("id=?"), eq(new String[]{"42"}), eq("name ASC"));
        verify(provider).provideReader(same(cursor));
        verifyZeroInteractions(subscriber);
    }

    @Test
    public void shouldSaveNewProject() {
        // Given
        Uri projectUri = mock(Uri.class);
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(projectUri);
        when(writer.toContentValues(any(Project.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).insert(eq(ChronorgSchema.PROJECTS_URI), same(contentValues));
    }

    @Test
    public void shouldSaveNewProjectFail() {
        // Given
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(null);
        when(writer.toContentValues(any(Project.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).insert(eq(ChronorgSchema.PROJECTS_URI), same(contentValues));
    }

    @Test
    public void shouldSaveExistingProject() {
        // Given
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(1);
        when(writer.toContentValues(any(Project.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).update(eq(ChronorgSchema.PROJECTS_URI), same(contentValues), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldSaveExistingProjectFail() {
        // Given
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(0);
        when(writer.toContentValues(any(Project.class)))
                .thenReturn(contentValues);


        // When
        boolean success = querier.save(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).update(eq(ChronorgSchema.PROJECTS_URI), same(contentValues), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldDeleteProject() {
        // Given
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        boolean success = querier.delete(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).delete(eq(ChronorgSchema.PROJECTS_URI), eq("id=?"), eq(new String[]{"42"}));
    }

    @Test
    public void shouldDeleteExistingProjectFail() {
        // Given
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(42);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(0);

        // When
        boolean success = querier.delete(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).delete(eq(ChronorgSchema.PROJECTS_URI), eq("id=?"), eq(new String[]{"42"}));
    }
}