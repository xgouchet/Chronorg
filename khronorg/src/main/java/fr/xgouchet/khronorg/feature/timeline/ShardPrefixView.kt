package fr.xgouchet.khronorg.feature.timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import fr.xgouchet.khronorg.R
import org.joda.time.DateTime
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
class ShardPrefixView(context: Context,
                      attrSet: AttributeSet?,
                      defStyleAttr: Int,
                      defStyleRes: Int) :
        View(context, attrSet, defStyleAttr, defStyleRes) {

    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrSet: AttributeSet?) : this(context, attrSet, 0, 0)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : this(context, attrSet, defStyleAttr, 0)


    private val lineWidth: Int
    private val lineStrokeWidth: Int
    private val lineMargin: Int
    private val lineCapWidth: Int
    private val lineCapDistanceFromTop: Int
    private val paint: Paint = Paint()

    var shard: TimelineShard by notNull()

    init {
        val res = context.resources

        lineWidth = res.getDimensionPixelSize(R.dimen.line_width)
        lineStrokeWidth = res.getDimensionPixelSize(R.dimen.line_stroke_width)
        lineCapWidth = res.getDimensionPixelSize(R.dimen.line_cap_width)
        lineCapDistanceFromTop = res.getDimensionPixelSize(R.dimen.line_cap_distance_from_top)

        lineMargin = (lineWidth - lineStrokeWidth) / 2

        paint.isAntiAlias = true

        if (isInEditMode) {
            val instant = DateTime("1970-01-01T00:00Z")
            shard = TimelineShard(instant, "Bar", Color.GREEN, TimelineShard.ShardType.FIRST, 0)
            shard.prefix.add(shard)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = shard.prefix.size * lineWidth
        val minHeight =  (2 * lineCapDistanceFromTop) + lineCapWidth


        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        when (widthMode) {
            View.MeasureSpec.EXACTLY -> width = widthSize
            View.MeasureSpec.AT_MOST -> width = Math.min(minWidth, widthSize)
            else -> width = minWidth
        }

        when (heightMode) {
            View.MeasureSpec.EXACTLY -> height = heightSize
            View.MeasureSpec.AT_MOST -> height = Math.min(minHeight, heightSize)
            else -> height = minHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var lineStartX = 0f

        for (prefixShard in shard.prefix) {
            if (prefixShard != null) {
                paint.color = prefixShard.color

                if (prefixShard.id == shard.id) {
                    drawShard(canvas, shard, lineStartX)
                } else {
                    drawOngoing(canvas, lineStartX)
                }
            }
            lineStartX += lineWidth
        }
    }

    fun drawOngoing(canvas: Canvas, lineStartX: Float) {
        val left = lineStartX + lineMargin
        val top = 0f
        val right = lineStartX + lineWidth - lineMargin
        val bottom = measuredHeight.toFloat()
        canvas.drawRect(left, top, right, bottom, paint)
    }

    fun drawShard(canvas: Canvas, shard: TimelineShard, lineStartX: Float) {

        canvas.drawCircle((lineStartX + lineWidth / 2),
                lineCapDistanceFromTop.toFloat(),
                (lineCapWidth / 2).toFloat(),
                paint)

        when (shard.type) {
            TimelineShard.ShardType.LAST -> {
                canvas.drawRect((lineStartX + lineMargin),
                        0f,
                        (lineStartX + lineWidth - lineMargin),
                        lineCapDistanceFromTop.toFloat(),
                        paint)
            }

            TimelineShard.ShardType.FIRST -> {
                canvas.drawRect((lineStartX + lineMargin),
                        lineCapDistanceFromTop.toFloat(),
                        (lineStartX + lineWidth - lineMargin),
                        measuredHeight.toFloat(),
                        paint)
            }

            TimelineShard.ShardType.SINGLE -> {
            }
        }
    }


}