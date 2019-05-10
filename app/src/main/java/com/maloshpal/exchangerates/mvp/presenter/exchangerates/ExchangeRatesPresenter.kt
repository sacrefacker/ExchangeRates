package com.maloshpal.exchangerates.mvp.presenter.exchangerates

import com.arellomobile.mvp.InjectViewState

import javax.inject.Inject

import com.maloshpal.exchangerates.app.InjectionHolder
import com.maloshpal.exchangerates.mvp.presenter.base.BasePresenter
import com.maloshpal.exchangerates.mvp.view.exchangerates.IExchangeRatesView
import com.maloshpal.exchangerates.mvp.view.exchangerates.ExchangeRateViewModel
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRatesManager
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRateListModel

@InjectViewState
class ExchangeRatesPresenter : BasePresenter<IExchangeRatesView>() {

// MARK: - Variables

    private var isInEditState: Boolean = false

    private var baseCurrency: String? = null
    private var baseCurrencyAmount: Long = DEFAULT_BASE_CURRENCY_AMOUNT

    private var latestExchangeRateList: IExchangeRateListModel? = null

    @Inject
    internal lateinit var ratesManager: IExchangeRatesManager

//    private var schedulerFetcher: ScheduledFetcher<ExchangeRateListNetworkModel>? = null
//    private var executorService = Executors.newSingleThreadScheduledExecutor()

// MARK: - Construction

    init {
        val bootstrapComponent = InjectionHolder.instance().bootstrapComponent
        bootstrapComponent.inject(this)
    }

// MARK: - Methods

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        this.viewState.showProgress(true)

//        this.schedulerFetcher = ScheduledFetcher(
//                this.executorService,
//                FETCH_SCHEDULE_INTERVAL_SECONDS,
//                ExchangeRatesCallback())

        this.ratesManager.getExchangeRates(this.baseCurrency, ExchangeRatesCallback())
    }

//    override fun attachView(view: IExchangeRatesView) {
//        super.attachView(view)
//        startFetchingExchangeRates()
//    }

//    override fun detachView(view: IExchangeRatesView) {
//        super.detachView(view)
//        stopFetchingExchangeRates()
//    }

// MARK: - Contract methods

    fun onForceRefresh() {
//        startFetchingExchangeRates()
        this.ratesManager.getExchangeRates(this.baseCurrency, ExchangeRatesCallback())
    }

    fun onExchangeRateSelected(rate: ExchangeRateViewModel) {
        this.baseCurrency = rate.id
        this.baseCurrencyAmount = rate.amount
        this.viewState.showMainExchangeRate(rate)

        val latestExchangeRates = requireNotNull(this.latestExchangeRateList,
                { "Attempt selecting main currency on empty exchange rate list" } )
        applyLatestExchangeRates(latestExchangeRates)
    }

    fun onStartChangingAmount() {
        this.isInEditState = true
    }

    fun onAmountChanged(amount: Long) {
        this.isInEditState = false
        this.baseCurrencyAmount = amount

        val latestExchangeRates = requireNotNull(this.latestExchangeRateList,
                { "Attempt selecting main rate on empty exchange rate list" } )
        applyLatestExchangeRates(latestExchangeRates)
    }

// MARK: - Private functions

//    private fun startFetchingExchangeRates() {
//        this.viewState.enableForceRefresh(false)
//        this.schedulerFetcher?.start { this@ExchangeRatesPresenter.requestExchangeRates(it) }
//    }

//    private fun stopFetchingExchangeRates() {
//        this.viewState.enableForceRefresh(true)
//        this.schedulerFetcher?.stop()
//    }

//    private fun requestExchangeRates(callback: IManagerCallback<IExchangeRateListModel>) {
//        this.ratesManager.getExchangeRates(this.baseCurrency, callback)
//    }

    private fun applyLatestExchangeRates(rateList: IExchangeRateListModel) {
        this.latestExchangeRateList = rateList
        if (!this.isInEditState) {
            val baseAmount = this@ExchangeRatesPresenter.baseCurrencyAmount
            val rateViewModels = rateList.rates
                    .map {
                        val currencyAmount = baseAmount * it.value
                        ExchangeRateViewModel(it.key, Math.round(currencyAmount))
                    }
            this.viewState.showExchangeRates(rateViewModels)
        }
    }

// MARK: - Inner types

    private inner class ExchangeRatesCallback : IManagerCallback<IExchangeRateListModel> {

        override fun handleResponse(response: IExchangeRateListModel) {
            if (this@ExchangeRatesPresenter.baseCurrency == null) {
                this@ExchangeRatesPresenter.baseCurrency = response.base
                val baseExchangeRate = ExchangeRateViewModel(response.base, this@ExchangeRatesPresenter.baseCurrencyAmount)
                this@ExchangeRatesPresenter.viewState.showMainExchangeRate(baseExchangeRate)
            }
            this@ExchangeRatesPresenter.viewState.showProgress(false)
            applyLatestExchangeRates(response)
        }

        override fun handleError() {
            this@ExchangeRatesPresenter.viewState.showProgress(false)
//            stopFetchingExchangeRates()
            this@ExchangeRatesPresenter.viewState.showLoadingError()
        }
    }

// MARK: - Companion

    companion object {

//        private const val FETCH_SCHEDULE_INTERVAL_SECONDS = 1L

        private const val DEFAULT_BASE_CURRENCY_AMOUNT = 100L
    }
}
