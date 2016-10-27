package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.formatters.ReadableInstantFormatter;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.activities.EntityDetailsActivity;
import fr.xgouchet.chronorg.ui.activities.EntityEditActivity;
import fr.xgouchet.chronorg.ui.adapters.EntitiesAdapter;

/**
 * @author Xavier Gouchet
 */
public class EntityListFragment extends BaseListFragment<Entity, EntitiesAdapter> {

    private static final String ARGUMENT_PROJECT_ID = "project_id";

    private int projectId = -1;

    public static EntityListFragment createFragment(int projectId) {
        EntityListFragment fragment = new EntityListFragment();

        Bundle args = new Bundle(1);
        args.putInt(ARGUMENT_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if ((args != null) && args.containsKey(ARGUMENT_PROJECT_ID)) {
            projectId = args.getInt(ARGUMENT_PROJECT_ID);
        }
    }

    @Override protected EntitiesAdapter getAdapter() {
        // TODO inject formatter
        return new EntitiesAdapter(this, new ReadableInstantFormatter());
    }

    @Override public void showCreateItemUi() {
        Intent intent = EntityEditActivity.intentNewEntity(getActivity(), projectId);
        getActivity().startActivity(intent);
    }

    @Override public void showItem(@NonNull Entity entity) {
        Intent intent = EntityDetailsActivity.buildIntent(getActivity(), entity);
        getActivity().startActivity(intent);
    }

}
