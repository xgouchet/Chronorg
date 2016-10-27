package fr.xgouchet.chronorg.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import fr.xgouchet.chronorg.R;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseFragmentActivity<T, F extends Fragment> extends BaseActivity {

    protected F fragment;

    protected T item;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prepare content view
        setContentView(R.layout.activity_base);
        fragment = createFragment();

        if (fragment instanceof DialogFragment) {
            ((DialogFragment) fragment).show(getSupportFragmentManager(), "");
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        // get item from intent
        item = readItem(getIntent());

    }

    @NonNull protected abstract T readItem(@Nullable Intent intent);


    @NonNull protected abstract F createFragment();
}
