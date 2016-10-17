package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.text.TextUtils;
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

    public interface Listener {
        void onJumpSelected(@NonNull Jump jump);
    }

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.from_instant) TextView from_instant;
    @BindView(R.id.to_instant) TextView to_instant;

    private Jump jump;

    @NonNull private final Listener listener;

    public JumpViewHolder(@NonNull Listener listener, View itemView) {
        super(itemView);
        this.listener = listener;
        bind(this, itemView);
    }

    @Override public void bindItem(@NonNull Jump jump) {
        this.jump = jump;

        name.setText(jump.getName());

        final String description = jump.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }

        from_instant.setText(dtf.print(jump.getFrom()));
        to_instant.setText(dtf.print(jump.getTo()));
    }

    @OnClick(R.id.jump)
    public void onSelectProject() {
        if (jump != null) {
            listener.onJumpSelected(jump);
        }
    }
}
