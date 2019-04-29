package com.maloshpal.exchangerates.app.dagger.modules

import com.maloshpal.exchangerates.app.dagger.scopes.BootScope
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRatesManager
import dagger.Module
import dagger.Provides
import com.maloshpal.exchangerates.app.manager.ApiService
import com.maloshpal.exchangerates.app.manager.exchangerates.ExchangeRatesManager

@Module
class ManagerModule {

    @Provides
    @BootScope
    internal fun provideExchangeRatesManager(apiService: ApiService): IExchangeRatesManager {
        return ExchangeRatesManager(apiService)
    }
}