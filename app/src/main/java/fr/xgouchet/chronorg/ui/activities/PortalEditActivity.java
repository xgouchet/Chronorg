package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.ui.fragments.PortalEditFragment;
import fr.xgouchet.chronorg.ui.presenters.PortalEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class PortalEditActivity extends BaseFragmentActivity<Portal, PortalEditFragment> {

    public static final String EXTRA_PROJECT_ID = "project_id";
    private static final String EXTRA_PORTAL = "portal";

    public static Intent intentNewPortal(@NonNull Context context, int projectId) {
        Intent intent = new Intent(context, PortalEditActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    public static Intent intentEditPortal(@NonNull Context context, @NonNull Portal portal) {
        Intent intent = new Intent(context, PortalEditActivity.class);
        intent.putExtra(EXTRA_PORTAL, portal);
        return intent;
    }


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup BasePresenter
        PortalEditPresenter presenter = getActivityComponent().getPortalEditPresenter();
        presenter.setPortal(item);
        presenter.setView(fragment);
    }

    @NonNull @Override protected Portal readItem(@Nullable Intent intent) {
        Portal portal;


        if ((intent != null) && intent.hasExtra(EXTRA_PROJECT_ID)) {
            int projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1);
            if (projectId <= 0) {
                throw new IllegalArgumentException("Invalid project id " + projectId);
            }
            portal = new Portal();
            portal.setProjectId(projectId);
        } else if ((intent != null) && intent.hasExtra(EXTRA_PORTAL)) {
            portal = intent.getParcelableExtra(EXTRA_PORTAL);
        } else {
            throw new IllegalArgumentException("No arguments for portal");
        }
        return portal;
    }

    @NonNull @Override protected PortalEditFragment createFragment() {
        return new PortalEditFragment();
    }
}
