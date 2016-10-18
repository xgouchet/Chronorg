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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.activities.EntityDetailsActivity;
import fr.xgouchet.chronorg.ui.activities.EntityEditActivity;
import fr.xgouchet.chronorg.ui.adapters.EntitiesAdapter;
import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;
import fr.xgouchet.chronorg.ui.viewholders.EntityViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityListFragment extends Fragment
        implements BaseListView<Entity>,
        EntityViewHolder.Listener {

    private static final String ARGUMENT_PROJECT_ID = "project_id";


    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private int projectId = -1;
    @Nullable private BaseListPresenter<Entity> presenter;

    /*package*/ final EntitiesAdapter adapter;

    public static EntityListFragment createFragment(int projectId) {
        EntityListFragment fragment = new EntityListFragment();

        Bundle args = new Bundle(1);
        args.putInt(ARGUMENT_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    public EntityListFragment() {
        this.adapter = new EntitiesAdapter(new ArrayList<Entity>(), this);
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if ((args != null) && args.containsKey(ARGUMENT_PROJECT_ID)) {
            projectId = args.getInt(ARGUMENT_PROJECT_ID);
        }
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
        if (presenter != null) presenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        if (presenter != null) presenter.unsubscribe();
    }

    @Override public void setPresenter(@NonNull BaseListPresenter<Entity> presenter) {
        this.presenter = presenter;
    }

    @Override public void showCreateItemUi() {
        Intent intent = EntityEditActivity.intentNewEntity(getActivity(), projectId);
        getActivity().startActivity(intent);
    }

    @Override public void showItem(@NonNull Entity entity) {
        Intent intent = EntityDetailsActivity.buildIntent(getActivity(), entity);
        getActivity().startActivity(intent);
    }

    @Override public void setLoading(boolean active) {
        loading.setVisibility(active ? View.VISIBLE : View.GONE);
        fab.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override public void setEmpty() {
        message.setText(R.string.empty_entities_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setError(@Nullable Throwable throwable) {
        message.setText(R.string.error_entities_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }


    @Override public void setContent(@NonNull List<Entity> entities) {
        adapter.update(entities);
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override public void onEntitySelected(@NonNull Entity entity) {
        presenter.itemSelected(entity);
    }

    @OnClick(R.id.fab) public void onFabClicked() {
        presenter.createNewItem();
    }
}
