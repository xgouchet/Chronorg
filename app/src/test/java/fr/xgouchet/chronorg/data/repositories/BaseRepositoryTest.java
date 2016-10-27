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
import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import rx.Observable;
import rx.functions.Action1;
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
public class BaseRepositoryTest {


    public static final int FAKE_ID = 42;
    @Mock Context context;
    @Mock IOProvider provider;
    @Mock ContentQuerier querier;
    @Mock ContentResolver contentResolver;

    private BaseRepository repository;
    private TestSubscriber<Object> subscriber;

    @Before
    public void setUp() {

        initMocks(this);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(provider.provideQuerier()).thenReturn(querier);

        repository = new BaseRepository<>(context, provider);
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldGetAll() throws Exception {
        // Given
        final Object[] items = new Object[]{mock(Object.class), mock(Object.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Action1 s = (Action1) invocation.getArguments()[1];
                s.call(items[0]);
                s.call(items[1]);
                return null;
            }
        }).when(querier)
                .queryAll(any(ContentResolver.class), any(Action1.class));

        // When
        Observable<Object> observable = repository.getAll();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(items);
        verify(querier).queryAll(same(contentResolver), any(Action1.class));
    }

    @Test
    public void shouldGetAllEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Action1.class));

        // When
        Observable<Object> observable = repository.getAll();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryAll(same(contentResolver), any(Action1.class));
    }

    @Test
    public void shouldGetAllWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Action1.class));

        // When
        Observable<Object> observable = repository.getAll();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryAll(same(contentResolver), any(Action1.class));
    }

    @Test
    public void shouldGet() throws Exception {
        // Given
        final Object item = mock(Object.class);
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Action1 s = (Action1) invocation.getArguments()[1];
                s.call(item);
                return null;
            }
        })
                .when(querier)
                .query(any(ContentResolver.class), any(Action1.class), anyInt());

        // When
        Observable<Object> observable = repository.get(FAKE_ID);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(item);
        verify(querier).query(same(contentResolver), any(Action1.class), eq(FAKE_ID));
    }

    @Test
    public void shouldGetEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .query(any(ContentResolver.class), any(Action1.class), anyInt());

        // When
        Observable<Object> observable = repository.get(FAKE_ID);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).query(same(contentResolver), any(Action1.class), eq(FAKE_ID));
    }

    @Test
    public void shouldGetWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .query(any(ContentResolver.class), any(Action1.class), anyInt());

        // When
        Observable<Object> observable = repository.get(FAKE_ID);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).query(same(contentResolver), any(Action1.class), eq(FAKE_ID));
    }


    @Test
    public void shouldSave() throws Exception {
        // Given
        Object item = mock(Object.class);
        when(querier.save(any(ContentResolver.class), any(Object.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.save(item);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).save(same(contentResolver), same(item));
    }


    @Test
    public void shouldSaveFail() throws Exception {
        // Given
        Object item = mock(Object.class);
        when(querier.save(any(ContentResolver.class), any(Object.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.save(item);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(item));
    }

    @Test
    public void shouldSaveWithError() throws Exception {
        // Given
        Object item = mock(Object.class);
        when(querier.save(any(ContentResolver.class), any(Object.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.save(item);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(item));
    }

    @Test
    public void shouldDelete() throws Exception {
        // Given
        Object item = mock(Object.class);
        when(querier.delete(any(ContentResolver.class), any(Object.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.delete(item);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).delete(same(contentResolver), same(item));
    }

    @Test
    public void shouldDeleteFail() throws Exception {
        // Given
        Object item = mock(Object.class);
        when(querier.delete(any(ContentResolver.class), any(Object.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.delete(item);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), same(item));
    }

    @Test
    public void shouldDeleteWithError() throws Exception {
        // Given
        Object item = mock(Object.class);
        when(querier.delete(any(ContentResolver.class), any(Object.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.delete(item);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), same(item));
    }
}