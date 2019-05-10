package com.maloshpal.exchangerates.datasource.network.exchangerates

import android.annotation.SuppressLint
import android.util.Log

import com.maloshpal.exchangerates.datasource.network.ApiService
import com.maloshpal.exchangerates.manager.IDataSourceCallback
import com.maloshpal.exchangerates.manager.exchangerates.IExchangeRatesDataSource
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRateListModel

class ExchangeRatesNetworkDataSource(private val api: ApiService) : IExchangeRatesDataSource {

    // FIXME remove rx
    @SuppressLint("CheckResult")
    override fun getExchangeRates(baseCurrency: String?, callback: IDataSourceCallback<IExchangeRateListModel>)  {
        this.api.getExchangeRates(baseCurrency)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel -> callback.handleResponse(responseModel) },
                        { throwable -> run {
                            Log.w(TAG, throwable)
                            callback.handleError(throwable.localizedMessage)
                        } })
    }

    override fun insertExchangeRates(baseCurrency: String, rates: IExchangeRateListModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cleanExchangeRates() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = ExchangeRatesNetworkDataSource::class.java.simpleName
    }
}

