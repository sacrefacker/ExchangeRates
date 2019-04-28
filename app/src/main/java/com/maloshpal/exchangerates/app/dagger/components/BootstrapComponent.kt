package com.maloshpal.exchangerates.app.dagger.components

import com.maloshpal.exchangerates.app.dagger.modules.ManagerModule
import com.maloshpal.exchangerates.app.dagger.modules.BootstrapModule
import com.maloshpal.exchangerates.app.dagger.scopes.BootScope
import com.maloshpal.exchangerates.mvp.presenter.exchangerates.ExchangeRatesPresenter
import com.maloshpal.exchangerates.ui.activity.MainActivity
import com.maloshpal.exchangerates.ui.activity.base.BaseAppActivity
import dagger.Component

@BootScope
@Component(modules = [BootstrapModule::class, ManagerModule::class])
interface BootstrapComponent
{
    fun inject(mainActivity: MainActivity)
    fun inject(baseAppActivity: BaseAppActivity)

    fun inject(baseAppActivity: ExchangeRatesPresenter)
}
