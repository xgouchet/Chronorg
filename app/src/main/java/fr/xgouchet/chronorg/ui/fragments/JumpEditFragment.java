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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.activities.DateTimePickerActivity;
import fr.xgouchet.chronorg.ui.contracts.JumpEditContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class JumpEditFragment extends Fragment
        implements JumpEditContract.View {

    private static final int REQUEST_FROM_DATE = 42;
    private static final int REQUEST_TO_DATE = 666;

    final DateTimeFormatter dtf = DateTimeFormat.forStyle("MM").withZoneUTC();

    private JumpEditContract.Presenter presenter;

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_from) TextView inputFrom;
    @BindView(R.id.input_to) TextView inputTo;
    private ReadableInstant from;
    private ReadableInstant to;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_jump, container, false);

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
            if (requestCode == REQUEST_FROM_DATE) {
                String dateTime = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                presenter.setFrom(dateTime);
            } else if (requestCode == REQUEST_TO_DATE) {
                String dateTime = data.getStringExtra(DateTimePickerActivity.EXTRA_RESULT);
                presenter.setTo(dateTime);
            }
        }
    }

    @OnFocusChange(R.id.input_name) void onNameFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            presenter.setName(inputName.getText().toString());
        }
    }

    @OnClick(R.id.input_from) void onBirthClicked() {
        onNameFocusChanged(false);
        Intent intent = DateTimePickerActivity.createDateTimePicker(getActivity(), from);
        startActivityForResult(intent, REQUEST_FROM_DATE);
    }

    @OnClick(R.id.input_to) void onDeathClicked() {
        onNameFocusChanged(false);
        Intent intent = DateTimePickerActivity.createDateTimePicker(getActivity(), to);
        startActivityForResult(intent, REQUEST_TO_DATE);
    }

    private void saveProject() {
        String inputNameText = inputName.getText().toString().trim();

        presenter.saveJump(inputNameText);
    }

    @Override public void setPresenter(@NonNull JumpEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Jump content) {
    }

    @Override
    public void setContent(@Nullable String name,
                           @NonNull ReadableInstant from,
                           @NonNull ReadableInstant to) {
        inputName.setText(name);
        inputFrom.setText(dtf.print(from));
        inputTo.setText(dtf.print(to));
        this.from = from;
        this.to = to;
    }

    @Override public void jumpSaved() {
        // TODO handle tablet
        getActivity().finish();
    }

    @Override public void jumpSaveError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override public void invalidFrom(int reason) {

    }

    @Override public void invalidTo(int reason) {

    }
}
