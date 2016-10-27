package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.formatters.ReadablePeriodFormatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.ui.activities.PortalEditActivity;
import fr.xgouchet.chronorg.ui.adapters.PortalsAdapter;

/**
 * @author Xavier Gouchet
 */
public class PortalListFragment extends BaseListFragment<Portal, PortalsAdapter> {

    private static final String ARGUMENT_PROJECT_ID = "project_id";

    private int projectId = -1;

    public static PortalListFragment createFragment(int projectId) {
        PortalListFragment fragment = new PortalListFragment();

        Bundle args = new Bundle(1);
        args.putInt(ARGUMENT_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected PortalsAdapter getAdapter() {
        // TODO inject formatter
        return new PortalsAdapter(this, new ReadablePeriodFormatter());
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if ((args != null) && args.containsKey(ARGUMENT_PROJECT_ID)) {
            projectId = args.getInt(ARGUMENT_PROJECT_ID);
        }
    }

    @Override public void showCreateItemUi() {
        Intent intent = PortalEditActivity.intentNewPortal(getActivity(), projectId);
        getActivity().startActivity(intent);
    }

    @Override public void showItem(@NonNull Portal item) {
        Intent intent = PortalEditActivity.intentEditPortal(getActivity(), item);
        getActivity().startActivity(intent);
    }

}
