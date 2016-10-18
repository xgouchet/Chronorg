package fr.xgouchet.chronorg.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import org.joda.time.DateTime;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.TimelineShard;

/**
 * @author Xavier Gouchet
 */
public class TimelineShardView extends View {

    private int lineWidth;
    private int lineStrokeWidth;
    private int lineMargin;
    private int lineCapWidth;
    private int lineCapDistanceFromTop;
    private Paint paint;

    @NonNull private TimelineShard timelineShard;

    public TimelineShardView(Context context) {
        super(context);
        init(context);
    }

    public TimelineShardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimelineShardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TimelineShardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        Resources res = context.getResources();

        lineWidth = res.getDimensionPixelSize(R.dimen.line_width);
        lineStrokeWidth = res.getDimensionPixelSize(R.dimen.line_stroke_width);
        lineCapWidth = res.getDimensionPixelSize(R.dimen.line_cap_width);
        lineCapDistanceFromTop = res.getDimensionPixelSize(R.dimen.line_cap_distance_from_top);

        lineMargin = (lineWidth - lineStrokeWidth) / 2;

        paint = new Paint();
        paint.setAntiAlias(true);

        if (isInEditMode()) {
            final DateTime instant = new DateTime("1970-01-01T00:00Z");
            timelineShard = new TimelineShard.Builder(TimelineShard.TYPE_EVENT, Color.GREEN, instant)
                    .withOngoingSegmentColor(Color.RED)
                    .withOngoingSegmentColor(Color.GREEN)
                    .withOngoingSegmentColor(Color.BLUE)
                    .withPosition(1)
                    .build();
        }
    }


    public void setTimelineShard(@NonNull TimelineShard timelineShard) {
        this.timelineShard = timelineShard;
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minWidth = timelineShard.getOngoingSegments().length * lineWidth;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(minWidth, widthSize);
        } else {
            width = minWidth;
        }

        height = heightSize;
        setMeasuredDimension(width, height);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getMeasuredHeight();
        int lineStartX = 0;
        int[] ongoingSegments = timelineShard.getOngoingSegments();
        for (int i = 0; i < ongoingSegments.length; i++) {
            int color = ongoingSegments[i];
            paint.setColor(color);

            if (timelineShard.getPosition() == i) {
                canvas.drawCircle(lineStartX + (lineWidth / 2),
                        lineCapDistanceFromTop,
                        lineCapWidth / 2,
                        paint);
                if (timelineShard.getType() == TimelineShard.TYPE_START) {
                    canvas.drawRect(lineStartX + lineMargin,
                            lineCapDistanceFromTop,
                            lineStartX + lineWidth - lineMargin,
                            height,
                            paint);
                } else if (timelineShard.getType() == TimelineShard.TYPE_END){
                    canvas.drawRect(lineStartX + lineMargin,
                            0,
                            lineStartX + lineWidth - lineMargin,
                            lineCapDistanceFromTop,
                            paint);
                }
            } else {
                canvas.drawRect(lineStartX + lineMargin,
                        0,
                        lineStartX + lineWidth - lineMargin,
                        height,
                        paint);
            }
            lineStartX += lineWidth;
        }
    }
}
