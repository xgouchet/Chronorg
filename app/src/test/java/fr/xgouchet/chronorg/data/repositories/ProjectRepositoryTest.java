package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.provider.queriers.ProjectContentQuerier;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@SuppressWarnings("unchecked")
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ProjectRepositoryTest {


    @Mock Context context;
    @Mock ProjectIOProvider provider;
    @Mock ProjectContentQuerier querier;
    @Mock ContentResolver contentResolver;

    private ProjectRepository repository;
    private TestSubscriber<Object> subscriber;

    @Before
    public void setUp() {

        initMocks(this);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(provider.provideQuerier()).thenReturn(querier);

        repository = new ProjectRepository(context, provider);
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldGetProjects() throws Exception {
        // Given
        final Project[] projects = new Project[]{mock(Project.class), mock(Project.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(projects[0]);
                s.onNext(projects[1]);
                return null;
            }
        }).when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Project> observable = repository.getProjects();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(projects);
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetProjectsEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Project> observable = repository.getProjects();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetProjectsWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Project> observable = repository.getProjects();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetProject() throws Exception {
        // Given
        final Project project = mock(Project.class);
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(project);
                return null;
            }
        })
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Project> observable = repository.getProject(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(project);
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetProjectEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Project> observable = repository.getProject(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetProjectWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Project> observable = repository.getProject(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }


    @Test
    public void shouldSaveProject() throws Exception {
        // Given
        Project project = mock(Project.class);
        when(querier.save(any(ContentResolver.class), any(Project.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.saveProject(project);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).save(same(contentResolver), same(project));
    }


    @Test
    public void shouldSaveProjectFail() throws Exception {
        // Given
        Project project = mock(Project.class);
        when(querier.save(any(ContentResolver.class), any(Project.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.saveProject(project);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(project));
    }

    @Test
    public void shouldSaveProjectWithError() throws Exception {
        // Given
        Project project = mock(Project.class);
        when(querier.save(any(ContentResolver.class), any(Project.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.saveProject(project);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(project));
    }

    @Test
    public void shouldDeleteProject() throws Exception {
        // Given
        Project project = mock(Project.class);
        when(querier.delete(any(ContentResolver.class), any(Project.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.deleteProject(project);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).delete(same(contentResolver), eq(project));
    }

    @Test
    public void shouldDeleteProjectFail() throws Exception {
        // Given
        Project project = mock(Project.class);
        when(querier.delete(any(ContentResolver.class), any(Project.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.deleteProject(project);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), eq(project));
    }

    @Test
    public void shouldDeleteProjectWithError() throws Exception {
        // Given
        Project project = mock(Project.class);
        when(querier.delete(any(ContentResolver.class), any(Project.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.deleteProject(project);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), eq(project));
    }
}