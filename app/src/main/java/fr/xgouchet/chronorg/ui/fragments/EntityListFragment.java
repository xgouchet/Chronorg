package fr.xgouchet.chronorg.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import fr.xgouchet.chronorg.ui.adapters.EntitiesAdapter;
import fr.xgouchet.chronorg.ui.contracts.EntityListContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityListFragment extends Fragment
        implements EntityListContract.View {

    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private final EntitiesAdapter adapter;
    private EntityListContract.Presenter presenter;

    public EntityListFragment() {
        this.adapter = new EntitiesAdapter(new ArrayList<Entity>(), this);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entity_list, container, false);

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

    @Override public void showCreateUi() {

    }

    @Override public void showEntity(@NonNull Entity entity) {

    }

    @Override public void setPresenter(@NonNull EntityListContract.Presenter presenter) {
        this.presenter = presenter;
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

    @Override public void setError() {
        message.setText(R.string.error_entities_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setContent(@NonNull List<Entity> content) {

    }

    @OnClick(R.id.fab) public void onAddContent() {
        presenter.createEntity();
    }
}
