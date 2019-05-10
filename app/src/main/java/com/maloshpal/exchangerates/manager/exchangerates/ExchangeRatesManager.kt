package com.maloshpal.exchangerates.manager.exchangerates

import android.util.Log
import com.maloshpal.exchangerates.manager.AppExecutors
import com.maloshpal.exchangerates.manager.AsyncCallback
import com.maloshpal.exchangerates.manager.CallResult
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRateListModel
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRatesManager
import java.lang.ref.WeakReference
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine

class ExchangeRatesManager(private val appExecutors: AppExecutors,
                           private val networkDataSource: IExchangeRatesDataSource
//                           private val localDataSource: IExchangeRatesDataSource
) : IExchangeRatesManager {

    private val cachedData: IExchangeRateListModel? = null

    override fun getExchangeRates(baseCurrency: String?, callback: IManagerCallback<IExchangeRateListModel>) {
        getExchangeRatesFromNetwork(baseCurrency, callback)
    }

    override fun refreshExchangeRates(baseCurrency: String?, callback: IManagerCallback<IExchangeRateListModel>) {
        getExchangeRatesFromNetwork(baseCurrency, callback)
    }

    private fun getExchangeRatesFromNetwork(baseCurrency: String?, callback: IManagerCallback<IExchangeRateListModel>) {
        val loadUserCallback = WeakReference(callback)

        // TODO local storage request
        appExecutors.networkIO().execute {

            val (networkResponse, error) = runBlocking {
                suspendCoroutine<CallResult<IExchangeRateListModel>> {
                    this@ExchangeRatesManager.networkDataSource.getExchangeRates(baseCurrency, AsyncCallback(it))
                }
            }

            // notify on the main thread
            appExecutors.mainThread().execute {
                loadUserCallback.get()?.let { retainedCallback ->
                    if (error != null) {
                        Log.w(TAG, "Couldn't get exchange rates from network, error message: $error")
                        retainedCallback.handleError()
                    }
                    else {
                        if (networkResponse == null) {
                            Log.e(TAG, "Couldn't get exchange rates from response when error is null")
                        }
                        else {
                            retainedCallback.handleResponse(networkResponse)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val TAG = ExchangeRatesManager::class.java.simpleName
    }
}