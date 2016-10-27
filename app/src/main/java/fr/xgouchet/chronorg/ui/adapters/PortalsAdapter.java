package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.ReadablePeriod;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;
import fr.xgouchet.chronorg.ui.viewholders.PortalViewHolder;

/**
 * @author Xavier Gouchet
 */
public class PortalsAdapter extends BaseSimpleAdapter<Portal, PortalViewHolder> {


    @NonNull private final BaseViewHolder.Listener<Portal> listener;
    @NonNull private final Formatter<ReadablePeriod> formatter;

    public PortalsAdapter(@NonNull BaseViewHolder.Listener<Portal> listener,
                          @NonNull Formatter<ReadablePeriod> formatter) {
        this.listener = listener;
        this.formatter = formatter;
    }

    @Override protected PortalViewHolder instantiateViewHolder(int viewType, View view) {
        return new PortalViewHolder(listener, view, formatter);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_base;
    }

}
