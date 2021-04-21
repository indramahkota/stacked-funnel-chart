package com.indramahkota.stackedfunnelchart.stackedchart

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.indramahkota.stackedfunnelchart.R
import kotlin.math.max
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
    private var minimumPaddingHorizontal = 0f //fixed size for background title
    private var drawingAreaBound = RectF()

    var showBackground = false
        set(value) {
            field = value
            invalidate()
        }

    // For title of stacked bar chart
    var titleText = ""
        set(value) {
            field = value
            invalidate()
        }

    private var titleTextSize = 14f
    private var titlePaddingStart = 4f
    private var titlePaddingEnd = 14f
    private var titleCenterValue = 0f
    private var titleBound = Rect()
    private var titleTextPaint: TextPaint

    var stacked1Text = "-"
        set(value) {
            field = value
            invalidate()
        }
    var stacked2Text = "-"
        set(value) {
            field = value
            invalidate()
        }
    var stacked3Text = "-"
        set(value) {
            field = value
            invalidate()
        }

    var stacked1Percent = 1 / 3f
        set(value) {
            field = value
            invalidate()
        }
    var stacked2Percent = 1 / 3f
        set(value) {
            field = value
            invalidate()
        }
    var stacked3Percent = 1 / 3f
        set(value) {
            field = value
            invalidate()
        }

    var stacked1Color = Color.parseColor("#FF6C02")
        set(value) {
            field = value
            invalidate()
        }
    var stacked2Color = Color.parseColor("#FEC106")
        set(value) {
            field = value
            invalidate()
        }
    var stacked3Color = Color.parseColor("#3776C3")
        set(value) {
            field = value
            invalidate()
        }

    var stackedTitle1Color = Color.parseColor("#97AEC9")
        set(value) {
            field = value
            invalidate()
        }
    var stackedTitle2Color = Color.parseColor("#FFAA6D")
        set(value) {
            field = value
            invalidate()
        }
    var stackedTitle3Color = Color.parseColor("#FFD141")
        set(value) {
            field = value
            invalidate()
        }

    private var type = Type.UPPER

    enum class Type {
        UPPER, MIDDLE, BOTTOM
    }

    companion object {
        private const val DEFAULT_ANGLE = 80
        private const val DEFAULT_HEIGHT = 72
        private const val DEFAULT_PADDING_BOTTOM = 10
        private const val DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE = "HHHH"
    }

    init {
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.StackedChart)
        try {
            type = Type.values()[array.getInt(R.styleable.StackedChart_stack_type, 0)]
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

        titleTextPaint = buildTextPaint(titleTextSize * dip)
        titleTextPaint.getTextBounds(
            DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE,
            0,
            DEFAULT_TITLE_TEXT_FOR_WIDTH_SIZE.length,
            titleBound
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingAreaBound = RectF(0f, 0f, getWidth().toFloat(), height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            measuredWidth,
            (if (type != Type.BOTTOM) (height + bottom + 0.5f) else (height + 0.5f)).toInt()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        delta = calculateSideDelta(angle, height)
        paddingHorizontal = titlePaddingStart + titleBound.width() + titlePaddingEnd
        minimumPaddingHorizontal = paddingHorizontal

        when (type) {
            Type.MIDDLE -> paddingHorizontal = max(
                paddingHorizontal,
                paddingHorizontal + calculateSideDelta(
                    angle,
                    height + bottom
                )
            )
            Type.BOTTOM -> paddingHorizontal = max(
                paddingHorizontal,
                paddingHorizontal + calculateSideDelta(
                    angle,
                    2 * (height + bottom)
                )
            )
            else -> {
            }
        }

        width = drawingAreaBound.width() - 2 * paddingHorizontal

        titleTextPaint.getTextBounds(
            titleText,
            0,
            titleText.length,
            titleBound
        )
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
            Type.UPPER -> {
                canvas.drawRoundRect(
                    RectF(
                        drawingAreaBound.width() / 2,
                        paddingVertical,
                        drawingAreaBound.width(),
                        height - paddingVertical
                    ), radius, radius, buildPaint(
                        stackedTitle1Color
                    )
                )
                canvas.drawText(
                    titleText, drawingAreaBound.width() - (titleBound.width() + titlePaddingEnd),
                    (height + titleBound.height()) / 2.0f, titleTextPaint
                )
            }
            Type.MIDDLE -> {
                canvas.drawRoundRect(
                    RectF(
                        paddingHorizontal - minimumPaddingHorizontal,
                        paddingVertical,
                        drawingAreaBound.width() / 2,
                        height - paddingVertical
                    ), radius, radius, buildPaint(
                        stackedTitle2Color
                    )
                )
                canvas.drawText(
                    titleText, paddingHorizontal - minimumPaddingHorizontal + titleCenterValue,
                    (height + titleBound.height()) / 2.0f, titleTextPaint
                )
            }
            Type.BOTTOM -> {
                canvas.drawRoundRect(
                    RectF(
                        drawingAreaBound.width() / 2,
                        paddingVertical,
                        0.5f * (drawingAreaBound.width() + width) + minimumPaddingHorizontal,
                        height - paddingVertical
                    ), radius, radius, buildPaint(
                        stackedTitle3Color
                    )
                )
                canvas.drawText(
                    titleText,
                    0.5f * (drawingAreaBound.width() + width) + titleCenterValue,
                    (height + titleBound.height()) / 2.0f, titleTextPaint
                )
            }
        }
    }

    private fun drawStackedBarChart(canvas: Canvas) {
        val path = Path()
        path.moveTo(paddingHorizontal, 0.0f)
        path.lineTo(paddingHorizontal + width, 0.0f)
        path.lineTo(paddingHorizontal + width - delta, height)
        path.lineTo(paddingHorizontal + delta, height)
        path.lineTo(paddingHorizontal, 0.0f)

        canvas.clipPath(path)
        path.rewind()

        val width11 = stacked1Percent * width
        path.addRect(
            RectF(paddingHorizontal, 0.0f, paddingHorizontal + width11, height),
            Path.Direction.CCW
        )
        canvas.drawPath(path, buildPaint(stacked1Color))

        val stacked1TextBounds = Rect()
        titleTextPaint.getTextBounds(
            stacked1Text.toCharArray(),
            0,
            stacked1Text.toCharArray().size,
            stacked1TextBounds
        )
        var stacked1TextX = paddingHorizontal + delta
        if (stacked1TextBounds.width() < width11) {
            stacked1TextX = paddingHorizontal + (width11 - stacked1TextBounds.width()) / 2
        }
        canvas.drawText(
            stacked1Text,
            stacked1TextX,
            (height + stacked1TextBounds.height()) / 2.0f,
            titleTextPaint
        )

        path.rewind()

        val width12 = stacked2Percent * width
        path.addRect(
            RectF(
                paddingHorizontal + width11,
                0.0f,
                paddingHorizontal + width11 + width12,
                height
            ), Path.Direction.CCW
        )
        canvas.drawPath(path, buildPaint(stacked2Color))

        val stacked2TextBounds = Rect()
        titleTextPaint.getTextBounds(
            stacked2Text.toCharArray(),
            0,
            stacked2Text.toCharArray().size,
            stacked2TextBounds
        )
        var stacked2TextX = paddingHorizontal + width11 + 5 * dip
        if (stacked2TextBounds.width() < width12) {
            stacked2TextX = paddingHorizontal + width11 + (width12 - stacked2TextBounds.width()) / 2
        }
        canvas.drawText(
            stacked2Text,
            stacked2TextX,
            (height + stacked2TextBounds.height()) / 2.0f,
            titleTextPaint
        )

        path.rewind()

        val width13 = stacked3Percent * width
        path.addRect(
            RectF(
                paddingHorizontal + width11 + width12,
                0.0f,
                paddingHorizontal + width11 + width12 + width13,
                height
            ), Path.Direction.CCW
        )
        canvas.drawPath(path, buildPaint(stacked3Color))

        val stacked3TextBounds = Rect()
        titleTextPaint.getTextBounds(
            stacked3Text.toCharArray(),
            0,
            stacked3Text.toCharArray().size,
            stacked3TextBounds
        )
        var stacked3TextX = paddingHorizontal + width11 + width12 + 5 * dip
        if (stacked3TextBounds.width() < width13) {
            stacked3TextX =
                paddingHorizontal + width11 + width12 + (width13 - stacked3TextBounds.width()) / 2
        }
        canvas.drawText(
            stacked3Text,
            stacked3TextX,
            (height + stacked3TextBounds.height()) / 2.0f,
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