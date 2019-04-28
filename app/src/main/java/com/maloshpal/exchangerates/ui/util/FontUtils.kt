package com.maloshpal.exchangerates.ui.util

import android.graphics.Typeface
import android.text.style.TypefaceSpan

import com.maloshpal.exchangerates.app.ApplicationImpl

object FontUtils {

    private val RUBLE_TYPEFACE = Typeface.createFromAsset(
            ApplicationImpl.context.assets, "fonts/rouble.ttf")

    fun createRubleSpan(): TypefaceSpan {
        return CustomTypefaceSpan("", RUBLE_TYPEFACE)
    }
}
