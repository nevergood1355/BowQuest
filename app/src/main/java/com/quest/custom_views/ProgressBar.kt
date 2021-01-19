package com.quest.custom_views

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.widget.TextView


class ProgressBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    private var maxBarValue: Int = 100
    public var textVariant = 1

    // Установка максимального значения
    fun setMaxValue(maxValue: Int) {
        this.maxBarValue = maxValue
    }

    // Установка значения
    @Synchronized
    fun setValue(value: Double) {
        val newClipLevel = (value * 10000 / maxBarValue).toInt()
        val background = this.background as LayerDrawable
        val barValue = background.getDrawable(1) as ClipDrawable
        text = ""
        barValue.level = newClipLevel
        // Уведомляем об изменении Drawable
        drawableStateChanged()
    }
}