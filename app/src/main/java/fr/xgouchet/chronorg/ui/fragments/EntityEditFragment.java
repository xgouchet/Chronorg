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
import android.widget.TextView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.activities.DateTimePickerActivity;
import fr.xgouchet.chronorg.ui.contracts.EntityEditContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityEditFragment extends Fragment
        implements EntityEditContract.View,
        ColorPickerSwatch.OnColorSelectedListener {

    private static final int REQUEST_BIRTH_DATE = 42;
    private static final int REQUEST_DEATH_DATE = 666;

    final DateTimeFormatter dtf = DateTimeFormat.forStyle("MM").withZoneUTC();

    private EntityEditContract.Presenter presenter;

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_birth) TextView inputBirth;
    @BindView(R.id.input_death) TextView inputDeath;
    @BindView(R.id.input_color) View inputColor;

    private ReadableInstant birth;
    private ReadableInstant death;
    private int[] colors;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_entity, container, false);

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
                saveProject();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_BIRTH_DATE) {
                String dateTime = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                presenter.setBirth(dateTime);
            } else if (requestCode == REQUEST_DEATH_DATE) {
                String dateTime = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                presenter.setDeath(dateTime);
            }
        }
    }

    @OnFocusChange(R.id.input_name) void onNameFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            presenter.setName(inputName.getText().toString());
        }
    }

    @OnClick(R.id.input_birth) void onBirthClicked() {
        onNameFocusChanged(false);
        Intent intent = DateTimePickerActivity.createDateTimePicker(getActivity(), birth);
        startActivityForResult(intent, REQUEST_BIRTH_DATE);
    }

    @OnClick(R.id.input_death) void onDeathClicked() {
        onNameFocusChanged(false);
        Intent intent = DateTimePickerActivity.createDateTimePicker(getActivity(), death);
        startActivityForResult(intent, REQUEST_DEATH_DATE);
    }

    @OnClick(R.id.input_color) void onColorClicked() {
        onNameFocusChanged(false);
        // TODO find nearest selected color
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                colors, 0, 4, ColorPickerDialog.SIZE_SMALL);
        dialog.setOnColorSelectedListener(this);
        dialog.show(getActivity().getFragmentManager(), "foo");
    }

    @Override public void onColorSelected(@ColorInt int color) {
        presenter.setColor(color);
        inputColor.setBackgroundColor(color);
    }

    private void saveProject() {
        String inputNameText = inputName.getText().toString().trim();

        presenter.saveEntity(inputNameText);
    }

    @Override public void setPresenter(@NonNull EntityEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Entity content) {
    }

    @Override
    public void setContent(@NonNull String name,
                           @Nullable String description,
                           @NonNull ReadableInstant birth,
                           @NonNull ReadableInstant death,
                           @ColorInt int color) {
        inputName.setText(name);
        this.birth = birth;
        inputBirth.setText(dtf.print(birth));
        this.death = death;
        inputDeath.setText(dtf.print(death));
        inputColor.setBackgroundColor(color);
    }

    @Override public void entitySaved() {
        getActivity().finish();
    }

    @Override public void entitySaveError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override public void invalidName(int reason) {

    }

    @Override public void invalidBirth(int reason) {

    }
}
