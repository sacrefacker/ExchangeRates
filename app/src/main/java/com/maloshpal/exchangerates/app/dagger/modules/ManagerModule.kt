package com.maloshpal.exchangerates.app.dagger.modules

import com.maloshpal.exchangerates.app.dagger.scopes.BootScope
import com.maloshpal.exchangerates.mvp.model.IExchangeRatesManager
import dagger.Module
import dagger.Provides

@Module
class ManagerModule {

    @Provides
    @BootScope
    internal fun provideExchangeRatesManager(): IExchangeRatesManager {
        TODO("not implemented")
    }
}