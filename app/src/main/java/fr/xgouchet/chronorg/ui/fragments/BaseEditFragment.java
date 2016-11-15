package fr.xgouchet.chronorg.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.ui.contracts.views.BaseEditView;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseEditFragment<T>
        extends Fragment
        implements BaseEditView<T>,
        ColorPickerSwatch.OnColorSelectedListener {

    private int[] pickableColors;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
        menu.findItem(R.id.delete).setVisible(isDeletable());
    }

    protected abstract boolean isDeletable();

    @Override public void onResume() {
        super.onResume();
        pickableColors = getResources().getIntArray(R.array.pickable_colors);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.save:
                onSaveItemClicked();
                break;
            case R.id.delete:
                new AlertDialog.Builder(getContext())
                        .setMessage(R.string.confirm_delete)
                        .setTitle("Hey !")
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                onDeleteItemClicked();
                            }
                        })
                        .setNeutralButton(android.R.string.cancel, null)
                        .create()
                        .show();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

    protected void pickColor() {
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                pickableColors, 0, 4, ColorPickerDialog.SIZE_SMALL);
        dialog.setOnColorSelectedListener(this);
        dialog.show(getActivity().getFragmentManager(), "foo");
    }

    protected abstract void onDeleteItemClicked();

    protected abstract void onSaveItemClicked();

}
