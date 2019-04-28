package com.maloshpal.exchangerates.mvp.view.base

import java.io.Serializable

@Deprecated("Not used")
data class MoneyViewModel(
        val amount: String,
        val currencyCode: String
): Serializable