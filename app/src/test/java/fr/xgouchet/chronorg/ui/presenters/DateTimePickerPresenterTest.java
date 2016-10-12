package fr.xgouchet.chronorg.ui.presenters;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.ui.contracts.DateTimePickerContract;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class DateTimePickerPresenterTest {

    @Mock DateTimePickerContract.View view;
    @Mock DateTimePickerPresenter.DateTimeInputValidator validator;
    private DateTimePickerPresenter presenter;

    @Before
    public void setUp() {

        initMocks(this);

        presenter = new DateTimePickerPresenter(view, validator, null, null, null);
        verify(view).setPresenter(presenter);
    }

    @Test
    public void shouldDoNothingOnUnsubscribe() {
        // When
        presenter.unsubscribe();

        // Then
        verifyNoMoreInteractions(view);
    }


    @Test
    public void shouldDoNothingOnLoad() {
        // When
        presenter.load(false);

        // Then
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldDoNothingOnForceLoad() {
        // When
        presenter.load(true);

        // Then
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_nullDate() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(false);

        // When
        presenter = new DateTimePickerPresenter(view, validator, null, null, null);
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).showDatePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_invalidDate() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(false);

        // When
        presenter = new DateTimePickerPresenter(view, validator, "spam", null, null);
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).showDatePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_validDate_nullTime() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(true);
        when(validator.isValidTime(anyString())).thenReturn(false);

        // When
        presenter = new DateTimePickerPresenter(view, validator, "1984-04-20", null, null);
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).showTimePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_validDate_invalidTime() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(true);
        when(validator.isValidTime(anyString())).thenReturn(false);

        // When
        presenter = new DateTimePickerPresenter(view, validator, "1984-04-20", "bar", null);
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).showTimePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_validDate_validTime_nullTimezone() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(true);
        when(validator.isValidTime(anyString())).thenReturn(true);
        when(validator.isValidTimezone(anyString())).thenReturn(false);

        // When
        presenter = new DateTimePickerPresenter(view, validator, "1984-04-20", "19:37:18", null);
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).showTimezonePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_validDate_validTime_invalidTimezone() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(true);
        when(validator.isValidTime(anyString())).thenReturn(true);
        when(validator.isValidTimezone(anyString())).thenReturn(false);

        // When
        presenter = new DateTimePickerPresenter(view, validator, "1984-04-20", "19:37:18", "bacon");
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).showTimezonePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldStartFirstPickerOnSubscribe_validDate_validTime_validTimezone() {
        // Given
        when(validator.isValidDate(anyString())).thenReturn(true);
        when(validator.isValidTime(anyString())).thenReturn(true);
        when(validator.isValidTimezone(anyString())).thenReturn(true);

        // When
        presenter = new DateTimePickerPresenter(view, validator, "1984-04-20", "19:37:18", "+04:30");
        presenter.subscribe();

        // Then
        verify(view).setPresenter(presenter);
        verify(view).setContent(eq(new DateTime("1984-04-20T19:37:18+04:30")));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldDismissOnCancel() {
        // When
        presenter.onCancel();

        // Then
        verify(view).dismiss();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldDisplayNextPicker_dateSelected() {
        // Given
        shouldStartFirstPickerOnSubscribe_nullDate();
        when(validator.isValidDate(anyString())).thenReturn(true);

        // When
        presenter.onDateSelected("1987-01-25");

        // Then
        verify(view).showTimePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldThrowException_invalidDateSelected() {
        // Given
        shouldStartFirstPickerOnSubscribe_nullDate();

        // When
        presenter.onDateSelected("Whatever");

        // Then
        verify(view).setError(any(IllegalArgumentException.class));
        verifyNoMoreInteractions(view);
    }


    @Test
    public void shouldDisplayNextPicker_timeSelected() {
        // Given
        shouldStartFirstPickerOnSubscribe_validDate_nullTime();
        when(validator.isValidTime(anyString())).thenReturn(true);

        // When
        presenter.onTimeSelected("12:34:56.789");

        // Then
        verify(view).showTimezonePicker();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldThrowException_invalidTimeSelected() {
        // Given
        shouldStartFirstPickerOnSubscribe_validDate_nullTime();

        // When
        presenter.onTimeSelected("mqoguhsp");

        // Then
        verify(view).setError(any(IllegalArgumentException.class));
        verifyNoMoreInteractions(view);
    }


    @Test
    public void shouldDisplayNextPicker_timezoneSelected() {
        // Given
        shouldStartFirstPickerOnSubscribe_validDate_validTime_nullTimezone();
        when(validator.isValidTimezone(anyString())).thenReturn(true);

        // When
        presenter.onTimezoneSelected("-09:34");

        // Then
        verify(view).setContent(any(DateTime.class));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldThrowException_invalidTimezoneSelected() {
        // Given
        shouldStartFirstPickerOnSubscribe_validDate_validTime_nullTimezone();

        // When
        presenter.onTimezoneSelected("Ooops");

        // Then
        verify(view).setError(any(IllegalArgumentException.class));
        verifyNoMoreInteractions(view);
    }

}