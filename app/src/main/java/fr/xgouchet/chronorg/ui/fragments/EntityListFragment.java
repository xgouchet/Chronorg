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
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.activities.EditEntityActivity;
import fr.xgouchet.chronorg.ui.activities.EntityDetailsActivity;
import fr.xgouchet.chronorg.ui.adapters.EntitiesAdapter;
import fr.xgouchet.chronorg.ui.contracts.EntityListContract;
import fr.xgouchet.chronorg.ui.viewholders.EntityViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityListFragment extends Fragment implements EntityListContract.View, EntityViewHolder.Listener {

    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private int projectId = -1;
    private EntityListContract.Presenter presenter;

    /*package*/ final EntitiesAdapter adapter;

    public EntityListFragment() {
        this.adapter = new EntitiesAdapter(new ArrayList<Entity>(), this);
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

    @Override public void setPresenter(@NonNull EntityListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showCreateItemUi() {
        Intent intent = EditEntityActivity.intentNewEntity(getActivity(), projectId);
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
}
