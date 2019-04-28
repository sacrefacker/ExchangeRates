package com.maloshpal.exchangerates.mvp.presenter.exchangerates

import com.arellomobile.mvp.InjectViewState

import javax.inject.Inject

import com.maloshpal.exchangerates.app.InjectionHolder
import com.maloshpal.exchangerates.mvp.model.ExchangeRateListModel
import com.maloshpal.exchangerates.mvp.presenter.base.BasePresenter
import com.maloshpal.exchangerates.mvp.view.exchangerates.IExchangeRatesView
import com.maloshpal.exchangerates.mvp.view.exchangerates.ExchangeRateViewModel
import com.maloshpal.exchangerates.mvp.model.IExchangeRatesManager
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback
import com.maloshpal.exchangerates.mvp.presenter.base.ScheduledFetcher
import java.util.concurrent.Executors

@InjectViewState
class ExchangeRatesPresenter : BasePresenter<IExchangeRatesView>() {

// MARK: - Variables

    private var isInEditState: Boolean = false

    private var mainExchangeRate: ExchangeRateViewModel? = null

    private var latestExchangeRateList: ExchangeRateListModel? = null

    @Inject
    internal lateinit var ratesManager: IExchangeRatesManager

    private var schedulerFetcher: ScheduledFetcher<ExchangeRateListModel>? = null
    private var exetutorService = Executors.newSingleThreadScheduledExecutor()

// MARK: - Construction

    init {
        val bootstrapComponent = InjectionHolder.instance().bootstrapComponent
        bootstrapComponent.inject(this)
    }

// MARK: - Methods

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        this.schedulerFetcher = ScheduledFetcher(
                this.exetutorService,
                FETCH_SCHEDULE_INTERVAL_MILLIS,
                ExchangeRatesCallback())
    }

    override fun attachView(view: IExchangeRatesView) {
        super.attachView(view)
        startFetchingExchangeRates()
    }

    override fun detachView(view: IExchangeRatesView) {
        super.detachView(view)
        stopFetchingExchangeRates()
    }

// MARK: - Contract methods

    fun onForceRefresh() {
        startFetchingExchangeRates()
    }

    fun onExchangeRateSelected(rate: ExchangeRateViewModel) {
        this.mainExchangeRate = rate
        this.viewState.showMainExchangeRate(rate)

        val latestExchangeRates = requireNotNull(this.latestExchangeRateList) { "Attempt selecting main rate on empty exchange rate list" }
        applyLatestExchangeRates(latestExchangeRates)
    }

    fun onStartChangingAmount() {
        this.isInEditState = true
    }

    fun onAmountChanged(amount: Long) {
        this.isInEditState = false
        val oldMainExchangeRate = requireNotNull(this.mainExchangeRate) { "Attempt changing amount on empty exchange rate" }
        val newMainExchangeRate = ExchangeRateViewModel(oldMainExchangeRate.id, amount)
        this.mainExchangeRate = newMainExchangeRate
        this.viewState.showMainExchangeRate(newMainExchangeRate)

        val latestExchangeRates = requireNotNull(this.latestExchangeRateList) { "Attempt selecting main rate on empty exchange rate list" }
        applyLatestExchangeRates(latestExchangeRates)
    }

// MARK: - Private functions

    private fun startFetchingExchangeRates() {
        this.viewState.enableForceRefresh(false)
        this.schedulerFetcher?.start { this@ExchangeRatesPresenter.requestExchangeRates(it) }
    }

    private fun stopFetchingExchangeRates() {
        this.viewState.enableForceRefresh(true)
        this.schedulerFetcher?.stop()
    }

    private fun requestExchangeRates(callback: IManagerCallback<ExchangeRateListModel>) {
        this.ratesManager.requestExchangeRates(callback)
    }

    private fun applyLatestExchangeRates(rateList: ExchangeRateListModel) {
        this.latestExchangeRateList = rateList
        if (!this.isInEditState) {
            val mainExchangeRate = requireNotNull(this.mainExchangeRate) { "Attempt changing amount on empty exchange rate" }
            val mainExchangeRateMultiplier = rateList.rates.first { it.id == mainExchangeRate.id }.multiplier
            val rateViewModels = rateList.rates
                    .filter { it.id != mainExchangeRate.id }
                    .map {
                        val rateAmount = mainExchangeRate.amount * it.multiplier / mainExchangeRateMultiplier
                        ExchangeRateViewModel(it.id, rateAmount)
                    }
            this.viewState.showExchangeRates(rateViewModels)
        }
    }

// MARK: - Inner types

    private inner class ExchangeRatesCallback : IManagerCallback<ExchangeRateListModel> {

        override fun handleResponse(response: ExchangeRateListModel) {
            if (this@ExchangeRatesPresenter.mainExchangeRate == null) {
                val firstExchangeRateInList = ExchangeRateViewModel(response.rates[0].id, 1)
                this@ExchangeRatesPresenter.mainExchangeRate = firstExchangeRateInList
                this@ExchangeRatesPresenter.viewState.showMainExchangeRate(firstExchangeRateInList)
            }
            applyLatestExchangeRates(response)
        }

        override fun handleError() {
            stopFetchingExchangeRates()
            this@ExchangeRatesPresenter.viewState.showLoadingError()
        }
    }

// MARK: - Companion

    companion object {
        private const val FETCH_SCHEDULE_INTERVAL_MILLIS = 1000L
    }
}
