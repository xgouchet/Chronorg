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
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.data.queriers.EntityContentQuerier;
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
public class EntityRepositoryTest {

    @Mock Context context;
    @Mock EntityIOProvider provider;
    @Mock EntityContentQuerier querier;
    @Mock ContentResolver contentResolver;

    private EntityRepository repository;
    private TestSubscriber<Object> subscriber;

    @Before
    public void setUp() {

        initMocks(this);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(provider.provideQuerier()).thenReturn(querier);

        repository = new EntityRepository(context, provider);
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldGetAllEntities() throws Exception {
        // Given
        final Entity[] entities = new Entity[]{mock(Entity.class), mock(Entity.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(entities[0]);
                s.onNext(entities[1]);
                return null;
            }
        }).when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Entity> observable = repository.getEntities();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(entities);
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetAllEntitiesEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Entity> observable = repository.getEntities();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetAllEntitiesWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Entity> observable = repository.getEntities();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetEntitiesInProject() throws Exception {
        // Given
        int projectId = 42;
        final Entity[] entities = new Entity[]{mock(Entity.class), mock(Entity.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(entities[0]);
                s.onNext(entities[1]);
                return null;
            }
        }).when(querier)
                .queryInProject(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Entity> observable = repository.getEntitiesInProject(projectId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(entities);
        verify(querier).queryInProject(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetEntitiesInProjectEmpty() throws Exception {
        // Given
        int projectId = 42;
        doNothing()
                .when(querier)
                .queryInProject(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Entity> observable = repository.getEntitiesInProject(projectId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryInProject(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetEntitiesInProjectWithError() throws Exception {
        // Given
        int projectId = 42;
        doThrow(new RuntimeException())
                .when(querier)
                .queryInProject(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Entity> observable = repository.getEntitiesInProject(projectId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryInProject(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetEntity() throws Exception {
        // Given
        final Entity entity = mock(Entity.class);
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(entity);
                return null;
            }
        })
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Entity> observable = repository.getEntity(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(entity);
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetEntityEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Entity> observable = repository.getEntity(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetEntityWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Entity> observable = repository.getEntity(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }


    @Test
    public void shouldSaveEntity() throws Exception {
        // Given
        Entity entity = mock(Entity.class);
        when(querier.save(any(ContentResolver.class), any(Entity.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.saveEntity(entity);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).save(same(contentResolver), same(entity));
    }


    @Test
    public void shouldSaveEntityFail() throws Exception {
        // Given
        Entity entity = mock(Entity.class);
        when(querier.save(any(ContentResolver.class), any(Entity.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.saveEntity(entity);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(entity));
    }

    @Test
    public void shouldSaveEntityWithError() throws Exception {
        // Given
        Entity entity = mock(Entity.class);
        when(querier.save(any(ContentResolver.class), any(Entity.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.saveEntity(entity);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(entity));
    }

    @Test
    public void shouldDeleteEntity() throws Exception {
        // Given
        Entity entity = mock(Entity.class);
        when(querier.delete(any(ContentResolver.class), any(Entity.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.deleteEntity(entity);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).delete(same(contentResolver), eq(entity));
    }

    @Test
    public void shouldDeleteEntityFail() throws Exception {
        // Given
        Entity entity = mock(Entity.class);
        when(querier.delete(any(ContentResolver.class), any(Entity.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.deleteEntity(entity);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), eq(entity));
    }

    @Test
    public void shouldDeleteEntityWithError() throws Exception {
        // Given
        Entity entity = mock(Entity.class);
        when(querier.delete(any(ContentResolver.class), any(Entity.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.deleteEntity(entity);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), eq(entity));
    }
}