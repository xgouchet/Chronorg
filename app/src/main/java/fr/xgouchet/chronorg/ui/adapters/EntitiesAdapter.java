package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;
import fr.xgouchet.chronorg.ui.viewholders.EntityViewHolder;

/**
 * @author Xavier Gouchet
 */
public class EntitiesAdapter extends BaseSimpleAdapter<Entity, EntityViewHolder> {

    @NonNull private final BaseViewHolder.Listener<Entity> listener;
    @NonNull private final Formatter<ReadableInstant> formatter;

    public EntitiesAdapter(@NonNull BaseViewHolder.Listener<Entity> listener,
                           @NonNull Formatter<ReadableInstant> formatter) {
        this.listener = listener;
        this.formatter = formatter;
    }

    @Override protected EntityViewHolder instantiateViewHolder(int viewType, View view) {
        return new EntityViewHolder(listener, view, formatter);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_base;
    }

}
