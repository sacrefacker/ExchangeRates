package com.maloshpal.exchangerates.ui.fragment.exchangerates

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.maloshpal.exchangerates.R
import com.maloshpal.exchangerates.mvp.presenter.exchangerates.ExchangeRatesPresenter
import com.maloshpal.exchangerates.mvp.view.exchangerates.ExchangeRateViewModel
import com.maloshpal.exchangerates.mvp.view.exchangerates.IExchangeRatesView
import com.maloshpal.exchangerates.ui.activity.base.BaseAppActivity
import com.maloshpal.exchangerates.ui.fragment.base.BaseAppFragment
import com.maloshpal.exchangerates.ui.view.decoration.DividerItemDecoration
import com.maloshpal.exchangerates.ui.view.exchangerates.ExchangeRateEditableView

import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById

@EFragment(R.layout.layout_fragment_exchange_rates)
open class ExchangeRatesFragment : BaseAppFragment(), IExchangeRatesView,
        BaseAppFragment.ActivityTitleProvider, BaseAppActivity.FragmentBackPressed {

// MARK: - Properties

    override val activityTitle: String
        get() = getString(R.string.app_name)

    private lateinit var exchangeRatesAdapter: ExchangeRatesAdapter

    @ViewById(R.id.progress_bar)
    internal lateinit var progressView: ProgressBar

    @ViewById(R.id.swipe_layout_exchange_rates)
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @ViewById(R.id.widget_main_exchange_rate)
    internal lateinit var baseExchangeRateView: ExchangeRateEditableView

    @ViewById(R.id.recycler_exchange_rates)
    internal lateinit var exchangeRatesRecycler: RecyclerView

    @InjectPresenter
    internal lateinit var presenter: ExchangeRatesPresenter

// MARK: - Methods

    override fun onInitInterface() {
        super.onInitInterface()

        this.swipeRefreshLayout.setOnRefreshListener { this.presenter.onForceRefresh() }

        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        this.exchangeRatesRecycler.layoutManager = layoutManager

        val dividerPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
        val itemDecoration = DividerItemDecoration(this.activity, R.drawable.divider)
        itemDecoration.setLeftPaddingInPx(dividerPadding)
        this.exchangeRatesRecycler.addItemDecoration(itemDecoration)

        this.exchangeRatesAdapter = ExchangeRatesAdapter().apply {
            clickListener = { this@ExchangeRatesFragment.presenter.onExchangeRateSelected(it) }
            this@ExchangeRatesFragment.exchangeRatesRecycler.adapter = this
        }

        this.baseExchangeRateView.amountChangeListener = object : ExchangeRateEditableView.IMoneyAmountEditListener {

            override fun onStartChangingAmount() {
                this@ExchangeRatesFragment.presenter.onStartChangingAmount()
            }

            override fun onAmountChanged(amount: Long) {
                this@ExchangeRatesFragment.presenter.onAmountChanged(amount)
            }
        }
    }

    override fun showMainExchangeRate(mainRate: ExchangeRateViewModel) {
        this.baseExchangeRateView.setName(mainRate.id)
        this.baseExchangeRateView.setMoneyAmount(mainRate.amount)
    }

    override fun showExchangeRates(rates: List<ExchangeRateViewModel>) {
        this.exchangeRatesAdapter.showExchangeRates(rates)
    }

    override fun showLoadingError() {
        getDialogManager().showAlertDialog(R.string.title_loading_error, R.string.message_loading_error)
    }

    override fun enableForceRefresh(enable: Boolean) {
        this.swipeRefreshLayout.isEnabled = enable
    }

    override fun showProgress(show: Boolean) {
        this.swipeRefreshLayout.isRefreshing = false
        this.progressView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed(): Boolean {
        showFinishActivityDialog()
        return true
    }
}
