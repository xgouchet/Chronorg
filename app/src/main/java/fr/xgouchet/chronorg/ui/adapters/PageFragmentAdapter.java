package fr.xgouchet.chronorg.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.activities.BaseActivity;
import fr.xgouchet.chronorg.ui.fragments.EntityListFragment;
import fr.xgouchet.chronorg.ui.fragments.EventListFragment;
import fr.xgouchet.chronorg.ui.fragments.PortalListFragment;
import fr.xgouchet.chronorg.ui.fragments.TimelineListFragment;
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;
import fr.xgouchet.chronorg.ui.presenters.EventListPresenter;
import fr.xgouchet.chronorg.ui.presenters.PortalListPresenter;
import fr.xgouchet.chronorg.ui.presenters.TimelineListPresenter;

/**
 * @author Xavier Gouchet
 */
public class PageFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_ENTITIES = 0;
    public static final int PAGE_EVENTS = 1;
    public static final int PAGE_PORTALS = 2;
    public static final int PAGE_TIMELINES = 3;

    public static final int PAGE_COUNT = 4;

    private final WeakReference<BaseActivity> activityRef;
    @NonNull private final Project project;

    public PageFragmentAdapter(@NonNull FragmentManager fm,
                               @NonNull BaseActivity activity,
                               @NonNull Project project) {
        super(fm);

        activityRef = new WeakReference<>(activity);
        this.project = project;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        BaseActivity activity = activityRef.get();
        if (activity == null) {
            return null;
        }

        switch (position) {
            case PAGE_ENTITIES: {
                final EntityListFragment fragment = EntityListFragment.createFragment(project.getId());
                EntityListPresenter presenter = activity.getActivityComponent().getEntityListPresenter();
                presenter.setProject(project);
                presenter.setView(fragment);
                return fragment;
            }
            case PAGE_EVENTS: {
                final EventListFragment fragment = EventListFragment.createFragment(project.getId());
                EventListPresenter presenter = activity.getActivityComponent().getEventListPresenter();
                presenter.setProject(project);
                presenter.setView(fragment);
                return fragment;
            }
            case PAGE_PORTALS: {
                final PortalListFragment fragment = PortalListFragment.createFragment(project.getId());
                PortalListPresenter presenter = activity.getActivityComponent().getPortalListPresenter();
                presenter.setProject(project);
                presenter.setView(fragment);
                return fragment;
            }
            case PAGE_TIMELINES: {
                final TimelineListFragment fragment = TimelineListFragment.createFragment(project.getId());
                TimelineListPresenter presenter = activity.getActivityComponent().getTimelineListPresenter();
                presenter.setProject(project);
                presenter.setView(fragment);
                return fragment;
            }

            default:
                return null;
        }
    }

    @Override public CharSequence getPageTitle(int position) {
        Context context = activityRef.get();
        if (context == null) {
            return null;
        }
        switch (position) {
            case PAGE_ENTITIES:
                return context.getString(R.string.title_entities);
            case PAGE_EVENTS:
                return context.getString(R.string.title_events);
            case PAGE_PORTALS:
                return context.getString(R.string.title_portals);
            case PAGE_TIMELINES:
                return context.getString(R.string.title_timelines);
        }


        return super.getPageTitle(position);
    }
}
