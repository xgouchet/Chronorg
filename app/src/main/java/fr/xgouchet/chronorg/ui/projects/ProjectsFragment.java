package fr.xgouchet.chronorg.ui.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.model.Project;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */

public class ProjectsFragment extends Fragment implements ProjectsContract.View {



    @BindView(android.R.id.list)
    RecyclerView list;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.message)
    TextView message;

    private ProjectsContract.Presenter presenter;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

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

    @Override public void setPresenter(ProjectsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setLoading(boolean active) {
        loading.setVisibility(active ? View.VISIBLE : View.GONE);
        fab.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override public void setEmpty() {
        message.setText(R.string.empty_projects_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setError() {
        message.setText(R.string.error_projects_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setContent(List<Project> projects) {
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        // TODO update adapter
    }

    @Override public void showCreateUi() {

    }

    @OnClick(R.id.fab)
    public void onCreateNewProject(View view) {
        presenter.createProject();
    }
}
