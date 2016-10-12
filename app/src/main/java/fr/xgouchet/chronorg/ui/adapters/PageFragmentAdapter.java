package fr.xgouchet.chronorg.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.provider.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.ui.fragments.EntityListFragment;
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;

/**
 * @author Xavier Gouchet
 */
public class PageFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_ENTITIES = 0;

    public static final int PAGE_COUNT = 1;

    private final WeakReference<Context> contextRef;
    @NonNull private final Project project;

    public PageFragmentAdapter(@NonNull FragmentManager fm,
                               @NonNull Context context,
                               @NonNull Project project) {
        super(fm);

        contextRef = new WeakReference<>(context);
        this.project = project;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Context context = contextRef.get();
        if (context == null) {
            return null;
        }

        // TODO inject presenters
        switch (position) {
            case PAGE_ENTITIES:
                EntityRepository entityRepository = new EntityRepository(context, new EntityIOProvider());
                final EntityListFragment fragment = EntityListFragment.createFragment(project.getId());
                new EntityListPresenter(entityRepository, fragment, project);
                return fragment;
            default:
                return null;
        }
    }

    @Override public CharSequence getPageTitle(int position) {
        Context context = contextRef.get();
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
