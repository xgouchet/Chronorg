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
import fr.xgouchet.chronorg.data.ioproviders.EventIOProvider;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.queriers.EventContentQuerier;
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
public class EventRepositoryTest {

    @Mock Context context;
    @Mock EventIOProvider provider;
    @Mock EventContentQuerier querier;
    @Mock ContentResolver contentResolver;

    private EventRepository repository;
    private TestSubscriber<Object> subscriber;

    @Before
    public void setUp() {

        initMocks(this);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(provider.provideQuerier()).thenReturn(querier);

        repository = new EventRepository(context, provider);
        subscriber = new TestSubscriber<>();
    }


    @Test
    public void shouldGetEventsInProject() throws Exception {
        // Given
        int projectId = 42;
        final Event[] events = new Event[]{mock(Event.class), mock(Event.class)};
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Action1 s = (Action1) invocation.getArguments()[1];
                s.call(events[0]);
                s.call(events[1]);
                return null;
            }
        }).when(querier)
                .queryInProject(any(ContentResolver.class), any(Action1.class), anyInt());

        // When
        Observable<Event> observable = repository.getEventsInProject(projectId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).containsExactly((Object[])events);
        verify(querier).queryInProject(same(contentResolver), any(Action1.class), eq(42));
    }

    @Test
    public void shouldGetEventsInProjectEmpty() throws Exception {
        // Given
        int projectId = 42;
        doNothing()
                .when(querier)
                .queryInProject(any(ContentResolver.class), any(Action1.class), anyInt());

        // When
        Observable<Event> observable = repository.getEventsInProject(projectId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
        assertThat(result).isEmpty();
        verify(querier).queryInProject(same(contentResolver), any(Action1.class), eq(42));
    }

    @Test
    public void shouldGetEventsInProjectWithError() throws Exception {
        // Given
        int projectId = 42;
        doThrow(new RuntimeException())
                .when(querier)
                .queryInProject(any(ContentResolver.class), any(Action1.class), anyInt());

        // When
        Observable<Event> observable = repository.getEventsInProject(projectId);
        observable.subscribe(subscriber);

        // Then
        subscriber.assertError(RuntimeException.class);
        subscriber.assertNotCompleted();
        verify(querier).queryInProject(same(contentResolver), any(Action1.class), eq(42));
    }

}