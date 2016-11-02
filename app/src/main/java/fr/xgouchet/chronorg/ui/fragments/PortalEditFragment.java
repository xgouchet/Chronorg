package fr.xgouchet.chronorg.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.Interval;
import org.joda.time.ReadableInstant;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.ui.activities.DateTimePickerActivity;
import fr.xgouchet.chronorg.ui.contracts.PortalEditContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalEditFragment extends Fragment
        implements PortalEditContract.View,
        ColorPickerSwatch.OnColorSelectedListener {

    private static final int REQUEST_PORTAL_DELAY_FROM = 42;
    private static final int REQUEST_PORTAL_DELAY_TO = 666;

    private Formatter<ReadableInstant> formatter;
    private PortalEditContract.Presenter presenter;

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_color) View inputColor;
    @BindView(R.id.input_portal_from) TextView inputDelayFrom;
    @BindView(R.id.input_portal_to) TextView inputDelayTo;
    @BindView(R.id.input_direction) Spinner inputDirection;

    private int[] colors;
    private Interval delay;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
        colors = getResources().getIntArray(R.array.pickable_colors);
        presenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.save:
                savePortal();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PORTAL_DELAY_FROM) {
                String start = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                presenter.setDelayStart(start);
            } else if (requestCode == REQUEST_PORTAL_DELAY_TO) {
                String end = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                presenter.setDelayEnd(end);
            }
        }
    }

    @OnFocusChange(R.id.input_name) void onNameFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            presenter.setName(inputName.getText().toString());
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
        // TODO find nearest selected color
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                colors, 0, 4, ColorPickerDialog.SIZE_SMALL);
        dialog.setOnColorSelectedListener(this);
        dialog.show(getActivity().getFragmentManager(), "foo");
    }

    @OnItemSelected(R.id.input_direction) void onItemSelected(int position) {
        presenter.setDirection(getDirectionAt(position));
    }

    @Override public void onColorSelected(@ColorInt int color) {
        presenter.setColor(color);
        inputColor.setBackgroundColor(color);
    }

    private void savePortal() {
        String inputNameText = inputName.getText().toString().trim();

        presenter.savePortal(inputNameText);
    }

    @Override public void setPresenter(@NonNull PortalEditContract.Presenter presenter) {
        this.presenter = presenter;
        formatter = presenter.getReadableInstantFormatter();
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Portal portal) {
    }

    @Override
    public void setContent(@NonNull String name,
                           @NonNull Interval delay,
                           @Portal.Direction int direction,
                           @ColorInt int color) {
        inputName.setText(name);
        this.delay = delay;
        inputDelayFrom.setText(formatter.format(delay.getStart()));
        inputDelayTo.setText(formatter.format(delay.getEnd()));
        inputColor.setBackgroundColor(color);
        inputDirection.setSelection(getDirectionOrdinal(direction));
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


    @Override public void portalSaved() {
        getActivity().finish();
    }

    @Override public void portalSaveError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override public void invalidName(int reason) {

    }

    @Override public void invalidDelay(int reason) {

    }
}
