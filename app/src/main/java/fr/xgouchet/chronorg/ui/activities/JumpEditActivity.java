package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.fragments.JumpEditFragment;
import fr.xgouchet.chronorg.ui.presenters.JumpEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class JumpEditActivity extends BaseFragmentActivity<Jump, JumpEditFragment> {

    public static final String EXTRA_ENTITY_ID = "entity_id";
    public static final String EXTRA_JUMP = "jump";

    public static Intent intentNewJump(@NonNull Context context, int entityId) {
        Intent intent = new Intent(context, JumpEditActivity.class);
        intent.putExtra(EXTRA_ENTITY_ID, entityId);
        return intent;
    }

    public static Intent intentEditJump(@NonNull Context context, @NonNull Jump jump) {
        Intent intent = new Intent(context, JumpEditActivity.class);
        intent.putExtra(EXTRA_JUMP, jump);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();


        JumpEditPresenter presenter = getActivityComponent().getJumpEditPresenter();
        presenter.setJump(item);
        presenter.setView(fragment);

    }

    @NonNull @Override protected Jump readItem(@Nullable Intent intent) {
        Jump jump;
        if ((intent != null) && intent.hasExtra(EXTRA_ENTITY_ID)) {
            int entityId = intent.getIntExtra(EXTRA_ENTITY_ID, -1);
            if (entityId <= 0) {
                throw new IllegalArgumentException("Invalid entity id " + entityId);
            }
            jump = new Jump();
            jump.setEntityId(entityId);
        } else if ((intent != null) && intent.hasExtra(EXTRA_JUMP)) {
            jump = intent.getParcelableExtra(EXTRA_JUMP);
        } else {
            throw new IllegalArgumentException("No arguments for jump");
        }

        return jump;
    }

    @NonNull @Override protected JumpEditFragment createFragment() {
        return new JumpEditFragment();
    }
}
