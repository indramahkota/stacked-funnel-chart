package com.indramahkota.stackedfunnelchart

import android.content.Context
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
            val stackedColor1 =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_1_color,
                        resources.getColor(R.color.stack_1, null)
                    )
                } else {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_1_color,
                        resources.getColor(R.color.stack_1)
                    )
                }

            val stackedColor2 =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_2_color,
                        resources.getColor(R.color.stack_2, null)
                    )
                } else {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_2_color,
                        resources.getColor(R.color.stack_2)
                    )
                }

            val stackedColor3 =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_3_color,
                        resources.getColor(R.color.stack_3, null)
                    )
                } else {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_3_color,
                        resources.getColor(R.color.stack_3)
                    )
                }

            chartBar1.stacked1Color = stackedColor1
            chartBar1.stacked2Color = stackedColor2
            chartBar1.stacked3Color = stackedColor3

            chartBar2.stacked1Color = stackedColor1
            chartBar2.stacked2Color = stackedColor2
            chartBar2.stacked3Color = stackedColor3

            chartBar3.stacked1Color = stackedColor1
            chartBar3.stacked2Color = stackedColor2
            chartBar3.stacked3Color = stackedColor3

            val stackedTitleColor1 =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_1_title_color,
                        resources.getColor(R.color.stack_title_1, null)
                    )
                } else {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_1_title_color,
                        resources.getColor(R.color.stack_title_1)
                    )
                }

            val stackedTitleColor2 =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_2_title_color,
                        resources.getColor(R.color.stack_title_2, null)
                    )
                } else {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_2_title_color,
                        resources.getColor(R.color.stack_title_2)
                    )
                }

            val stackedTitleColor3 =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_3_title_color,
                        resources.getColor(R.color.stack_title_3, null)
                    )
                } else {
                    array.getColor(
                        R.styleable.StackedFunnelChart_stacked_3_title_color,
                        resources.getColor(R.color.stack_title_3)
                    )
                }

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

}