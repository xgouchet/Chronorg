package fr.xgouchet.chronorg.ui.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.model.Project;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectDetailsFragment extends Fragment implements ProjectDetailsContract.View {

    private ProjectDetailsContract.Presenter projectPresenter;


    @BindView(R.id.description) TextView description;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.project_content) RecyclerView contentList;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, container, false);

        bind(this, view);

        return view;
    }

    @Override public void onResume() {
        super.onResume();
        projectPresenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        projectPresenter.unsubscribe();
    }

    @Override public void setPresenter(@NonNull ProjectDetailsContract.Presenter presenter) {
        projectPresenter = presenter;
    }

    @Override public void setLoading(boolean active) {

    }

    @Override public void setEmpty() {
        getActivity().finish();
    }

    @Override public void setError() {

    }

    @Override public void setContent(@NonNull Project project) {
        description.setText(project.getDescription());
        toolbar.setTitle(project.getName());
    }
}
