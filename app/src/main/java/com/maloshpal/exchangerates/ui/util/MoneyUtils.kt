package com.maloshpal.exchangerates.ui.util

import android.content.Context
import com.maloshpal.exchangerates.R

object MoneyUtils {

// MARK: - Methods

    fun convertToString(context: Context, amount: Long): String {
        val integerPart = amount / 100
        val decimalPart = amount % 100
        return context.getString(R.string.label_amount, integerPart, decimalPart)
    }

    fun convertToAmount(stringDecimal: String): Long {
        val amount = stringDecimal.toDouble()
        val integerPart = amount.toLong() * 100
        val decimalPart = Math.round(amount *  100 - integerPart)
        return integerPart + decimalPart
    }
}