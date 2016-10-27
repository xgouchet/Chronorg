package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.fragments.EntityEditFragment;
import fr.xgouchet.chronorg.ui.presenters.EntityEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class EntityEditActivity extends BaseFragmentActivity<Entity, EntityEditFragment> {

    public static final String EXTRA_PROJECT_ID = "project_id";
    public static final String EXTRA_ENTITY = "entity";

    public static Intent intentNewEntity(@NonNull Context context, int projectId) {
        Intent intent = new Intent(context, EntityEditActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    public static Intent intentEditEntity(@NonNull Context context, @NonNull Entity entity) {
        Intent intent = new Intent(context, EntityEditActivity.class);
        intent.putExtra(EXTRA_ENTITY, entity);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EntityEditPresenter presenter = getActivityComponent().getEntityEditPresenter();
        presenter.setEntity(item);
        presenter.setView(fragment);
    }

    @NonNull @Override protected Entity readItem(@Nullable Intent intent) {
        Entity entity;
        if ((intent != null) && intent.hasExtra(EXTRA_PROJECT_ID)) {
            int projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1);
            if (projectId <= 0) {
                throw new IllegalArgumentException("Invalid project id " + projectId);
            }
            entity = new Entity();
            entity.setProjectId(projectId);
        } else if ((intent != null) && intent.hasExtra(EXTRA_ENTITY)) {
            entity = intent.getParcelableExtra(EXTRA_ENTITY);
        } else {
            throw new IllegalArgumentException("No arguments for jump");
        }
        return entity;
    }

    @NonNull @Override protected EntityEditFragment createFragment() {
        return new EntityEditFragment();
    }
}
