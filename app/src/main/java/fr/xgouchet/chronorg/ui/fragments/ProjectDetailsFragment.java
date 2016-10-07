package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.activities.EditEntityActivity;
import fr.xgouchet.chronorg.ui.adapters.EntitiesAdapter;
import fr.xgouchet.chronorg.ui.contracts.EntityListContract;
import fr.xgouchet.chronorg.ui.contracts.ProjectDetailsContract;
import fr.xgouchet.chronorg.ui.viewholders.EntityViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectDetailsFragment extends Fragment implements EntityViewHolder.Listener {


    @BindView(R.id.description) TextView description;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    /*package*/ ProjectDetailsContract.Presenter projectPresenter;
    /*package*/ EntityListContract.Presenter entityListPresenter;

    private final ProjectDetailsView projectDetailsView = new ProjectDetailsView();
    private final EntityListView entityListView = new EntityListView();
    /*package*/ int projectId = -1;

    /*package*/ final EntitiesAdapter adapter;

    public ProjectDetailsFragment() {
        adapter = new EntitiesAdapter(new ArrayList<Entity>(), this);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, container, false);

        bind(this, view);

        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);

        return view;
    }

    @Override public void onResume() {
        super.onResume();
        projectPresenter.subscribe();
        entityListPresenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        projectPresenter.unsubscribe();
        entityListPresenter.unsubscribe();
    }

    public ProjectDetailsView getProjectDetailsView() {
        return projectDetailsView;
    }

    public EntityListView getEntityListView() {
        return entityListView;
    }

    @OnClick(R.id.fab) public void onCreateNewEntity() {
        entityListPresenter.createNewItem();
    }

    @Override public void onEntitySelected(@NonNull Entity project) {

    }

    /*package*/ class ProjectDetailsView implements ProjectDetailsContract.View {

        @Override public void setPresenter(@NonNull ProjectDetailsContract.Presenter presenter) {
            projectPresenter = presenter;
        }

        @Override public void setError(@Nullable Throwable throwable) {
            // TODO toast
            getActivity().finish();
        }

        @Override public void setContent(@NonNull Project project) {
            projectId = project.getId();
            description.setText(project.getDescription());
            toolbar.setTitle(project.getName());
        }

    }

    /*package*/ class EntityListView implements EntityListContract.View {

        @Override public void setPresenter(@NonNull EntityListContract.Presenter presenter) {
            entityListPresenter = presenter;
        }

        @Override public void showCreateItemUi() {
            Intent intent = EditEntityActivity.intentNewEntity(getActivity(), projectId);
            getActivity().startActivity(intent);
        }

        @Override public void showItem(@NonNull Entity item) {
            // TODO
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


        @Override public void setContent(@NonNull List<Entity> entities) {
            adapter.update(entities);
            message.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }

}
