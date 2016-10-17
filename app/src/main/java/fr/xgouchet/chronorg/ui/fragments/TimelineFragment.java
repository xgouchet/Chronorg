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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import fr.xgouchet.chronorg.ui.adapters.TimelineShardsAdapter;
import fr.xgouchet.chronorg.ui.contracts.TimelineContract;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class TimelineFragment extends Fragment implements TimelineContract.View {


    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private TimelineContract.Presenter presenter;
    private final TimelineShardsAdapter adapter;

    public static TimelineFragment createFragment() {
        return new TimelineFragment();
    }

    public TimelineFragment() {
        this.adapter = new TimelineShardsAdapter(new ArrayList<TimelineShard>());
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        bind(this, view);

        fab.setVisibility(View.GONE);
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

    @Override public void setPresenter(@NonNull TimelineContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setLoading(boolean active) {
        loading.setVisibility(active ? View.VISIBLE : View.GONE);
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


    @Override public void setContent(@NonNull List<TimelineShard> shards) {
        adapter.update(shards);
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override public void showCreateItemUi() {
        // ignore
    }

    @Override public void showItem(@NonNull TimelineShard item) {
        // ignore
    }
}
