package fr.xgouchet.chronorg.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.contracts.ProjectEditContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectEditFragment extends Fragment
        implements ProjectEditContract.View {

    private ProjectEditContract.Presenter presenter;

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_name_layout) TextInputLayout inputNameLayout;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_project, container, false);

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

    @Override public void setPresenter(@NonNull ProjectEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Project project) {
        inputName.setText(project.getName());
    }

    @Override public void projectSaved() {
        getActivity().finish();
    }

    @Override public void projectSaveError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void saveProject() {
        String inputNameText = inputName.getText().toString().trim();

        presenter.saveProject(inputNameText);
    }
}
