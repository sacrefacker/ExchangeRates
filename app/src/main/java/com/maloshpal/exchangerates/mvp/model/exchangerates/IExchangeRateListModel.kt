package com.maloshpal.exchangerates.mvp.model.exchangerates

import com.maloshpal.exchangerates.mvp.model.base.IBaseModel

interface IExchangeRateListModel : IBaseModel {

    val base: String

    val date: String

    val rates: Map<String, Double>
}