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
import fr.xgouchet.chronorg.data.ioproviders.JumpIOProvider;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.queriers.JumpContentQuerier;
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
public class JumpRepositoryTest {

    @Mock Context context;
    @Mock JumpIOProvider provider;
    @Mock JumpContentQuerier querier;
    @Mock ContentResolver contentResolver;

    private JumpRepository repository;
    private TestSubscriber<Object> subscriber;

    @Before
    public void setUp() {

        initMocks(this);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(provider.provideQuerier()).thenReturn(querier);

        repository = new JumpRepository(context, provider);
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldGetAllJumps() throws Exception {
        // Given
        final Jump[] jumps = new Jump[]{mock(Jump.class), mock(Jump.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(jumps[0]);
                s.onNext(jumps[1]);
                return null;
            }
        }).when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Jump> observable = repository.getJumps();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(jumps);
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetAllJumpsEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Jump> observable = repository.getJumps();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetAllJumpsWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .queryAll(any(ContentResolver.class), any(Subscriber.class));

        // When
        Observable<Jump> observable = repository.getJumps();
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryAll(same(contentResolver), any(Subscriber.class));
    }

    @Test
    public void shouldGetJumpsInEntity() throws Exception {
        // Given
        int entityId = 42;
        final Jump[] jumps = new Jump[]{mock(Jump.class), mock(Jump.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(jumps[0]);
                s.onNext(jumps[1]);
                return null;
            }
        }).when(querier)
                .queryInEntity(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Jump> observable = repository.getJumpsInEntity(entityId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(jumps);
        verify(querier).queryInEntity(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetJumpsInEntityEmpty() throws Exception {
        // Given
        int entityId = 42;
        doNothing()
                .when(querier)
                .queryInEntity(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Jump> observable = repository.getJumpsInEntity(entityId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryInEntity(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetJumpsInEntityWithError() throws Exception {
        // Given
        int entityId = 42;
        doThrow(new RuntimeException())
                .when(querier)
                .queryInEntity(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Jump> observable = repository.getJumpsInEntity(entityId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryInEntity(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetJump() throws Exception {
        // Given
        final Jump jump = mock(Jump.class);
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber s = (Subscriber) invocation.getArguments()[1];
                s.onNext(jump);
                return null;
            }
        })
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Jump> observable = repository.getJump(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly(jump);
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetJumpEmpty() throws Exception {
        // Given
        doNothing()
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Jump> observable = repository.getJump(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }

    @Test
    public void shouldGetJumpWithError() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(querier)
                .query(any(ContentResolver.class), any(Subscriber.class), anyInt());

        // When
        Observable<Jump> observable = repository.getJump(42);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).query(same(contentResolver), any(Subscriber.class), eq(42));
    }


    @Test
    public void shouldSaveJump() throws Exception {
        // Given
        Jump jump = mock(Jump.class);
        when(querier.save(any(ContentResolver.class), any(Jump.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.saveJump(jump);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).save(same(contentResolver), same(jump));
    }


    @Test
    public void shouldSaveJumpFail() throws Exception {
        // Given
        Jump jump = mock(Jump.class);
        when(querier.save(any(ContentResolver.class), any(Jump.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.saveJump(jump);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(jump));
    }

    @Test
    public void shouldSaveJumpWithError() throws Exception {
        // Given
        Jump jump = mock(Jump.class);
        when(querier.save(any(ContentResolver.class), any(Jump.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.saveJump(jump);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).save(same(contentResolver), same(jump));
    }

    @Test
    public void shouldDeleteJump() throws Exception {
        // Given
        Jump jump = mock(Jump.class);
        when(querier.delete(any(ContentResolver.class), any(Jump.class)))
                .thenReturn(true);

        // When
        Observable<Void> observable = repository.deleteJump(jump);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        verify(querier).delete(same(contentResolver), eq(jump));
    }

    @Test
    public void shouldDeleteJumpFail() throws Exception {
        // Given
        Jump jump = mock(Jump.class);
        when(querier.delete(any(ContentResolver.class), any(Jump.class)))
                .thenReturn(false);

        // When
        Observable<Void> observable = repository.deleteJump(jump);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), eq(jump));
    }

    @Test
    public void shouldDeleteJumpWithError() throws Exception {
        // Given
        Jump jump = mock(Jump.class);
        when(querier.delete(any(ContentResolver.class), any(Jump.class)))
                .thenThrow(new RuntimeException());

        // When
        Observable<Void> observable = repository.deleteJump(jump);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).delete(same(contentResolver), eq(jump));
    }
}