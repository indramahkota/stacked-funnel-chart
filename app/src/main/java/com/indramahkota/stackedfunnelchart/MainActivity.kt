package com.indramahkota.stackedfunnelchart

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var stackedFunnelChart: StackedFunnelChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stackedFunnelChart = findViewById(R.id.stacked_funnel_chart)

        val checkBox = findViewById<CheckBox>(R.id.checkbox)
        checkBox.setOnClickListener { view: View ->
            stackedFunnelChart.showBackgroundColor((view as CheckBox).isChecked)
        }

        val seekBar1 = findViewById<SeekBar>(R.id.seekBar1)
        seekBar1.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                stackedFunnelChart.setStackedFunnelChartAngle(80 + progress / 100f * 10)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val seekBar2 = findViewById<SeekBar>(R.id.seekBar2)
        seekBar2.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                stackedFunnelChart.setStackedFunnelChartHeight(72 + progress / 100f * 78)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val seekBar3 = findViewById<SeekBar>(R.id.seekBar3)
        seekBar3.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                stackedFunnelChart.setStackedFunnelChartBottom(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val seekBar4 = findViewById<SeekBar>(R.id.seekBar4)
        seekBar4.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val paddingHorizontal =
                    (16 * resources.displayMetrics.density + progress / 100f * 200 + 0.5f).toInt()
                params.setMargins(paddingHorizontal, 0, paddingHorizontal, 0)
                stackedFunnelChart.setStackedFunnelChartPaddingHorizontal(params)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val button = findViewById<Button>(R.id.randomButton)
        button.setOnClickListener { v: View? ->
            randomValue()
        }

        randomValue()
    }

    private fun calculatePercentageValue(): IntArray {
        val random = Random()
        val percent1 = random.nextInt(100)
        val percent2 = random.nextInt(100 - percent1)
        val percent3 = 100 - (percent1 + percent2)
        return intArrayOf(percent1, percent2, percent3)
    }

    private fun randomValue() {
        val percent1 = calculatePercentageValue()
        stackedFunnelChart.setChartBar1Stacked1Percent(percent1[0] / 100f)
        stackedFunnelChart.setChartBar1Stacked1Text("${percent1[0]}%")
        stackedFunnelChart.setChartBar1Stacked2Percent(percent1[1] / 100f)
        stackedFunnelChart.setChartBar1Stacked2Text("${percent1[1]}%")
        stackedFunnelChart.setChartBar1Stacked3Percent(percent1[2] / 100f)
        stackedFunnelChart.setChartBar1Stacked3Text("${percent1[2]}%")

        val percent2 = calculatePercentageValue()
        stackedFunnelChart.setChartBar2Stacked1Percent(percent2[0] / 100f)
        stackedFunnelChart.setChartBar2Stacked1Text("${percent2[0]}%")
        stackedFunnelChart.setChartBar2Stacked2Percent(percent2[1] / 100f)
        stackedFunnelChart.setChartBar2Stacked2Text("${percent2[1]}%")
        stackedFunnelChart.setChartBar2Stacked3Percent(percent2[2] / 100f)
        stackedFunnelChart.setChartBar2Stacked3Text("${percent2[2]}%")

        val percent3 = calculatePercentageValue()
        stackedFunnelChart.setChartBar3Stacked1Percent(percent3[0] / 100f)
        stackedFunnelChart.setChartBar3Stacked1Text("${percent3[0]}%")
        stackedFunnelChart.setChartBar3Stacked2Percent(percent3[1] / 100f)
        stackedFunnelChart.setChartBar3Stacked2Text("${percent3[1]}%")
        stackedFunnelChart.setChartBar3Stacked3Percent(percent3[2] / 100f)
        stackedFunnelChart.setChartBar3Stacked3Text("${percent3[2]}%")
    }
}