package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Jump;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class JumpViewHolder extends BaseViewHolder<Jump> {

    final DateTimeFormatter dtf = DateTimeFormat.forStyle("MM").withZoneUTC();

    @BindView(R.id.name) TextView name;
    @BindView(R.id.from_instant) TextView from_instant;
    @BindView(R.id.to_instant) TextView to_instant;

    public JumpViewHolder(@NonNull Listener<Jump> listener, View itemView) {
        super(listener, itemView);
        bind(this, itemView);
    }

    @Override public void onBindItem(@NonNull Jump jump) {
        name.setText(jump.getName());

        from_instant.setText(dtf.print(jump.getFrom()));
        to_instant.setText(dtf.print(jump.getTo()));
    }

    @OnClick(R.id.jump)
    public void onSelectProject() {
        fireSelected();
    }
}
