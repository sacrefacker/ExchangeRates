package com.maloshpal.exchangerates.mvp.model

import com.maloshpal.exchangerates.mvp.model.base.IBaseManager
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback

interface IExchangeRatesManager : IBaseManager {

    fun requestExchangeRates(callback: IManagerCallback<ExchangeRateListModel>)
}
