package com.indramahkota.stackedfunnelchart.model

class StackedModel {
    var first: Int = 0
    var second: Int = 0
    var third: Int = 0

    fun getEachPercent(): List<Float> {
        val total = first + second + third
        return listOf(
            (first.toDouble() / total).toFloat(),
            (second.toDouble() / total).toFloat(),
            (third.toDouble() / total).toFloat()
        )
    }
}