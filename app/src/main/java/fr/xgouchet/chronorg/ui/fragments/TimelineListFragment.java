package fr.xgouchet.chronorg.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.formatters.ReadablePeriodFormatter;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.ui.adapters.TimelinesAdapter;

/**
 * @author Xavier Gouchet
 */
public class TimelineListFragment extends BaseListFragment<Timeline, TimelinesAdapter> {

    private static final String ARGUMENT_PROJECT_ID = "project_id";

    private int projectId = -1;

    public static TimelineListFragment createFragment(int projectId) {
        TimelineListFragment fragment = new TimelineListFragment();

        Bundle args = new Bundle(1);
        args.putInt(ARGUMENT_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected TimelinesAdapter getAdapter() {
        // TODO inject formatter
        return new TimelinesAdapter(this, new ReadablePeriodFormatter());
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if ((args != null) && args.containsKey(ARGUMENT_PROJECT_ID)) {
            projectId = args.getInt(ARGUMENT_PROJECT_ID);
        }
    }

    @Override public void showCreateItemUi() {
//        Intent intent = TimelineEditActivity.intentNewTimeline(getActivity(), projectId);
//        getActivity().startActivity(intent);
    }

    @Override public void showItem(@NonNull Timeline item) {
//        Intent intent = TimelineEditActivity.intentEditTimeline(getActivity(), item);
//        getActivity().startActivity(intent);
    }

}
