package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.activities.ProjectEditActivity;
import fr.xgouchet.chronorg.ui.activities.ProjectDetailsActivity;
import fr.xgouchet.chronorg.ui.adapters.ProjectsAdapter;
import fr.xgouchet.chronorg.ui.contracts.ProjectListContract;
import fr.xgouchet.chronorg.ui.viewholders.ProjectViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectListFragment extends Fragment
        implements ProjectListContract.View,
        ProjectViewHolder.Listener {

    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private final ProjectsAdapter adapter;

    private ProjectListContract.Presenter presenter;

    public ProjectListFragment() {
        this.adapter = new ProjectsAdapter(new ArrayList<Project>(), this);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        bind(this, view);

        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
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

    @Override public void setPresenter(@NonNull ProjectListContract.Presenter presenter) {
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

    @Override public void setError(@Nullable Throwable throwable) {
        message.setText(R.string.error_projects_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setContent(@NonNull List<Project> projects) {
        adapter.update(projects);
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override public void showCreateItemUi() {
        // TODO handle tablet
        Intent intent = ProjectEditActivity.intentNewProject(getActivity());
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.fab) public void onCreateNewProject() {
        presenter.createNewItem();
    }

    @Override public void onProjectSelected(@NonNull Project project) {
        presenter.itemSelected(project);
    }

    @Override public void showItem(@NonNull Project item) {
        Intent intent = ProjectDetailsActivity.buildIntent(getActivity(), item);

        getActivity().startActivity(intent);
    }
}
