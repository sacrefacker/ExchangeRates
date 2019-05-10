package com.maloshpal.exchangerates.app.dagger.modules

import com.maloshpal.exchangerates.app.dagger.qualifiers.DataSourceQualifiers
import com.maloshpal.exchangerates.app.dagger.scopes.BootScope
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRatesManager
import dagger.Module
import dagger.Provides
import com.maloshpal.exchangerates.datasource.network.ApiService
import com.maloshpal.exchangerates.datasource.network.exchangerates.ExchangeRatesNetworkDataSource
import com.maloshpal.exchangerates.manager.AppExecutors
import com.maloshpal.exchangerates.manager.exchangerates.ExchangeRatesManager
import com.maloshpal.exchangerates.manager.exchangerates.IExchangeRatesDataSource
import javax.inject.Named

@Module
class ManagerModule(
        private val appExecutors: AppExecutors
) {

    @Provides
    @BootScope
    @Named(DataSourceQualifiers.NETWORK_DATA_SOURCE)
    internal fun provideExchangeRatesNetworkDataSource(apiService: ApiService): IExchangeRatesDataSource {
        return ExchangeRatesNetworkDataSource(apiService)
    }

    @Provides
    @BootScope
    internal fun provideExchangeRatesManager(@Named(DataSourceQualifiers.NETWORK_DATA_SOURCE) exchangeRatesNetworkDataSource: IExchangeRatesDataSource): IExchangeRatesManager {
        return ExchangeRatesManager(appExecutors, exchangeRatesNetworkDataSource)
    }
}