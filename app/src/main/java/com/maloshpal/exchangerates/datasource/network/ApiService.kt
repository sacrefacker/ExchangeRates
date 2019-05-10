package com.maloshpal.exchangerates.datasource.network

import com.maloshpal.exchangerates.datasource.network.exchangerates.ExchangeRateListNetworkModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/latest")
    fun getExchangeRates(@Query("base") baseCurrency: String?): Observable<ExchangeRateListNetworkModel>
}