package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Entity;

/**
 * @author Xavier Gouchet
 */
public class EntityViewHolder extends BaseProjectItemViewHolder<Entity> {

    @NonNull final Formatter<ReadableInstant> formatter;

    public EntityViewHolder(@NonNull Listener<Entity> listener,
                            View itemView,
                            @NonNull Formatter<ReadableInstant> formatter) {
        super(listener, itemView);
        this.formatter = formatter;
    }

    @Override protected String getName(@NonNull Entity item) {
        return item.getName();
    }

    @Override protected int getColor(@NonNull Entity item) {
        return item.getColor();
    }

    @Override protected String getInfo(@NonNull Entity item) {
        return "* " + formatter.format(item.getBirth()) + "\n† " + formatter.format(item.getDeath());
    }
}
