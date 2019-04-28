package com.maloshpal.exchangerates.ui.util

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

@SuppressLint("ParcelCreator")
class CustomTypefaceSpan(family: String, private val mTypeface: Typeface) : TypefaceSpan(family) {

    override fun updateDrawState(textPaint: TextPaint) {
        applyCustomTypeFace(textPaint, mTypeface)
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        applyCustomTypeFace(textPaint, mTypeface)
    }

    private fun applyCustomTypeFace(paint: Paint, typeface: Typeface) {
        val originalTypeface = paint.typeface
        val originalStyle = originalTypeface?.style ?: 0

        val invertedMask = originalStyle and typeface.style.inv()

        if (invertedMask and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (invertedMask and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = typeface
    }
}
