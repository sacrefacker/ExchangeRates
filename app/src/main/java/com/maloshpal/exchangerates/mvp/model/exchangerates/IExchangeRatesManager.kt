package com.maloshpal.exchangerates.mvp.model.exchangerates

import com.maloshpal.exchangerates.mvp.model.base.IBaseManager
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback

interface IExchangeRatesManager : IBaseManager {

    fun requestExchangeRates(baseCurrency: String?, callback: IManagerCallback<ExchangeRateListModel>)
}
