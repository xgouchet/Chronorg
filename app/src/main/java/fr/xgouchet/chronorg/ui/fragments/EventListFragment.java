package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.activities.EventEditActivity;
import fr.xgouchet.chronorg.ui.adapters.EventsAdapter;

/**
 * @author Xavier Gouchet
 */
public class EventListFragment extends BaseListFragment<Event, EventsAdapter> {

    private static final String ARGUMENT_PROJECT_ID = "project_id";

    private int projectId = -1;

    public static EventListFragment createFragment(int projectId) {
        EventListFragment fragment = new EventListFragment();

        Bundle args = new Bundle(1);
        args.putInt(ARGUMENT_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected EventsAdapter getAdapter() {
        return new EventsAdapter(new ArrayList<Event>(), this);
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if ((args != null) && args.containsKey(ARGUMENT_PROJECT_ID)) {
            projectId = args.getInt(ARGUMENT_PROJECT_ID);
        }
    }

    @Override public void showCreateItemUi() {
        // TODO handle tablet
        Intent intent = EventEditActivity.intentNewEvent(getActivity(), projectId);
        getActivity().startActivity(intent);
    }

    @Override public void showItem(@NonNull Event item) {
        Intent intent = EventEditActivity.intentEditEvent(getActivity(), item);
        getActivity().startActivity(intent);
    }

}
