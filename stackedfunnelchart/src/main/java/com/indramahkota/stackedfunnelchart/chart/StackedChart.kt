package com.indramahkota.stackedfunnelchart.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.indramahkota.stackedfunnelchart.R
import com.indramahkota.stackedfunnelchart.chart.StackedChart.Type.BOTTOM
import com.indramahkota.stackedfunnelchart.chart.StackedChart.Type.MIDDLE
import com.indramahkota.stackedfunnelchart.chart.StackedChart.Type.UPPER
import com.indramahkota.stackedfunnelchart.chart.StackedChart.Type.values
import com.indramahkota.stackedfunnelchart.model.StackedModel
import kotlin.math.sin

class StackedChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val dip = resources.displayMetrics.density

    // For area of stacked bar chart
    private var width = 0f

    var height = 0f
        set(value) {
            field = value * dip
            requestLayout()
            invalidate()
        }

    var angle = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var delta = 0f

    var bottom = 0f
        set(value) {
            field = value * dip
            requestLayout()
            invalidate()
        }

    private var paddingHorizontal = 0f
    private var minimumPaddingHorizontal = 0f
    private var drawingAreaBound = RectF()

    var showBackground = false
        set(value) {
            field = value
            invalidate()
        }

    // For title of stacked bar chart
    var titleText = "-"
        set(value) {
            field = value
            invalidate()
        }

    private var values = listOf(0.33f, 0.33f, 0.33f)
    var model = StackedModel()
        set(value) {
            field = value
            values = value.getEachPercent()
            requestLayout()
            invalidate()
        }

    private var colorValues = mutableListOf<Int>()
    private var titleTextSize = 0f
    private var titlePaddingStart = 0f
    private var titlePaddingEnd = 0f
    private var titleCenterValue = 0f
    private var titleBound = Rect()
    private var titleMinimumBound = Rect()
    private var titleTextPaint: TextPaint

    private var type = UPPER

    enum class Type {
        UPPER, MIDDLE, BOTTOM
    }

    companion object {
        private const val DEFAULT_ANGLE = 80
        private const val DEFAULT_HEIGHT = 72
        private const val DEFAULT_PADDING_BOTTOM = 10
        private const val DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE = "XXXX"
    }

    init {
        colorValues.add(ContextCompat.getColor(context, R.color.stack_title_1))
        colorValues.add(ContextCompat.getColor(context, R.color.stack_title_2))
        colorValues.add(ContextCompat.getColor(context, R.color.stack_title_3))

        val array = getContext().obtainStyledAttributes(attrs, R.styleable.StackedChart)
        try {
            type = values()[array.getInt(R.styleable.StackedChart_stack_type, 0)]
            angle = array.getInt(R.styleable.StackedChart_angle_side_delta, DEFAULT_ANGLE).toFloat()
            height = array.getDimensionPixelSize(
                R.styleable.StackedChart_stack_height, DEFAULT_HEIGHT
            ).toFloat()
            bottom = array.getDimensionPixelSize(
                R.styleable.StackedChart_stack_padding_bottom, DEFAULT_PADDING_BOTTOM
            ).toFloat()
            titleText = array.getString(R.styleable.StackedChart_stack_title)
                ?: DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE
        } finally {
            array.recycle()
        }

        titleTextSize = resources.getDimension(R.dimen.title_text_size)
        titlePaddingStart = resources.getDimension(R.dimen.title_padding_start)
        titlePaddingEnd = resources.getDimension(R.dimen.title_padding_end)

        titleTextPaint = buildTextPaint(titleTextSize)
        titleTextPaint.getTextBounds(
            DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE,
            0,
            DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE.length,
            titleMinimumBound
        )

        minimumPaddingHorizontal = titlePaddingStart + titleMinimumBound.width() + titlePaddingEnd
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingAreaBound = RectF(0f, 0f, getWidth().toFloat(), height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            measuredWidth,
            (if (type != BOTTOM) (height + bottom + 0.5f) else (height + 0.5f)).toInt()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        delta = calculateSideDelta(angle, height)

        paddingHorizontal = when (type) {
            UPPER -> minimumPaddingHorizontal
            MIDDLE -> minimumPaddingHorizontal + calculateSideDelta(
                angle, height + bottom
            )
            BOTTOM -> minimumPaddingHorizontal + calculateSideDelta(
                angle, 2 * (height + bottom)
            )
        }

        width = drawingAreaBound.width() - 2 * paddingHorizontal

        titleTextPaint.getTextBounds(titleText, 0, titleText.length, titleBound)
        titleCenterValue = (minimumPaddingHorizontal - titleBound.width()) / 2

        if (showBackground) canvas?.drawColor(Color.GRAY)

        if (canvas != null) {
            drawTitleBackground(canvas)
            drawStackedBarChart(canvas)
        }
    }

    private fun drawTitleBackground(canvas: Canvas) {
        val radius = 8 * dip
        val paddingVertical = 10 * dip
        when (type) {
            UPPER -> {
                canvas.drawRoundRect(
                    RectF(
                        drawingAreaBound.width() / 2,
                        paddingVertical,
                        drawingAreaBound.width(),
                        height - paddingVertical
                    ), radius, radius, buildPaint(colorValues[0])
                )
                canvas.drawText(
                    titleText, drawingAreaBound.width() - (titleBound.width() + titlePaddingEnd),
                    (height + titleBound.height()) / 2f, titleTextPaint
                )
            }
            MIDDLE -> {
                canvas.drawRoundRect(
                    RectF(
                        paddingHorizontal - minimumPaddingHorizontal,
                        paddingVertical,
                        drawingAreaBound.width() / 2,
                        height - paddingVertical
                    ), radius, radius, buildPaint(colorValues[1])
                )
                canvas.drawText(
                    titleText, paddingHorizontal - minimumPaddingHorizontal + titleCenterValue,
                    (height + titleBound.height()) / 2f, titleTextPaint
                )
            }
            BOTTOM -> {
                canvas.drawRoundRect(
                    RectF(
                        drawingAreaBound.width() / 2,
                        paddingVertical,
                        0.5f * (drawingAreaBound.width() + width) + minimumPaddingHorizontal,
                        height - paddingVertical
                    ), radius, radius, buildPaint(colorValues[2])
                )
                canvas.drawText(
                    titleText,
                    0.5f * (drawingAreaBound.width() + width) + titleCenterValue,
                    (height + titleBound.height()) / 2f, titleTextPaint
                )
            }
        }
    }

    private fun drawStackedBarChart(canvas: Canvas) {
        val path = Path()
        path.moveTo(paddingHorizontal, 0f)
        path.lineTo(paddingHorizontal + width, 0f)
        path.lineTo(paddingHorizontal + width - delta, height)
        path.lineTo(paddingHorizontal + delta, height)
        path.lineTo(paddingHorizontal, 0f)

        canvas.clipPath(path)
        path.rewind()

        val width11 = values[0] * width
        path.addRect(
            RectF(paddingHorizontal, 0f, paddingHorizontal + width11, height),
            Path.Direction.CCW
        )
        canvas.drawPath(path, buildPaint(ContextCompat.getColor(context, R.color.stack_1)))

        val stacked1TextBounds = Rect()
        titleTextPaint.getTextBounds(
            "${values[0]}%".toCharArray(),
            0,
            "${values[0]}%".toCharArray().size,
            stacked1TextBounds
        )
        var stacked1TextX = paddingHorizontal + delta
        if (stacked1TextBounds.width() < width11) {
            stacked1TextX = paddingHorizontal + (width11 - stacked1TextBounds.width()) / 2
        }
        canvas.drawText(
            "${values[0]}%",
            stacked1TextX,
            (height + stacked1TextBounds.height()) / 2f,
            titleTextPaint
        )

        path.rewind()

        val width12 = values[1] * width
        path.addRect(
            RectF(
                paddingHorizontal + width11,
                0f,
                paddingHorizontal + width11 + width12,
                height
            ), Path.Direction.CCW
        )
        canvas.drawPath(path, buildPaint(ContextCompat.getColor(context, R.color.stack_2)))

        val stacked2TextBounds = Rect()
        titleTextPaint.getTextBounds(
            "${values[1]}%".toCharArray(),
            0,
            "${values[1]}%".toCharArray().size,
            stacked2TextBounds
        )
        var stacked2TextX = paddingHorizontal + width11 + 5 * dip
        if (stacked2TextBounds.width() < width12) {
            stacked2TextX = paddingHorizontal + width11 + (width12 - stacked2TextBounds.width()) / 2
        }
        canvas.drawText(
            "${values[1]}%",
            stacked2TextX,
            (height + stacked2TextBounds.height()) / 2f,
            titleTextPaint
        )

        path.rewind()

        val width13 = values[2] * width
        path.addRect(
            RectF(
                paddingHorizontal + width11 + width12,
                0f,
                paddingHorizontal + width11 + width12 + width13,
                height
            ), Path.Direction.CCW
        )
        canvas.drawPath(path, buildPaint(ContextCompat.getColor(context, R.color.stack_3)))

        val stacked3TextBounds = Rect()
        titleTextPaint.getTextBounds(
            "${values[2]}%".toCharArray(),
            0,
            "${values[2]}%".toCharArray().size,
            stacked3TextBounds
        )
        var stacked3TextX = paddingHorizontal + width11 + width12 + 5 * dip
        if (stacked3TextBounds.width() < width13) {
            stacked3TextX =
                paddingHorizontal + width11 + width12 + (width13 - stacked3TextBounds.width()) / 2
        }
        canvas.drawText(
            "${values[2]}%",
            stacked3TextX,
            (height + stacked3TextBounds.height()) / 2f,
            titleTextPaint
        )
    }

    private fun calculateSideDelta(angle: Float, height: Float) =
        (height * sin(Math.toRadians(90 - angle.toDouble())) / sin(Math.toRadians(angle.toDouble()))).toFloat()

    private fun buildPaint(clr: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = clr
    }

    private fun buildTextPaint(size: Float) = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = size
        color = Color.WHITE
        typeface = Typeface.create("Arial", Typeface.BOLD)
    }
}