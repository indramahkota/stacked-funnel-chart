package com.indramahkota.stackedfunnelchart

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import com.indramahkota.stackedfunnelchart.stackedchart.StackedChart

class StackedFunnelChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var chartBar1: StackedChart
    private var chartBar2: StackedChart
    private var chartBar3: StackedChart

    init {
        inflate(getContext(), R.layout.stacked_funnel_layout, this)

        chartBar1 = findViewById(R.id.stacked1);
        chartBar2 = findViewById(R.id.stacked2);
        chartBar3 = findViewById(R.id.stacked3);

        val array = getContext().obtainStyledAttributes(attrs, R.styleable.StackedFunnelChart)
        try {
            val stackedColor1 = getColorFromResources(
                array,
                R.styleable.StackedFunnelChart_stacked_1_color,
                R.color.stack_1
            )
            val stackedColor2 = getColorFromResources(
                array,
                R.styleable.StackedFunnelChart_stacked_2_color,
                R.color.stack_2
            )
            val stackedColor3 = getColorFromResources(
                array,
                R.styleable.StackedFunnelChart_stacked_3_color,
                R.color.stack_3
            )

            chartBar1.stacked1Color = stackedColor1
            chartBar1.stacked2Color = stackedColor2
            chartBar1.stacked3Color = stackedColor3

            chartBar2.stacked1Color = stackedColor1
            chartBar2.stacked2Color = stackedColor2
            chartBar2.stacked3Color = stackedColor3

            chartBar3.stacked1Color = stackedColor1
            chartBar3.stacked2Color = stackedColor2
            chartBar3.stacked3Color = stackedColor3

            val stackedTitleColor1 = getColorFromResources(
                array,
                R.styleable.StackedFunnelChart_stacked_1_title_color,
                R.color.stack_title_1
            )
            val stackedTitleColor2 = getColorFromResources(
                array,
                R.styleable.StackedFunnelChart_stacked_2_title_color,
                R.color.stack_title_2
            )
            val stackedTitleColor3 = getColorFromResources(
                array,
                R.styleable.StackedFunnelChart_stacked_3_title_color,
                R.color.stack_title_3
            )

            chartBar1.stackedTitle1Color = stackedTitleColor1
            chartBar2.stackedTitle2Color = stackedTitleColor2
            chartBar3.stackedTitle3Color = stackedTitleColor3

            val stackedTitle1 = array.getString(R.styleable.StackedFunnelChart_stacked_1_title)
                ?: "VAL 1"

            val stackedTitle2 = array.getString(R.styleable.StackedFunnelChart_stacked_2_title)
                ?: "VAL 2"

            val stackedTitle3 = array.getString(R.styleable.StackedFunnelChart_stacked_3_title)
                ?: "VAL 3"

            chartBar1.titleText = stackedTitle1
            chartBar2.titleText = stackedTitle2
            chartBar3.titleText = stackedTitle3
        } finally {
            array.recycle()
        }
    }

    private fun getColorFromResources(array: TypedArray, id: Int, default: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            array.getColor(id, resources.getColor(default, null))
        } else {
            array.getColor(id, resources.getColor(default))
        }
    }

    fun showBackgroundColor(value: Boolean) {
        chartBar1.showBackground = value
        chartBar2.showBackground = value
        chartBar3.showBackground = value
    }

    fun setStackedFunnelChartAngle(value: Float) {
        chartBar1.angle = value
        chartBar2.angle = value
        chartBar3.angle = value
    }

    fun setStackedFunnelChartHeight(value: Float) {
        chartBar1.height = value
        chartBar2.height = value
        chartBar3.height = value
    }

    fun setStackedFunnelChartBottom(value: Float) {
        chartBar1.bottom = value
        chartBar2.bottom = value
        chartBar3.bottom = value
    }

    fun setStackedFunnelChartPaddingHorizontal(value: LayoutParams) {
        chartBar1.layoutParams = value
        chartBar2.layoutParams = value
        chartBar3.layoutParams = value
    }

    fun setChartBar1Stacked1Percent(value: Float) {
        chartBar1.stacked1Percent = value
    }

    fun setChartBar1Stacked2Percent(value: Float) {
        chartBar1.stacked2Percent = value
    }

    fun setChartBar1Stacked3Percent(value: Float) {
        chartBar1.stacked3Percent = value
    }

    fun setChartBar1Stacked1Text(value: String) {
        chartBar1.stacked1Text = value
    }

    fun setChartBar1Stacked2Text(value: String) {
        chartBar1.stacked2Text = value
    }

    fun setChartBar1Stacked3Text(value: String) {
        chartBar1.stacked3Text = value
    }

    fun setChartBar2Stacked1Percent(value: Float) {
        chartBar2.stacked1Percent = value
    }

    fun setChartBar2Stacked2Percent(value: Float) {
        chartBar2.stacked2Percent = value
    }

    fun setChartBar2Stacked3Percent(value: Float) {
        chartBar2.stacked3Percent = value
    }

    fun setChartBar2Stacked1Text(value: String) {
        chartBar2.stacked1Text = value
    }

    fun setChartBar2Stacked2Text(value: String) {
        chartBar2.stacked2Text = value
    }

    fun setChartBar2Stacked3Text(value: String) {
        chartBar2.stacked3Text = value
    }

    fun setChartBar3Stacked1Percent(value: Float) {
        chartBar3.stacked1Percent = value
    }

    fun setChartBar3Stacked2Percent(value: Float) {
        chartBar3.stacked2Percent = value
    }

    fun setChartBar3Stacked3Percent(value: Float) {
        chartBar3.stacked3Percent = value
    }

    fun setChartBar3Stacked1Text(value: String) {
        chartBar3.stacked1Text = value
    }

    fun setChartBar3Stacked2Text(value: String) {
        chartBar3.stacked2Text = value
    }

    fun setChartBar3Stacked3Text(value: String) {
        chartBar3.stacked3Text = value
    }
}