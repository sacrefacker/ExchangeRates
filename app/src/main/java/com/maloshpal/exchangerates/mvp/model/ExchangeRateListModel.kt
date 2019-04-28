package com.maloshpal.exchangerates.mvp.model

import com.maloshpal.exchangerates.mvp.model.base.BaseModel

data class ExchangeRateListModel(val rates: List<ExchangeRateModel>) : BaseModel()