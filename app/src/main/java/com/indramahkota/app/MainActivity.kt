package com.indramahkota.app

import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.indramahkota.app.databinding.ActivityMainBinding
import com.indramahkota.stackedfunnelchart.model.StackedFunnelModel
import com.indramahkota.stackedfunnelchart.model.StackedModel
import kotlin.random.Random

fun SeekBar.onChanged(changeListener: ((progress: Int) -> Unit)? = null) {
    this.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            changeListener?.invoke(progress)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {}
    })
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            checkbox.setOnClickListener { view ->
                stackedFunnelChart.showBackgroundColor((view as CheckBox).isChecked)
            }

            seekBar1.onChanged { progress ->
                stackedFunnelChart.setStackedFunnelChartAngle(80 + progress / 100f * 10)
            }

            seekBar2.onChanged { progress ->
                stackedFunnelChart.setStackedFunnelChartHeight(72 + progress / 100f * 78)
            }

            seekBar3.onChanged { progress ->
                stackedFunnelChart.setStackedFunnelChartBottom(progress.toFloat())
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )

            seekBar4.onChanged { progress ->
                val paddingHorizontal =
                    (16 * resources.displayMetrics.density + progress / 100f * 200 + 0.5f).toInt()
                params.setMargins(paddingHorizontal, 0, paddingHorizontal, 0)
                stackedFunnelChart.setStackedFunnelChartPaddingHorizontal(params)
            }

            randomButton.setOnClickListener { randomValue() }
        }

        randomValue()
    }

    private fun calculateValue(): StackedModel {
        val firstValue = Random.nextInt(100)
        val secondValue = Random.nextInt(100 - firstValue)
        val thirdValue = 100 - (firstValue + secondValue)
        return StackedModel().apply {
            first = firstValue
            second = secondValue
            third = thirdValue
        }
    }

    private fun randomValue() {
        val model = StackedFunnelModel(calculateValue(), calculateValue(), calculateValue())
        binding.stackedFunnelChart.setStackedValue(model)
    }
}