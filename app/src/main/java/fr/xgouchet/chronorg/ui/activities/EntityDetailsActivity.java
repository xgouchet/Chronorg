package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ENTITY = "entity";

    public static Intent buildIntent(@NonNull Context context, @NonNull Entity entity) {
        Intent intent = new Intent(context, EntityDetailsActivity.class);
        intent.putExtra(EXTRA_ENTITY, entity);
        return intent;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_details);
        bind(this);


        // Get project from intent
        Entity entity = getIntent().getParcelableExtra(EXTRA_ENTITY);
        if (entity == null) {
            Toast.makeText(this, R.string.error_entity_details_empty, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(entity.getName());
    }
}
