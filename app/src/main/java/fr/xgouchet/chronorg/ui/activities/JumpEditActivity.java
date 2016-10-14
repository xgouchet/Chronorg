package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.fragments.JumpEditFragment;
import fr.xgouchet.chronorg.ui.presenters.JumpEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class JumpEditActivity extends BaseActivity {

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
        setContentView(R.layout.activity_jump_edit);
        JumpEditFragment fragment = (JumpEditFragment) getSupportFragmentManager().findFragmentById(R.id.jump_edit_fragment);


        Intent intent = getIntent();
        Jump jump = null;
        if (intent.hasExtra(EXTRA_ENTITY_ID)) {
            int entityId = intent.getIntExtra(EXTRA_ENTITY_ID, -1);
            if (entityId <= 0) {
                Toast.makeText(this, "Bad entity id", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            jump = new Jump();
            jump.setEntityId(entityId);
        } else if (intent.hasCategory(EXTRA_JUMP)) {
            jump = intent.getParcelableExtra(EXTRA_JUMP);
        }

        if (jump == null) {
            Toast.makeText(this, "Null entity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        JumpEditPresenter presenter = getActivityComponent().getJumpEditPresenter();
        presenter.setJump(jump);
        presenter.setView(fragment);

    }
}
