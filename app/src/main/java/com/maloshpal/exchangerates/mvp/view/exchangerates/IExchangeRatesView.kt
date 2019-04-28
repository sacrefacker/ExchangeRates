package com.maloshpal.exchangerates.mvp.view.exchangerates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.maloshpal.exchangerates.mvp.view.base.IBaseView
import com.maloshpal.exchangerates.mvp.view.base.IProgressView

@StateStrategyType(AddToEndSingleStrategy::class)
interface IExchangeRatesView : IBaseView, IProgressView {

    fun showMainExchangeRate(mainRate: ExchangeRateViewModel)
    fun showExchangeRates(rates: List<ExchangeRateViewModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoadingError()

    fun enableForceRefresh(enable: Boolean)
}
