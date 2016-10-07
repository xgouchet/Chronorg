package fr.xgouchet.chronorg.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import org.joda.time.ReadableInstant;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.activities.DateTimePickerActivity;
import fr.xgouchet.chronorg.ui.contracts.EditEntityContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EditEntityFragment extends Fragment implements EditEntityContract.View {

    private static final int REQUEST_BIRTH_DATE = 42;
    private static final int REQUEST_DEATH_DATE = 666;

    private EditEntityContract.Presenter presenter;

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_description) EditText inputDescription;
    @BindView(R.id.input_birth) TextView inputBirth;
    @BindView(R.id.input_death) TextView inputDeath;
    private ReadableInstant birth;
    private ReadableInstant death;

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
        presenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_project, menu);
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

    @OnFocusChange(R.id.input_description) void onDescriptionFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            presenter.setDescription(inputDescription.getText().toString());
        }
    }

    @OnClick(R.id.input_birth) void onBirthClicked() {
        onNameFocusChanged(false);
        onDescriptionFocusChanged(false);
        Intent intent = new Intent(getActivity(), DateTimePickerActivity.class);
        startActivityForResult(intent, REQUEST_BIRTH_DATE);
    }

    @OnClick(R.id.input_death) void onDeathClicked() {
        onNameFocusChanged(false);
        onDescriptionFocusChanged(false);
        Intent intent = new Intent(getActivity(), DateTimePickerActivity.class);
        startActivityForResult(intent, REQUEST_DEATH_DATE);
    }

    private void saveProject() {
        String inputNameText = inputName.getText().toString().trim();
        String inputDescText = inputDescription.getText().toString();

        presenter.saveProject(inputNameText, inputDescText);
    }

    @Override public void setPresenter(@NonNull EditEntityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setError(@Nullable Throwable throwable) {
        // TODO Toast ?
    }

    @Override public void setContent(@NonNull Entity content) {

//        birth = content.getBirth();
//        inputBirth.setText(birth.toString());
    }

    @Override
    public void setContent(@NonNull String name, @Nullable String description, @NonNull ReadableInstant birth, @Nullable ReadableInstant death) {
        inputName.setText(name);
        inputDescription.setText(description);
        this.birth = birth;
        inputBirth.setText(birth.toString());
        this.death = death;
        inputDeath.setText(death == null ? "" : death.toString());
    }

    @Override public void entitySaved() {
        // TODO handle tablet
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
