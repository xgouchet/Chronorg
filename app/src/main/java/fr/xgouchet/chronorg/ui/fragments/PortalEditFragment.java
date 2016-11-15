package fr.xgouchet.chronorg.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableInstant;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import fr.xgouchet.chronorg.ChronorgApplication;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.ui.activities.DateTimePickerActivity;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseEditPresenter;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalEditFragment extends BaseEditFragment<Portal> {

    private static final int REQUEST_PORTAL_DELAY_FROM = 42;
    private static final int REQUEST_PORTAL_DELAY_TO = 666;

    private Formatter<ReadableInstant> formatter;
    private BaseEditPresenter<Portal> presenter;

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_color) View inputColor;
    @BindView(R.id.input_portal_from) TextView inputDelayFrom;
    @BindView(R.id.input_portal_to) TextView inputDelayTo;
    @BindView(R.id.input_direction) Spinner inputDirection;


    private Interval delay;
    private Portal portal;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_portal, container, false);

        bind(this, view);
        return view;
    }

    @Override public void onResume() {
        super.onResume();
        formatter = ((ChronorgApplication) getActivity().getApplicationContext())
                .getChronorgComponent().getReadableInstantFormatter();
        presenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PORTAL_DELAY_FROM) {
                String start = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                portal.setDelay(safeInterval(new DateTime(start), portal.getDelay().getEnd()));
                presenter.setItemValue(portal);
            } else if (requestCode == REQUEST_PORTAL_DELAY_TO) {
                String end = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                portal.setDelay(safeInterval(portal.getDelay().getStart(), new DateTime(end)));
                presenter.setItemValue(portal);
            }
        }
    }

    @NonNull
    private Interval safeInterval(@NonNull DateTime start, @NonNull DateTime end) {
        if (start.isAfter(end)) {
            return new Interval(end, start);
        } else if (start.isEqual(end)) {
            return new Interval(start, end.plusSeconds(1));
        } else {
            return new Interval(start, end);
        }
    }

    @OnFocusChange(R.id.input_name) void onNameFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            portal.setName(inputName.getText().toString());
            presenter.setItemValue(portal);
        }
    }

    @OnClick(R.id.input_portal_from) void onDelayFromClicked() {
        onNameFocusChanged(false);
        Intent intent = DateTimePickerActivity.createDateTimePicker(getActivity(), delay.getStart());
        startActivityForResult(intent, REQUEST_PORTAL_DELAY_FROM);
    }

    @OnClick(R.id.input_portal_to) void onDelayToClicked() {
        onNameFocusChanged(false);
        Intent intent = DateTimePickerActivity.createDateTimePicker(getActivity(), delay.getEnd());
        startActivityForResult(intent, REQUEST_PORTAL_DELAY_TO);
    }

    @OnClick(R.id.input_color) void onColorClicked() {
        onNameFocusChanged(false);
        pickColor();
    }

    @OnItemSelected(R.id.input_direction) void onItemSelected(int position) {
        portal.setDirection(getDirectionAt(position));
        presenter.setItemValue(portal);
    }

    @Override public void onColorSelected(@ColorInt int color) {
        portal.setColor(color);
        presenter.setItemValue(portal);
    }

    @Override protected void onSaveItemClicked() {
        onNameFocusChanged(false);
        presenter.setItemValue(portal);

        presenter.saveItem();
    }

    @Override protected void onDeleteItemClicked() {
        presenter.deleteItem();
    }

    @Override public void setPresenter(@NonNull BaseEditPresenter<Portal> presenter) {
        this.presenter = presenter;
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Portal portal) {
        this.portal = portal;
        refreshContent();
    }

    @Override protected boolean isDeletable() {
        return this.portal.getId() > 0;
    }

    @UiThread
    private void refreshContent() {

        inputName.setText(portal.getName());
        this.delay = portal.getDelay();
        inputDelayFrom.setText(formatter.format(delay.getStart()));
        inputDelayTo.setText(formatter.format(delay.getEnd()));
        inputColor.setBackgroundColor(portal.getColor());
        inputDirection.setSelection(getDirectionOrdinal(portal.getDirection()));
    }

    private int getDirectionOrdinal(@Portal.Direction int direction) {
        switch (direction) {
            case Portal.Direction.FUTURE:
                return 0;
            case Portal.Direction.PAST:
                return 1;
            case Portal.Direction.BOTH:
            default:
                return 2;
        }
    }

    @Portal.Direction private int getDirectionAt(int position) {
        switch (position) {
            case 0:
                return Portal.Direction.FUTURE;
            case 1:
                return Portal.Direction.PAST;
            case 2:
            default:
                return Portal.Direction.BOTH;
        }
    }


    @Override public void dismiss() {
        getActivity().finish();
    }

    @Override public void onError(@Nullable Throwable t) {
        Toast.makeText(getActivity(), t != null ? t.getMessage() : "Oups", Toast.LENGTH_LONG).show();
    }

}
