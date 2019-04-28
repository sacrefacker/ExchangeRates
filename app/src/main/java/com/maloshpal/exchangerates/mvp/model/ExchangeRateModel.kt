package com.maloshpal.exchangerates.mvp.model

import com.maloshpal.exchangerates.mvp.model.base.BaseModel

data class ExchangeRateModel(val id: String, val multiplier: Long) : BaseModel()