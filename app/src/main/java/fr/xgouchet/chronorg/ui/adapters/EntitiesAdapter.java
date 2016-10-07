package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.viewholders.EntityViewHolder;

/**
 * @author Xavier Gouchet
 */
public class EntitiesAdapter extends BaseSimpleAdapter<Entity, EntityViewHolder> {

    @NonNull /*package*/ final List<Entity> entities;
    @NonNull private final EntityViewHolder.Listener listener;

    public EntitiesAdapter(List<Entity> entities, EntityViewHolder.Listener listener) {
        this.entities = entities;
        this.listener = listener;
    }


    @Override public int getItemCount() {
        return entities.size();
    }

    public void update(@NonNull final List<Entity> newContent) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() {
                return entities.size();
            }

            @Override public int getNewListSize() {
                return newContent.size();
            }

            @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return entities.get(oldItemPosition).getId() == newContent.get(newItemPosition).getId();
            }

            @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return TextUtils.equals(
                        entities.get(oldItemPosition).getName(),
                        newContent.get(newItemPosition).getName())
                        &&
                        TextUtils.equals(
                                entities.get(oldItemPosition).getDescription(),
                                newContent.get(newItemPosition).getDescription())
                        &&
                        // TODO compare values and not toString
                        TextUtils.equals(
                                entities.get(oldItemPosition).getBirth().toString(),
                                newContent.get(newItemPosition).getBirth().toString())

                        // TODO also compare nullable death
                        ;
            }
        });

        entities.clear();
        entities.addAll(newContent);
        result.dispatchUpdatesTo(this);
    }

    @Override protected EntityViewHolder instantiateViewHolder(int viewType, View view) {
        return new EntityViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_entity;
    }

    @Override protected Entity getItem(int position) {
        return entities.get(position);
    }
}
