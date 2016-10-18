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
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.adapters.EventsAdapter;
import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;
import fr.xgouchet.chronorg.ui.viewholders.EventViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EventListFragment extends Fragment
        implements BaseListView<Event>,
        EventViewHolder.Listener {

    private static final String ARGUMENT_PROJECT_ID = "project_id";

    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private BaseListPresenter<Event> presenter;
    private final EventsAdapter adapter;

    private int projectId = -1;

    public static EventListFragment createFragment(int projectId) {
        EventListFragment fragment = new EventListFragment();

        Bundle args = new Bundle(1);
        args.putInt(ARGUMENT_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    public EventListFragment() {
        adapter = new EventsAdapter(new ArrayList<Event>(), this);
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
        presenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override public void setPresenter(@NonNull BaseListPresenter<Event> presenter) {
        this.presenter = presenter;
    }

    @Override public void setLoading(boolean active) {
        loading.setVisibility(active ? View.VISIBLE : View.GONE);
        fab.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override public void setEmpty() {
        message.setText(R.string.empty_events_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setError(@Nullable Throwable throwable) {
        message.setText(R.string.error_events_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setContent(@NonNull List<Event> jumps) {
        adapter.update(jumps);
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override public void showCreateItemUi() {
        // TODO handle tablet
//        Intent intent = EventEditActivity.intentNewEvent(getActivity(), entityId);
//        getActivity().startActivity(intent);
    }

    @OnClick(R.id.fab) public void onCreateNewEvent() {
        presenter.createNewItem();
    }

    @Override public void showItem(@NonNull Event item) {
//        Intent intent = EventEditActivity.intentEditEvent(getActivity(), item);
//        getActivity().startActivity(intent);
    }

    @Override public void onEventSelected(@NonNull Event event) {
        presenter.itemSelected(event);
    }
}
