package com.indramahkota.stackedfunnelchart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.indramahkota.stackedfunnelchart.chart.StackedChart
import com.indramahkota.stackedfunnelchart.databinding.StackedFunnelLayoutBinding
import com.indramahkota.stackedfunnelchart.model.StackedFunnelModel

class StackedFunnelChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: StackedFunnelLayoutBinding
    private var listOfStacked = mutableListOf<StackedChart>()

    init {
        binding = StackedFunnelLayoutBinding.inflate(LayoutInflater.from(context), this)
        with(binding) {
            listOfStacked.add(stacked1)
            listOfStacked.add(stacked2)
            listOfStacked.add(stacked3)
        }

        val array = getContext().obtainStyledAttributes(attrs, R.styleable.StackedFunnelChart)
        try {
            val stackedTitle1 = array.getString(R.styleable.StackedFunnelChart_stacked_1_title)
                ?: "VAL 1"
            val stackedTitle2 = array.getString(R.styleable.StackedFunnelChart_stacked_2_title)
                ?: "VAL 2"
            val stackedTitle3 = array.getString(R.styleable.StackedFunnelChart_stacked_3_title)
                ?: "VAL 3"
            listOfStacked[0].titleText = stackedTitle1
            listOfStacked[1].titleText = stackedTitle2
            listOfStacked[2].titleText = stackedTitle3
        } finally {
            array.recycle()
        }
    }

    fun setStackedValue(value: StackedFunnelModel) {
        listOfStacked[0].model = value.first
        listOfStacked[1].model = value.second
        listOfStacked[2].model = value.third
    }

    fun showBackgroundColor(value: Boolean) {
        listOfStacked.forEach { it.showBackground = value }
    }

    fun setStackedFunnelChartAngle(value: Float) {
        listOfStacked.forEach { it.angle = value }
    }

    fun setStackedFunnelChartHeight(value: Float) {
        listOfStacked.forEach { it.height = value }
    }

    fun setStackedFunnelChartBottom(value: Float) {
        listOfStacked.forEach { it.bottom = value }
    }

    fun setStackedFunnelChartPaddingHorizontal(value: LayoutParams) {
        listOfStacked.forEach { it.layoutParams = value }
    }
}