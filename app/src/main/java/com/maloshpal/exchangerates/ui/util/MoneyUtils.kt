package com.maloshpal.exchangerates.ui.util

import android.text.SpannableString
import android.text.Spanned
import android.util.Log

import java.util.Currency

object MoneyUtils {

// MARK: - Constants

    private val TAG = MoneyUtils::class.java.simpleName

    private const val RUBLE_SPAN_PLACEHOLDER = "p"

    private const val NON_BREAKING_WHITE_SPACE: Char = 0xA0.toChar()

// MARK: - Methods

    fun getCurrencySign(currencyCode: String): CharSequence {
        var result: CharSequence = ""

        if (currencyCode.isNotBlank()) {
            if (currencyCode == "RUB" || currencyCode == "RUR") {
                val rubSign = SpannableString(RUBLE_SPAN_PLACEHOLDER)
                rubSign.setSpan(FontUtils.createRubleSpan(), 0, rubSign.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                result = rubSign
            }
            else {
                result = try {
                    val currency = Currency.getInstance(currencyCode)
                    // TODO consider replacing with NON_BREAKING_WHITE_SPACE
                    // u2009 is a "Thin Space" symbol; its width is closer to gap width before RUR symbol than regular space width
                    '\u2009' + currency.symbol
                } catch (ex: IllegalArgumentException) {
                    Log.e(TAG, Log.getStackTraceString(ex))
                    NON_BREAKING_WHITE_SPACE + currencyCode
                }

            }
        }

        return result
    }
}