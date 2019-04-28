package com.maloshpal.exchangerates.ui.fragment.exchangerates

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.maloshpal.exchangerates.mvp.view.exchangerates.ExchangeRateViewModel
import com.maloshpal.exchangerates.ui.view.IOnItemViewClickListener
import com.maloshpal.exchangerates.ui.view.exchangerates.ExchangeRateListItemView
import com.maloshpal.exchangerates.ui.view.exchangerates.ExchangeRateViewHolder

class ExchangeRatesAdapter : RecyclerView.Adapter<ExchangeRateViewHolder>()
{
// MARK: - Properties

    var clickListener: ((ExchangeRateViewModel) -> Unit)? = null

    private var items: List<ExchangeRateViewModel> = emptyList()

// MARK: - Methods

    fun showExchangeRates(rates: List<ExchangeRateViewModel>?) {
        this.items = rates.orEmpty()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val view = ExchangeRateListItemView.newView(parent.context)
        return ExchangeRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {

        holder.bind(this.items[position], position, object : IOnItemViewClickListener {

            override fun onItemViewClick(view: View, position: Int) {
                this@ExchangeRatesAdapter.clickListener?.invoke(this@ExchangeRatesAdapter.items[position])
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }
}