package com.maloshpal.exchangerates.mvp.view.base

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IProgressView : IBaseView {

    fun showProgress(show: Boolean)
}
