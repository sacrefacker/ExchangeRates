package com.maloshpal.exchangerates.manager.exchangerates

import com.maloshpal.exchangerates.manager.IDataSourceCallback
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRateListModel

interface IExchangeRatesDataSource {

    fun getExchangeRates(baseCurrency: String?, callback: IDataSourceCallback<IExchangeRateListModel>)
    fun insertExchangeRates(baseCurrency: String, rates: IExchangeRateListModel)
    fun cleanExchangeRates()
}
