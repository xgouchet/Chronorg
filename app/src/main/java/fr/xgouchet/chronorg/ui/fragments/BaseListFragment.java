package fr.xgouchet.chronorg.ui.fragments;

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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.ui.adapters.BaseSimpleAdapter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseListView;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseListPresenter;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseListFragment<T, A extends BaseSimpleAdapter<T, ? extends BaseViewHolder<T>>>
        extends Fragment
        implements BaseListView<T>,
        BaseViewHolder.Listener<T> {


    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    @Nullable private BaseListPresenter<T> presenter;

    /*package*/ final A adapter;

    protected BaseListFragment() {
        this.adapter = getAdapter();
    }

    protected abstract A getAdapter();


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

    @Override public void setPresenter(@NonNull BaseListPresenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override public void setLoading(boolean active) {
        loading.setVisibility(active ? View.VISIBLE : View.GONE);
        fab.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override public void setEmpty() {
        message.setText(R.string.empty_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setError(@Nullable Throwable throwable) {
        message.setText(getString(R.string.error_generic, throwable.getMessage()));
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }


    @Override public void setContent(@NonNull List<T> entities) {
        adapter.update(entities);
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override public void onSelected(@NonNull T item) {
        presenter.itemSelected(item);
    }

    @OnClick(R.id.fab) public void onFabClicked() {
        if (presenter != null) presenter.createNewItem();
    }
}
