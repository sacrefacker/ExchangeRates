package com.maloshpal.exchangerates.app.manager

import com.maloshpal.exchangerates.mvp.model.exchangerates.ExchangeRateListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/latest")
    fun getExchangeRates(@Query("base") baseCurrency: String?): Observable<ExchangeRateListModel>
}