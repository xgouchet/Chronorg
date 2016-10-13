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
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;

/**
 * @author Xavier Gouchet
 */
public class PageFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_ENTITIES = 0;

    public static final int PAGE_COUNT = 1;

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
            case PAGE_ENTITIES:
                final EntityListFragment fragment = EntityListFragment.createFragment(project.getId());
                EntityListPresenter presenter = activity.getActivityComponent().getEntityListPresenter();
                presenter.setProject(project);
                presenter.setView(fragment);
                return fragment;
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
        }


        return super.getPageTitle(position);
    }
}
